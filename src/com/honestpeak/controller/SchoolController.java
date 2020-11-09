package com.honestpeak.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.honestpeak.annotation.LogCustom;
import com.honestpeak.model.School;
import com.honestpeak.result.Result;
import com.honestpeak.service.SchoolService;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;
/**
 * 
 * @author bpc
 *
 */
@Controller
@RequestMapping("back/school")
@LogCustom(desc="学校项管理")
public class SchoolController extends BaseController{
	@Autowired
	private SchoolService schoolService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("manager")
	@LogCustom(desc="获取了学校项信息列表！")
	public ModelAndView schooltList(Integer currentPage, School school){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行学校管理");
		ModelAndView mav = new ModelAndView();
		try{
			Page<School> page = schoolService.getAllForPage(currentPage == null ? 1:currentPage, school);
			mav.addObject("schoolList", page.getResultList());
			initPageParams(mav, page);
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());
			mav.addObject("school", school);
			mav.setViewName("back/school/manager");
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			mav = new ModelAndView("/error/500");
			logger.error("学校信息管理，manager出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping("schoolAddUI")
	public ModelAndView schoolAddUI(ModelAndView mav){
		mav.setViewName("back/school/schoolAdd");
		return mav;
	}
	
	/**
	 * 院系添加
	 * @param mav
	 * @param department
	 * @return
	 */
	@RequestMapping("schoolAdd")
	@ResponseBody
	@LogCustom(desc="进行了单个学校添加")
	public Result schoolAdd(ModelAndView mav, School school){
		try {
			schoolService.add(school);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			logger.error("学校添加,schoolAdd出错{}");
			return new Result(false, "添加失败");
		}
	}
	
	/**
	 * 删除学校
	 * @param id
	 * @return
	 */
	@RequestMapping(value="schoolDelete/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了单个学校删除")
	public Result schoolDelete(@PathVariable("id")String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行学校删除，删除项："+id);
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){
				schoolService.delete(Long.valueOf(decrypt));
			}
			return new Result(true,"删除成功");
		} catch (DataIntegrityViolationException e) {
			logger.error("学校删除，删除处理 出错，无法删除关联对象！",e);
			return new Result(false,"删除失败，有关联对象");}
			catch (Exception e) {
			logger.error("学校删除,schoolDelete出错{}");
			return new Result(false,"删除失败");
		}
	}
	
	@RequestMapping("schoolDeleteIDs")
	@ResponseBody
	@LogCustom(desc="进行了学校批量删除")
	public Result schoolDeleteIDs(String ids){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行学校批量删除");
		try {
			schoolService.deleteAll(ids.split(","));
			return new Result(true,"删除成功");
		} catch (DataIntegrityViolationException e) {
			logger.error("学校删除，deleteIDs 删除处理 出错，无法删除关联对象！",e);
			return new Result(false,"删除失败");
		} catch (Exception e) {
			logger.error("学校删除，delete 删除处理 出错{}",e);
			return new Result(false,"删除失败");
		}
	}
	/**
	 * 修改界面
	 * @param mav
	 * @param id
	 * @return
	 */
	@RequestMapping("schoolUpdateUI/{id}")
	@LogCustom(desc="进行了院系修改页面")
	public ModelAndView schoolUpdateUI(ModelAndView mav,@PathVariable("id")String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行学校修改");
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){
				School school = schoolService.getById(Long.valueOf(decrypt));
				mav.addObject("school", school);
				mav.setViewName("back/school/schoolUpdate");
			}
			return mav;
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("学校信息修改页面，schoolUpdateUI出错{}", e);
			return mav;
		}
	}
	/**
	 * @Title: schoolUpdate
	 * @Description: 更新信息
	 * @return Result 返回类型
	 * @throws
	 * @param mav
	 * @param school
	 * @return
	 */
	@RequestMapping("schoolUpdate")
	@ResponseBody
	public Result schoolUpdate(School school){
		try {
			schoolService.update(school);
			return new Result(true,"修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"修改失败");
		}
	}
}
