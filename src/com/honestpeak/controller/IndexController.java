package com.honestpeak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.honestpeak.annotation.LogCustom;
import com.honestpeak.model.User;
/**
 * 
 * @author bpc
 *
 */
@Controller
@LogCustom(desc="首页")
public class IndexController extends BaseController {
	
	/**
	 * @Title: index
	 * @Description: 进入前台首页
	 * @return
	 */
//	@LogCustom(desc="进入前台首页")
//	@RequestMapping(value="front/index")
//	public ModelAndView index(){
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("front/front");
//		return mav;
//	}
	
	/**
	 * @Title: unauth
	 * @Description: 未取得授权
	 * @return
	 */
	@LogCustom(desc="未取得系统授权")
	@RequestMapping(value="unauth")
	public ModelAndView unauth(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("error/403");
		return mav;
	}
	
	/**
	 * @Title: home
	 * @Description: 进入后台首页
	 * @return
	 */
	@RequestMapping(value="back/home")
	public ModelAndView home(){
		ModelAndView mav = new ModelAndView();
		User user = (User) getSession().getAttribute("user");
		if(user == null) {
			mav = new ModelAndView("common/error");
			mav.addObject("error", "未登录！");
			return mav;
		}
		mav.setViewName("back/index");
		return mav;
	}
	/**
	 * @Title: home
	 * @Description: 进入后台首页
	 * TODO:后面考虑要不要做成动态的
	 * @return
	 */
	@RequestMapping(value="back/index")
	public ModelAndView tips(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("back/home");
		return mav;
	}
	

}
