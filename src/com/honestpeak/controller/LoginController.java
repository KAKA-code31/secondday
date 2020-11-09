package com.honestpeak.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.honestpeak.annotation.LogCustom;
import com.honestpeak.constant.AccountConstants;
import com.honestpeak.model.User;
import com.honestpeak.service.LoginRecordService;
import com.honestpeak.service.StudentService;
import com.honestpeak.service.TeacherService;
import com.honestpeak.shiro.LoginToken;
import com.honestpeak.utils.ClientIPUtils;
import com.honestpeak.utils.EncryptFrontUtil;
import com.honestpeak.utils.PasswordUtil;

@Controller
@LogCustom(desc="登录控制")
public class LoginController extends BaseController {
	@Autowired
	private LoginRecordService loginRecordService;
	@Autowired
	private StudentService stuService;
	@Autowired
	private TeacherService teacherService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @Title: loginAdmin
	 * @Description: 只允许GET方法进入的后台登陆界面URL.
	 * @return
	 */
	@LogCustom(desc="进入后台管理登陆页面")
	@RequestMapping(value = "/login/admin", method = RequestMethod.GET)
	public ModelAndView loginAdmin(){
		ModelAndView mav = null;
		if (SecurityUtils.getSubject().isAuthenticated()) {
			//发现授权了，直接跳转到首页
			mav = new ModelAndView("redirect:/back/home");
        } else {
        	mav = new ModelAndView("login/loginAdmin");
        }
		return mav;
	}
	
	/**
	 * @Title: loginAdmin
	 * @Description: 具体的登陆方法
	 * @param username 用户名
	 * @param password 密码
	 * @param request 请求
	 * @return
	 */
	@LogCustom(desc="进入后台管理登陆页面")
	@RequestMapping(value = "/login/admin", method = RequestMethod.POST)
	@ResponseBody
	public Object loginAdmin(String username, String password,Integer type, HttpServletRequest request){
		logger.info("用户登录，用户名："+username);
		User u = null;
		if (StringUtils.isBlank(username)) {
            return renderError("用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return renderError("密码不能为空");
        }
        //前台用户名、密码 信息 解密
        username = new String(EncryptFrontUtil.decode(username));
        password = new String(EncryptFrontUtil.decode(password));
        Subject user = SecurityUtils.getSubject();
        LoginToken token = null;
        if (type == 2) {// 学生登录
        	u = stuService.loginUserByUserAccount(username);
        	if(u!=null){
        	username = u.getName();
			u.setAdminType(0);
        	token = new LoginToken(username, PasswordUtil.encryption(password).toCharArray(), false, AccountConstants.LOGIN_FRONT);
			}else{
				return renderError("账号不存在！");
			}
        }else if (type == 1){ // 教师登录
        	u = teacherService.loginUserByUserAccount(username);
        	if(u != null){
        		username = u.getName();
        		token = new LoginToken(username, PasswordUtil.encryption(password).toCharArray(), false, AccountConstants.LOGIN_ADMIN);
        	}else{
        		 return renderError("账号不存在！");
        	}
        }
        try {
        	request.getSession().setAttribute("user", u);
            user.login(token);
        } catch (UnknownAccountException e) {
        	loginRecordService.userLogOutFailure(username, ClientIPUtils.getClientIP(request), AccountConstants.NAMEERROR);
            logger.info("账号不存在：{}");
            e.printStackTrace();
            return renderError("账号不存在！");
        } catch (DisabledAccountException e) {
        	loginRecordService.userLogOutFailure(username, ClientIPUtils.getClientIP(request), AccountConstants.NOTENABLE);
            logger.info("账号或角色未启用：{}");
            e.printStackTrace();
            return renderError("账号或角色未启用");
        } catch (IncorrectCredentialsException e) {
        	loginRecordService.userLogOutFailure(username, ClientIPUtils.getClientIP(request), AccountConstants.PWDERROR);
            logger.info("密码错误：{}");
            return renderError("用户名或密码错误！");
        } catch (RuntimeException e) {
        	loginRecordService.userLogOutFailure(username, ClientIPUtils.getClientIP(request), AccountConstants.OTHER);
            logger.error("未知错误,请联系管理员：{}", e);
            return renderError("未知错误,请联系管理员！");
        }
	    
	    return renderSuccess("登陆成功");
	}
	
	/**
     * 退出
     * @return {Result}
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    @LogCustom(desc="进行用户登出！")
    public Object logout(HttpServletRequest request) {
        logger.info("用户登出");
        loginRecordService.userLogOutSuccess(getCurrentUser().getUserId(), ClientIPUtils.getClientIP(request));
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return renderSuccess("退出成功！");
    }
    
    /**
     * 切换账号
     * @param request
     * @return
     */
    @RequestMapping("/loginOut")
	public ModelAndView loginOut(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			request.getSession().removeAttribute("user");
			Subject subject = SecurityUtils.getSubject();
	        subject.logout();
			mav.setViewName("redirect:/login/admin");
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			mav = new ModelAndView("common/error");
			mav.addObject("error", "有异常！");
			return mav;
		}
	}
}