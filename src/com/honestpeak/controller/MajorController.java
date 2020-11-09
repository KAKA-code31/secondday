package com.honestpeak.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.honestpeak.annotation.LogCustom;
import com.honestpeak.model.Depart;
import com.honestpeak.model.Major;
import com.honestpeak.result.Result;
import com.honestpeak.service.DepartmentService;
import com.honestpeak.service.MajorService;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;
import com.honestpeak.vo.MajorVo;

@Controller
@RequestMapping("back/major")
public class MajorController extends BaseController{
	@Resource
	private MajorService majorService;
	@Resource
	private DepartmentService departmentService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("manager")
	public ModelAndView majorList(ModelAndView mav,Integer currentPage,String departmentName, MajorVo major){
		try{
			Page<MajorVo> page = majorService.getAllForPage(currentPage == null ? 1:currentPage,departmentName, major);
			mav.addObject("majorList", page.getResultList());
			initPageParams(mav, page);
			mav.addObject("major", major);
			mav.addObject("departmentName", departmentName);
			mav.setViewName("back/major/manager");
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			mav = new ModelAndView("/error/500");
			logger.error("专业信息管理，manager出错{}", e);
		}
		return mav;
	}
	@RequestMapping("majorDeleteIDs")
	@ResponseBody
	@LogCustom(desc="进行了专业批量删除")
	public Result departmentDeleteIDs(String ids){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行专业批量删除");
		try {
			majorService.deleteAll(ids.split(","));
			return new Result(true,"删除成功");
		} catch (DataIntegrityViolationException e) {
			logger.error("专业删除，deleteIDs 删除处理 出错，无法删除关联对象！",e);
			return new Result(false,"删除失败");
		} catch (Exception e) {
			logger.error("专业删除，delete 删除处理 出错{}",e);
			return new Result(false,"删除失败");
		}
	}
	/**
	 * 修改界面
	 * @param mav
	 * @param id
	 * @return
	 */
	@RequestMapping("majorUpdateUI/{id}")
	@LogCustom(desc="进行了专业修改页面")
	public ModelAndView majorUpdateUI(ModelAndView mav,@PathVariable("id")String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行专业修改");
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){
				MajorVo major = majorService.getById(Long.valueOf(decrypt));
				List<Depart> departmentList=departmentService.getAll();
				mav.addObject("departmentList", departmentList);
				mav.addObject("major", major);
				mav.setViewName("back/major/majorUpdate");
			}
			return mav;
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("院系信息修改页面，departmentUpdateUI出错{}", e);
			return mav;
		}
	}
	/**
	 * @Title:majorUpdate
	 * @Description: 更新信息
	 * @return Result 返回类型
	 * @throws
	 * @param mav
	 * @param department
	 * @return
	 */
	@RequestMapping("majorUpdate")
	@ResponseBody
	public Result departmentUpdate(MajorVo major){
		try {
			majorService.update(major);
			return new Result(true,"修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"修改失败");
		}
	}
	/**
	 * 删除专业
	 * @param id
	 * @return
	 */
	@RequestMapping(value="majorDelete/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了单个院系删除")
	public Result majorDelete(@PathVariable("id")String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行专业删除，删除项："+id);
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){
				majorService.delete(Long.valueOf(decrypt));
			}
			return new Result(true,"删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("院系删除,majorDelete出错{}");
			return new Result(false,"删除失败");
		}
	}
	@RequestMapping("majorAddUI")
	public ModelAndView majorAddUI(ModelAndView mav){
		List<Depart> departmentList=departmentService.getAll();
		mav.addObject("departmentList", departmentList);
		mav.setViewName("back/major/majorAdd");
		return mav;
	}
	/**
	 * 院系添加
	 * @param mav
	 * @param department
	 * @return
	 */
	@RequestMapping("majorAdd")
	@ResponseBody
	@LogCustom(desc="进行了单个院系添加")
	public Result majorAdd(ModelAndView mav, MajorVo major){
		try {
			majorService.addMajorVo(major);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			logger.error("专业添加,majorAdd出错{}");
			e.printStackTrace();
			return new Result(false, "添加失败,专业编号不能重复");
		}
	}
	
	/**
	 * @Title: findMajorBydepartJson
	 * @Description: 通过departId，找出该院系下的所有专业列表
	 * @return
	 */
	@RequestMapping("findMajorBydepartJson")
	@ResponseBody
	public Object findMajorBydepartJson(String departId){
		String decrypt = QEncodeUtil.decryptId(departId);//ID解密
		if(decrypt!=null){
			List<Major> majors = majorService.findMajorByDepartId(Long.valueOf(decrypt));
			return renderSuccess(majors);
		}else{
			//ID解密失败，返回null
			return renderError("表单数据解析失败！");
		}
	}
}
