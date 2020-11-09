package com.honestpeak.controller;

import java.util.Date;
import java.util.List;

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
import com.honestpeak.model.Depart;
import com.honestpeak.model.School;
import com.honestpeak.result.Result;
import com.honestpeak.service.DepartmentService;
import com.honestpeak.service.SchoolService;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;
import com.honestpeak.vo.DepartmentVo;
/**
 * 
 * @Title: DepartmentController.java 
 * @Package com.honestpeak.controller 
 * @Description: 院系信息管理 
 * @author BPC
 * @date 2017年7月20日 下午2:18:48 
 * @version V1.0
 */
@Controller
@RequestMapping("back/department")
@LogCustom(desc="院系项管理")
public class DepartmentController extends BaseController{
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private SchoolService schoolService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("manager")
	public ModelAndView departmentList(ModelAndView mav,Integer currentPage, DepartmentVo department ,String schoolName){
		try{
			Page<DepartmentVo> page = departmentService.getAllForPage(currentPage == null ? 1:currentPage,schoolName,department);
			mav.addObject("departmentList", page.getResultList());
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());
			mav.addObject("department", department);
			mav.addObject("schoolName", schoolName);
			mav.setViewName("back/department/manager");
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			mav = new ModelAndView("/error/500");
			logger.error("院系信息管理，manager出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping("departmentAddUI")
	public ModelAndView departmentAddUI(ModelAndView mav){
		List<School> schoolList = schoolService.getAll();
		mav.addObject("schoolList",schoolList);
		mav.setViewName("back/department/departmentAdd");
		return mav;
	}
	
	/**
	 * 院系添加
	 * @param mav
	 * @param department
	 * @return
	 */
	@RequestMapping("departmentAdd")
	@ResponseBody
	@LogCustom(desc="进行了单个院系添加")
	public Result departmentAdd(ModelAndView mav, Depart department){
		try {
			departmentService.add(department);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			logger.error("院系添加,departmentAdd出错{}");
			return new Result(false, "添加失败");
		}
	}
	
	/**
	 * 删除院系
	 * @param id
	 * @return
	 */
	@RequestMapping(value="departmentDelete/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了单个院系删除")
	public Result departmentDelete(@PathVariable("id")String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行院系删除，删除项："+id);
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){
				departmentService.delete(Long.valueOf(decrypt));
			}
			return new Result(true,"删除成功");
		} catch (DataIntegrityViolationException e) {
			logger.error("院系删除，删除处理 出错，无法删除关联对象！",e);
			return new Result(false,"删除失败，有关联对象");}
			catch (Exception e) {
			logger.error("院系删除,departmentDelete出错{}");
			return new Result(false,"删除失败");
		}
	}
	
	@RequestMapping("departmentDeleteIDs")
	@ResponseBody
	@LogCustom(desc="进行了院系批量删除")
	public Result departmentDeleteIDs(String ids){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行院系批量删除");
		try {
			departmentService.deleteAll(ids.split(","));
			return new Result(true,"删除成功");
		} catch (DataIntegrityViolationException e) {
			logger.error("院系删除，deleteIDs 删除处理 出错，无法删除关联对象！",e);
			return new Result(false,"删除失败");
		} catch (Exception e) {
			logger.error("院系删除，delete 删除处理 出错{}",e);
			return new Result(false,"删除失败");
		}
	}
	/**
	 * 修改界面
	 * @param mav
	 * @param id
	 * @return
	 */
	@RequestMapping("departmentUpdateUI/{id}")
	@LogCustom(desc="进行了院系修改页面")
	public ModelAndView departmentUpdateUI(ModelAndView mav,@PathVariable("id")String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行院系修改");
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){
				DepartmentVo department = departmentService.getById(Long.valueOf(decrypt));
				List<School> schoolList = schoolService.getAll();
				mav.addObject("schoolList", schoolList);
				mav.addObject("department", department);
				mav.setViewName("back/department/departmentUpdate");
			}
			return mav;
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("院系信息修改页面，departmentUpdateUI出错{}", e);
			return mav;
		}
	}
	/**
	 * @Title: departmentUpdate
	 * @Description: 更新信息
	 * @return Result 返回类型
	 * @throws
	 * @param mav
	 * @param department
	 * @return
	 */
	@RequestMapping("departmentUpdate")
	@ResponseBody
	public Result departmentUpdate(Depart department){
		try {
			departmentService.update(department);
			return new Result(true,"修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"修改失败");
		}
	}
}
