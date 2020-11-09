package com.honestpeak.scan;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.honestpeak.annotation.LogCustom;
import com.honestpeak.constant.PathConstants;
import com.honestpeak.model.SystemLog;
import com.honestpeak.service.SystemLogService;
import com.honestpeak.utils.ClientIPUtils;
import com.honestpeak.utils.DateTimeUtils;

/**
 * @ClassName: SystemLogAspect
 * @Description: AOP 日志
 * @author Jeabev
 * @date 2016年7月30日 上午9:21:46
 */
@Aspect
@Component
public class SystemLogAspect {
    private static Logger LOGGER = LoggerFactory.getLogger(SystemLogAspect.class);

    @Autowired
    private SystemLogService logService;

    @Around("@annotation(log)")
    public Object recordSysLog(ProceedingJoinPoint point,LogCustom log) throws Throwable {
    	
        String strMethodName = point.getSignature().getName();
       // String strClassName = point.getTarget().getClass().getName();
        //类上的 LogCustom 信息
        LogCustom cLog = point.getTarget().getClass().getAnnotation(LogCustom.class);
        Object[] params = point.getArgs();
        StringBuffer bfParams = new StringBuffer();
        Enumeration<String> paraNames = null;
        HttpServletRequest request = null;
        if (params != null && params.length > 0) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            paraNames = request.getParameterNames();
            String key;
            String value;
            while (paraNames.hasMoreElements()) {
                key = paraNames.nextElement();
                value = request.getParameter(key);
                bfParams.append(key).append("=").append(value).append("&");
            }
            if (StringUtils.isBlank(bfParams)) {
                bfParams.append(request.getQueryString());
            } else {
            	if(bfParams.charAt(bfParams.length()-1) == '&'){
            		bfParams.deleteCharAt(bfParams.length()-1);
            	}
            }
        }

        /*String strMessage = String
                .format("[类名]:%s,[方法]:%s", strClassName, strMethodName);*/
        StringBuffer strMessage = new StringBuffer("");
        if (isWriteLog(strMethodName)) {
            try {
                Subject currentUser = SecurityUtils.getSubject();
                PrincipalCollection collection = currentUser.getPrincipals();
                if (null != collection) {
                    String loginName = collection.getPrimaryPrincipal().toString();
                    SystemLog sysLog = new SystemLog();
                    sysLog.setLoginName(loginName);
                    
                    strMessage = createLogMessage(strMessage,loginName,cLog,log,bfParams,strMethodName);
                    
                    sysLog.setOptContent(strMessage.toString());
                    //判断参数是否需要被写入,若要写入。
                    if(isAddParam(strMethodName) && bfParams!=null && !bfParams.toString().equals("null")){
                    	sysLog.setOptContent(sysLog.getOptContent()+",参数信息："+bfParams);
                    }
                    
                    sysLog.setOptTime(new Date());
                    if (request != null) {
                    	sysLog.setOptIp(ClientIPUtils.getClientIP(request));
                    }
                    LOGGER.info(sysLog.toString());
                    logService.insertLog(sysLog);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return point.proceed();
    }
    
    /**
     * @Title: createLogMessage
     * @Description: 生成日志信息方法
     * @param strMessage 接收日志信息StringBuffer
     * @param loginName 登陆用户名称
     * @param cLog 类日志信息  {@link LogCustom}
     * @param log 方法日志信息 {@link LogCustom}
     * @param method 方法名称
     * @param bfParams 参数
     * @return 日志信息 
     */
    private StringBuffer createLogMessage(StringBuffer strMessage, String loginName, LogCustom cLog, LogCustom log, StringBuffer bfParams, String method) {
    	if(strMessage==null){
    		strMessage = new StringBuffer();
    	}
    	//用户名不为空
    	if(loginName!=null && !loginName.trim().equals("")) {
    		strMessage.append("用户[ "+loginName+" ],于"+DateTimeUtils.DateToDateTimeString(new Date()));
    	}
    	
    	//类说明不为空，添加类说明
    	if(cLog!=null && !cLog.desc().trim().equals("")) {
    		strMessage.append(",通过"+cLog.desc());
    	}
    	
    	//方法说明不为空，添加方法说明
    	if(log!=null && !log.desc().trim().equals("")) {
    		strMessage.append(","+log.desc());
    	}
    	
    	//需要记录参数，并且参数列表不为空，记录参数
    	/*if(isAddParam(method) && bfParams!=null && !bfParams.equals("")){
    		
    	}*/
		return strMessage;
	}
    
    /**
     * @Title: isAddParam
     * @Description: 根据方法名，判断添加日志记录时是否要将用户携带的参数计入日志
     * @param method 方法名
     * @return
     */
	private boolean isAddParam(String method) {
		for (String s : PathConstants.LOGPARAM) {
            if (method.indexOf(s) > -1) {
                return true;
            }
        }
        return false;
	}
	
	/**
	 * @Title: isWriteLog
	 * @Description: 根据方法名，判断用户记录是否要被同步到日志中
	 * @param method 方法名
	 * @return
	 */
	private boolean isWriteLog(String method) {
        for (String s : PathConstants.SYSLOGURL) {
            if (method.indexOf(s) > -1) {
                return true;
            }
        }
        return false;
    }
}
