package com.honestpeak.controller;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.honestpeak.annotation.LogCustom;
import com.honestpeak.model.SystemLog;
import com.honestpeak.service.SystemLogService;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;
/**
 * 
 * @author bpc
 *
 */
@Controller
@RequestMapping("/back/systemLog")
@LogCustom(desc="系统日志管理")
public class SystemLogController extends BaseController {
	@Autowired
	private SystemLogService systemLogService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 
	 * @Title: manager
	 * @Description: 获取日志列表
	 * @return ModelAndView 返回类型
	 */
	@RequestMapping(value="/manager")
	@LogCustom(desc="获取了系统日志列表")
	public ModelAndView manager(Integer currentPage, SystemLog systemLog) {
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行日志管理");
		ModelAndView mav = new ModelAndView();
		try {
			Page<SystemLog> page = systemLogService.findSystemLogPage(systemLog,currentPage==null?1:currentPage);
			//列表项
			mav.addObject("systemLogList", page.getResultList());
			//分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());
			
			//参数回显
			mav.addObject("systemLog", systemLog);
			mav.setViewName("back/systemLog/manager");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("日志获取失败，manager出错{}",e);
		}

		return mav;
	}
	
	@RequestMapping(value="/detail/{id}")
	@LogCustom(desc="进入系统日志详细查看页面")
	public ModelAndView detail(@PathVariable String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进入日志详情页面");
		ModelAndView mav = new ModelAndView();
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt==null){
				logger.error("查看日志详情失败，系统无法解析表单信息！");
				mav.setViewName("error/400");
			} else {
				SystemLog systemLog = systemLogService.getById(Long.parseLong(decrypt));
				mav.addObject("systemLog",systemLog);
				if(systemLog!=null){
					mav.setViewName("/back/systemLog/detail");
				} else {
					logger.error("查看日志详情失败，找不到对应的教师信息！");
					mav.setViewName("error/500");
				}
			}
		} catch (Exception e) {
			logger.error("查看日志详情失败，detail出错{}", e);
			mav.setViewName("error/500");
		}
		return mav;
	
	}


}
