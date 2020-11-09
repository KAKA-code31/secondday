package com.honestpeak.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import com.honestpeak.model.Teacher;
import com.honestpeak.service.DepartmentService;
import com.honestpeak.service.RoleService;
import com.honestpeak.service.SchoolService;
import com.honestpeak.service.TeacherService;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.Encrypt;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;
import com.honestpeak.vo.TeacherVo;

@Controller
@RequestMapping("/back/teacher")
@LogCustom(desc="教师信息管理")
public class TeacherController extends BaseController{
	@Autowired
	private RoleService roleService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private TeacherService teacherService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/manager")
	@LogCustom(desc="获取了教师列表")
	public ModelAndView manager(TeacherVo user, Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行教师管理");
		ModelAndView mav = new ModelAndView();
		try {
			Page<TeacherVo> page = teacherService.findTeacherPage(user, currentPage == null ? 1 : currentPage);
			// 列表项
			mav.addObject("teacherList", page.getResultList());
			// 分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());
			// 搜索条件
			mav.addObject("user", user);
			mav.setViewName("back/teacher/manager");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("教师信息管理，manager出错{}", e);
		}
		return mav;
	}
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc = "删除了一条教师信息！")
	public Object delete(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行教师信息删除");
		int res = 0;
		try {
			String decrypt = QEncodeUtil.decryptId(id);// ID解密
			if (decrypt != null) {
				res = teacherService.deleteTeacher(Long.valueOf(decrypt));
			}
		} catch (DataIntegrityViolationException e) {
			logger.error("delete 删除处理 出错，无法删除关联对象！", e);
			return renderError("删除失败");
		} catch (Exception e) {
			logger.error("delete 删除处理 出错{}", e);
			return renderError("教师信息删除失败，系统内部出错！");
		}
		if (res == 0) {
			return renderError("教师信息删除失败，无法对该注册进行删除！");
		}
		return renderSuccess("教师信息删除成功！");
	}
	
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	@LogCustom(desc = "前往添加教师信息页面！")
	public ModelAndView addUI() {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，添加教师信息页面。");
		ModelAndView mav = new ModelAndView();
		try {
			mav.addObject("schools", schoolService.getAll());
			mav.setViewName("back/teacher/addUI");
		} catch (Exception e) {
			logger.error("添加教师信息出错！", e);
			mav.setViewName("error/500");
		}
		return mav;
	}
	
	@RequestMapping(value = "/selectDepart", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "获取院系列表！")
	public Object selectDepart(Long schoolId) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取院系信息");
		try {
			List<Depart> departList =departmentService.findDepartmentBySchoolId(schoolId);
		    return departList;
		} catch (Exception e) {
			logger.error("获取院系列表失败！", e);
			return null;
		}
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "添加教师信息！")
	public Object add(TeacherVo teacherVo) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，添加教师信息！");
		int res = 0;
		if(teacherVo==null){
			return renderError("教师信息有误，请检查后重新填写。");
		}
		try {
			Teacher teacher = teacherService.selectTeacher(teacherVo.getUserId());
			if(teacher != null){
				return renderError("添加失败，该职工号已经存在！");
			}
			res=teacherService.addTeacher(teacherVo);
			if(res==1){
				return renderSuccess("添加教师信息成功！");
			}
			return renderError("添加教师信息失败！");
		} catch (Exception e) {
			logger.error("添加教师信息，add 添加处理出错{}", e);
			return renderError("添加失败，系统内部错误！");
		}
	}
	
	@RequestMapping(value = "/resetUI/{id}", method = RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc = "重置教师密码！")
	public Object resetPassword(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，重置教师密码");
		int res = 0;
		try {
			String decrypt = QEncodeUtil.decryptId(id);// ID解密
			if (decrypt != null) {
				res = teacherService.updateTeacherPassWord(Long.valueOf(decrypt));
			}
			if(res==1){
				return renderSuccess("重置密码成功！密码为111333！");
			} else {
				return renderError("重置教师默认密码失败！");
			}
		} catch (Exception e) {
			logger.error("resetPassword失败", e);
			return renderError("重置教师默认密码失败，系统内部出错！");
		}
	}
	
	@RequestMapping(value = "/editUI/{id}", method = RequestMethod.GET)
	@LogCustom(desc = "进入教师修改页面")
	public ModelAndView editUI(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，前往修改教师信息页面");
		ModelAndView mav = new ModelAndView();
		try {
			String decrypt = QEncodeUtil.decryptId(id);// ID解密
			if (decrypt == null) {
				logger.error("修改教师信息失败，系统无法解析表单信息！");
				mav.setViewName("error/400");
			} else {
				TeacherVo teacherVo = teacherService.selectTeacherVo(Long.valueOf(decrypt));
				List<School> schoolList = schoolService.getAll();
				mav.addObject("schoolList", schoolList);
				if (teacherVo != null) {
					mav.addObject("teacherVo", teacherVo);
					mav.setViewName("/back/teacher/editUI");
				}
			}
		} catch (Exception e) {
			logger.error("修改教师，editUI出错{}", e);
			mav.setViewName("error/500");
		}
		return mav;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "进行了修改教师信息")
	public Object edit(TeacherVo teacherVo) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，修改教师信息");
		if(teacherVo==null){
			return renderError("教师信息有误");
		}
		int res = 0;
		try {
			res = teacherService.updateTeacher(teacherVo);
			if (res == 0) {
				return renderSuccess("修改教师信息成功！");
			}
			return renderSuccess("修改教师信息失败！");
		} catch (Exception e) {
			logger.error("修改教师信息出错，edit出错{}", e);
			return renderError("修改教师信息出错，系统内部出错！");
		}
	}
	
	@RequestMapping(value = "/deleteIDs", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "删除了多条教师信息！")
	public Object deleteIDs(String ids) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行多条教师信息删除");
		int res = 0;
		try {
			// 主键信息解密
			String[] keys = QEncodeUtil.decryptKeys(ids);
			if (keys != null && keys.length > 0) {
				res = teacherService.deleteMore(keys);
			}
		} catch (DataIntegrityViolationException e) {
			logger.error("多条教师信息删除，deleteIDs 删除处理 出错，无法删除关联对象！", e);
			return renderError("删除失败，不能删除多条教师信息！");
		} catch (Exception e) {
			logger.error("多条教师信息删除，delete 删除处理 出错{}", e);
			return renderError("删除失败，系统内部出错！");
		}
		if (res == 0) {
			return renderError("删除失败，无法对多条教师信息进行删除！");
		}
		return renderSuccess("删除成功！");
	}
	@RequestMapping(value="/ppUI/{id}",method=RequestMethod.GET)
	@LogCustom(desc="进入了个人信息修改页面")
	public ModelAndView editIdsUI(@PathVariable String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，去行个人信息修改页面");
		ModelAndView mav = new ModelAndView();
		try {
			Teacher teacher = teacherService.findUserById(getCurrentUser().getId());
			if (teacher != null) {
				teacher.setEncryptId(QEncodeUtil.encryptId(teacher.getId()));
				mav.addObject("teacher", teacher);
				mav.setViewName("/back/teacher/editIdsUI");
			} 
		} catch (Exception e) {
			logger.error("个人信息修改，editUI出错{}", e);
			mav.setViewName("error/500");
		}
		return mav;
	}
	
	/**
	 * @Title: editIds
	 * @Description: 个人信息编辑处理
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value="/ppIds",method=RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc="进行了个人信息修改")
	public Object editIds(Teacher teacher){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行个人信息修改");
		int res = 0;
		try {
			Teacher currentTeacher = teacherService.findUserById(getUserId()); 
			String decr = teacher.decrypt();
			if (decr != null) {
				teacher.setId(Long.valueOf(decr));
			}
			teacher.setId(getUserId());
			
			//判断当前用户是否修改图片，若没有修改，把之前的路径再次赋值，以防路径错误
			res = teacherService.update(teacher);
		} catch (Exception e) {
			logger.error("教师修改，edit出错{}",e);
			return renderError("修改失败，系统内部出错！");
		}	
		if(res==0){
			return renderError("修改失败，无法对该教师进行修改！");
		}
		return renderSuccess("修改成功！");
	}
	
	@RequestMapping("/passwordUI/{id}")
	@LogCustom(desc="进入了用户的密码修改页面")
	public ModelAndView passwordManager(Long id) {
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行了用户密码修改页面");
		ModelAndView mav = new ModelAndView();
		try {
			Teacher teacher=teacherService.findUserById(getCurrentUser().getId());
			mav.addObject("teacher", teacher);
			mav.setViewName("back/teacher/password");
			return mav;
		} catch (Exception e) {
			logger.error("用户密码修改页面，passwordUI出错{}", e);
			e.printStackTrace();
			mav = new ModelAndView("common/error");
			mav.addObject("error", "有异常！");
			return mav;
		}
	}
	
	/**
	 * 
	 * @Title: checkoldPwd
	 * @Description: 检验旧密码
	 * @return Object 返回类型
	 */
	@RequestMapping("checkoldPwd")
	@ResponseBody
	public Object checkoldPwd(String oldPwd) {
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行检验旧密码");
		Teacher user = teacherService.findUserById(getCurrentUser().getId());
		String oldP = Encrypt.SHA256(oldPwd);
		if(oldP.equals(user.getPassword())){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: editPassword
	 * @Description: 执行用户密码修改
	 * @return Object 返回类型
	 */
	@RequestMapping("password")
	@ResponseBody
	@LogCustom(desc="进行了用户的密码修改")
	public Object editPassword(String newPwd){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行用户密码修改");
		try {
			Teacher user = teacherService.findUserById(getCurrentUser().getId());
			String newP = Encrypt.SHA256(newPwd);
			user.setPassword(newP);
			teacherService.update(user);
			Subject subject = SecurityUtils.getSubject();
	        subject.logout();
			return renderSuccess("密码修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("密码修改失败：editPassword出错{}",e);
			return renderError("密码修改失败，系统内部出错！");
		}
	}
}


