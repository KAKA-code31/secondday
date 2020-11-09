package com.honestpeak.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.honestpeak.model.User;
import com.honestpeak.result.Result;
import com.honestpeak.service.StudentService;
import com.honestpeak.service.TeacherService;
import com.honestpeak.shiro.ShiroUser;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.PropertyUtils;
import com.honestpeak.utils.StringEscapeEditor;
import com.honestpeak.utils.StringUtil;

/**
 * 
 * @author bpc
 *
 */
public abstract class BaseController {
	 @Autowired
	    protected TeacherService teacherService;
	 @Autowired
		private StudentService stuService;
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }
    
    protected void initPageParams(ModelAndView mv, Page<?> page){
		mv.addObject("currentPage", page.getPageNo());
		mv.addObject("totalPage", page.getTotalPage());
		mv.addObject("totalCount", page.getTotalCount());
	}

    /**
     * 获取当前登录用户对象
     * @return
     */
    public User getCurrentUser() {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        User currentUser = null; 
        if(user.getAdminType() == 1){
        	currentUser = teacherService.findUserById(user.id);
        }  else if(user.getAdminType() == 0){
        	currentUser = stuService.findUserById(user.id);
        	currentUser.setAdminType(0);
        }
       return currentUser;
    }
    /**
     * 获取当前登录用户对象学生
     * @return
     */
    public User getCurrentStudent() {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        User currentUser = stuService.findUserById(user.id);
        return currentUser;
		//return null;
    }
    /**
     * 获取当前登录用户id
     * @return
     */
    public Long getUserId() {
        return this.getCurrentUser().getId();
    }

    /**
     * 获取当前登录用户名
     * @return
     */
    public String getUserName() {
        return this.getCurrentUser().getName();
    }

    /**
     * ajax失败
     * @param msg 失败的消息
     * @return {Object}
     */
    public Object renderError(String msg) {
        Result result = new Result();
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * @return {Object}
     */
    public Object renderSuccess() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    /**
     * ajax成功
     * @param msg 消息
     * @return {Object}
     */
    public Object renderSuccess(String msg) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * @param obj 成功时的对象
     * @return {Object}
     */
    public Object renderSuccess(Object obj) {
        Result result = new Result();
        result.setSuccess(true);
        result.setObj(obj);
        return result;
    }
    /**
     * ajax成功
     * @param obj 成功时的对象
     * @param msg 消息
     * @return {Object}
     */
    public Object renderSuccess(String msg, Object obj) {
    	Result result = new Result();
    	result.setSuccess(true);
    	result.setObj(obj);
    	result.setMsg(msg);
    	return result;
    }
    
    /**
     * ajax成功
     * @param obj 成功时的对象
     * @param msg 消息
     * @return {Object}
     */
    public Object renderSuccess(String msg, Object obj,Object obj1) {
    	Result result = new Result();
    	result.setSuccess(true);
    	result.setObj(obj);
    	result.setObj(obj1);
    	result.setMsg(msg);
    	return result;
    }
    
    /**
     * @Title: getRootPath
     * @Description: 获取当前web系统在服务器中的路径，末尾不带“/”
     * @return
     */
    public String getRootPath(){
    	return PropertyUtils.getRoot();
    }
    
    /**
     * @Title: getSession
     * @Description: 获取Session
     * @return
     */
    public Session getSession() {
    	return SecurityUtils.getSubject().getSession();
    }
    
    public Integer getCurrentUserIdentity() {
    	 ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user==null?null:user.adminType;
	}
    /**
	 * @Title: getIntYearList
	 * @Description: 获取整型年份
	 * @return List<Integer> 返回类型
	 * @author zzy
	 * @throws
	 * @return
	 */
	public static List<Integer> getIntYearList(){
		
		Integer year = Integer.parseInt(StringUtil.getCurrentYear());
		List<Integer> yearList = new ArrayList<Integer>();
		for (int i = year; i >= year-8; i--) {
			yearList.add(i);
		}
		return yearList;
	}
	
}
