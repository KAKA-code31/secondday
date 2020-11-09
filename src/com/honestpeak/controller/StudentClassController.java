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
import com.honestpeak.model.School;
import com.honestpeak.model.StudentClass;
import com.honestpeak.result.Result;
import com.honestpeak.service.DepartmentService;
import com.honestpeak.service.MajorService;
import com.honestpeak.service.SchoolService;
import com.honestpeak.service.StudentClassService;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;
import com.honestpeak.vo.StudentClassVo;

/**
 * 
 * @author bpc
 *
 */
@Controller
@RequestMapping("back/studentClass")
public class StudentClassController  extends BaseController{
	@Resource
	private StudentClassService studentClassService;
	@Resource
	private MajorService majorService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private SchoolService schoolService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("manager")
	public ModelAndView classList(ModelAndView mav,Integer currentPage,Long schoolId,Long majorId ,Long departmentId,Long classId,String grade,StudentClassVo studentClassVo){
		try{
			Page<StudentClassVo> page = studentClassService.getAllForPage(currentPage == null ? 1:currentPage,schoolId,departmentId,majorId,classId,grade,studentClassVo);
			mav.addObject("classList", page.getResultList());
			initPageParams(mav, page);
			List<School> schoolList=schoolService.getAll();
			mav.addObject("studentClassVo", studentClassVo);
			mav.addObject("schoolList", schoolList);
			mav.addObject("classId", classId);
			mav.addObject("majorId",majorId);
			mav.addObject("departmentId",departmentId);
			mav.addObject("schoolId",schoolId);
			mav.setViewName("back/studentClass/manager");
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			mav = new ModelAndView("/error/500");
			logger.error("班级信息管理，manager出错{}", e);
		}
		return mav;
	}
	/**
	 * 修改界面
	 * @param mav
	 * @param id
	 * @return
	 */
	@RequestMapping("studentClassUpdateUI/{id}")
	@LogCustom(desc="进行了班级修改页面")
	public ModelAndView studentClassUpdateUI(ModelAndView mav,@PathVariable("id")String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行专业修改");
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){
				StudentClassVo studentClassVo = studentClassService.getById(Long.valueOf(decrypt));
				List<School> schoolList=schoolService.getAll();
				List<Depart> departmentList=departmentService.getAll();
				List<Major> majorList=majorService.getAll();
				List<Integer> yearList = getIntYearList();
				mav.addObject("schoolList", schoolList);
				mav.addObject("departmentList", departmentList);
				mav.addObject("majorList", majorList);
				mav.addObject("yearList", yearList);
				mav.addObject("studentClassVo", studentClassVo);
				mav.setViewName("back/studentClass/classUpdate");
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
	@RequestMapping("studentClassUpdate")
	@ResponseBody
	public Result studentClassUpdate(StudentClassVo studentClassVo){
		try {
			studentClassService.update(studentClassVo);
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
	@RequestMapping(value="studentClassDelete/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了单个院系删除")
	public Result studentClassDelete(@PathVariable("id")String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行班级删除，删除项："+id);
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){
				studentClassService.delete(Long.valueOf(decrypt));
			}
			return new Result(true,"删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("班级删除,majorDelete出错{}");
			return new Result(false,"删除失败");
		}
	}
	@RequestMapping("studentClassDeleteIDs")
	@ResponseBody
	@LogCustom(desc="进行了班级批量删除")
	public Result studentClassDeleteIDs(String ids){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行班级批量删除");
		try {
			studentClassService.deleteAll(ids.split(","));
			return new Result(true,"删除成功");
		} catch (DataIntegrityViolationException e) {
			logger.error("班级删除，deleteIDs 删除处理 出错，无法删除关联对象！",e);
			return new Result(false,"删除失败");
		} catch (Exception e) {
			logger.error("班级删除，delete 删除处理 出错{}",e);
			return new Result(false,"删除失败");
		}
	}
	@RequestMapping("studentClassAddUI")
	public ModelAndView studentClassAddUI(ModelAndView mav){
		List<School> schoolList=schoolService.getAll();
		List<Depart> departmentList=departmentService.getAll();
		List<Major> majorList=majorService.getAll();
		mav.addObject("schoolList", schoolList);
		mav.addObject("departmentList", departmentList);
		mav.addObject("majorList", majorList);
		mav.setViewName("back/studentClass/classAdd");
		return mav;
	}
	/**
	 * 班级添加
	 * @param mav
	 * @param studentClass
	 * @return
	 */
	@RequestMapping("studentClassAdd")
	@ResponseBody
	@LogCustom(desc="进行了单个班级添加")
	public Result majorAdd(ModelAndView mav, StudentClassVo studentClass){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，添加班级信息！");
		try {
			studentClassService.add(studentClass);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			logger.error("专业添加,majorAdd出错{}");
			return new Result(false, "添加失败,班级编号不能重复");
		}
	}
	/**
	 * 根据学校选择院系联动
	 */
	@RequestMapping("seletDepartment")
	@ResponseBody
	@LogCustom(desc="院系联动")
	public Object seletDepartment(Long schoolId){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取院系信息！");
		try {
		if(schoolId!=null){
				List<Depart> departmentList=departmentService.findDepartmentBySchoolId(schoolId);
				return renderSuccess(departmentList);
		}
		} catch (Exception e) {
			logger.error("获取专业失败！", e);
			return renderError("获取专业失败！");
		}
		return renderSuccess("没有专业信息！");
	}
	/**
	 * 根据院系选择专业联动
	 */
	@RequestMapping("seletMajor")
	@ResponseBody
	@LogCustom(desc="专业联动")
	public Object seletMajor(Long departmentId){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取专业信息！");
		try {
		if(departmentId!=null){
				List<Major> majorList=majorService.findMajorByDepartId(departmentId);
				return renderSuccess(majorList);
		}
		} catch (Exception e) {
			logger.error("获取专业失败！", e);
			return renderError("获取专业失败！");
		}
		return renderSuccess("没有专业信息！");
	}
	/**
	 * 根据专业联动班级
	 */
	@RequestMapping("seletClass")
	@ResponseBody
	@LogCustom(desc="专业联动")
	public Object seletClass(Long majorId){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取班级信息！");
		try {
		if(majorId!=null){
				List<StudentClass> studentClassList=studentClassService.findClassByMajorId(majorId);
				return renderSuccess(studentClassList);
		}
		} catch (Exception e) {
			logger.error("获取专业失败！", e);
			return renderError("获取专业失败！");
		}
		return renderSuccess("没有专业信息！");
	}
	
	/**
	 * @Title: findClassByMajorJson
	 * @Description: 根据专业加密ID, 获取班级信息列表
	 * @return
	 */
	@RequestMapping("findClassByMajorJson")
	@ResponseBody
	public Object findClassByMajorJson(String majorId){
		String decrypt = QEncodeUtil.decryptId(majorId);//ID解密
		if(decrypt!=null){
			List< StudentClass> classes = studentClassService.findClassByMajorId(Long.valueOf(decrypt));
			return renderSuccess(classes);
		}else{
			//ID解密失败，返回null
			return renderError("表单数据解析失败！");
		}
	}
	
	
	/**
	 * 院系和班级联动
	 * @param departmentId
	 * @return
	 */
	@RequestMapping("/selectClassByDepartId")
	@ResponseBody
	public Object selectClass(String departId){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，院系和班级联动！");
		try {
		if(departId!=null){
			List<StudentClass> studentClassList=studentClassService.findClassByDepartmentId(departId);
			return studentClassList;
		}
		} catch (Exception e) {
			logger.error("获取班级失败！", e);
			return renderError("获取班级失败！");
		}
		return renderSuccess("没有班级信息！");
	}
	/**
	 * 判断班级编号和名称
	 * 
	 */
	@RequestMapping("seletClassByCode")
	@ResponseBody
	@LogCustom(desc="获取班级编号")
	public Object selectClassCode(String classCode) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取班级编号或名称！");
		try {
			String bh=classCode.trim();
			if(bh!=null && bh!="") {
				StudentClass studentClass=studentClassService.selectClassByCode(bh);
				if(studentClass!=null) {
					return renderError("该班级编号已存在,请重新编号");
				}
				return renderSuccess("");
			}
			return 1;
		}catch(Exception e){
			logger.error("获取班级编号出错！", e);
			return renderError("获取班级编号出错！");
		}
		
	}
	/**
	 * 判断班级名称
	 * 
	 */
	@RequestMapping("seletClassByName")
	@ResponseBody
	@LogCustom(desc="获取班级编号")
	public Object selectClassName(String className) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取班级名称！");
		try {
			String name=className.trim();
			if(name!=null && name!="") {
				StudentClass studentClass=studentClassService.selectClassByName(name);
				if(studentClass!=null) {
					return renderError("该班级名称已存在,请重新编号");
				}
				return renderSuccess("");
			}
			return 1;
		}catch(Exception e){
			logger.error("获取班级名称出错！", e);
			return renderError("获取班级名称出错！");
		}
		
	}
}
