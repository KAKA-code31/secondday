package com.honestpeak.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.honestpeak.annotation.LogCustom;
import com.honestpeak.model.Depart;
import com.honestpeak.model.Major;
import com.honestpeak.model.Student;
import com.honestpeak.model.StudentClass;
import com.honestpeak.model.Teacher;
import com.honestpeak.model.User;
import com.honestpeak.result.Result;
import com.honestpeak.service.DepartmentService;
import com.honestpeak.service.MajorService;
import com.honestpeak.service.StudentClassService;
import com.honestpeak.service.StudentService;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.Encrypt;
import com.honestpeak.utils.FileOperateUtil;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.PropertyUtils;
import com.honestpeak.utils.QEncodeUtil;
import com.honestpeak.vo.StudentVo;

/**
 * @Title: StudentController.java 
 * @Package com.honestpeak.controller 
 * @Description: 学生信息管理 
 * @author BPC
 * @date 2017年7月24日 上午9:23:28 
 * @version V1.0
 */
@Controller
@RequestMapping("/back/student")
@LogCustom(desc = "学生信息管理")
public class StudentController extends BaseController {
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private MajorService majorService;
	@Autowired
	private StudentClassService studentClassService;
	@Autowired
	private StudentService studentService;
	
	private final static Logger logger = LoggerFactory.getLogger(StudentController.class);

	/**
	 * @param student
	 * @param enDepartId
	 * @param enMajorId
	 * @param enClassId
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "/manager")
	@LogCustom(desc = "获取了学生信息列表")
	public ModelAndView manager(Student student, String enDepartId, String enMajorId, String enClassId,
			Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行学生信息管理");
		ModelAndView mav = new ModelAndView();
		try {
			//初始化院系、专业、班级 搜索条件
			student = initStudentDepartInfo(student, enDepartId, enMajorId, enClassId);
			Page<StudentVo> page = studentService.findStudentPage(student, currentPage == null ? 1 : currentPage);
			// 列表项
			mav.addObject("studentList", page.getResultList());
			// 分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());

			// 院系信息查询条件
			mav.addObject("enDepartId", enDepartId);
			mav.addObject("enMajorId", enMajorId);
			mav.addObject("enClassId", enClassId);
			
			
			mav.addObject("departs", departmentService.getAll());

			mav.setViewName("/back/student/manager");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("学生管理，manager出错{}", e);
		}
		return mav;
	}
	
	/**
	 * @Title: initStudentDepartInfo
	 * @Description: 初始化学生院系、专业、班级 搜索条件。
	 * @param student 学生信息，不可为空
	 * @param enDepartId 院系加密id
	 * @param enMajorId 专业加密id
	 * @param enClassId 班级加密id
	 */
	private Student initStudentDepartInfo(Student student, String enDepartId, String enMajorId, String enClassId) {
		if(student==null){//student为空，直接返回空
			return null;
		}
		String code;//解密主键暂存
		//判断enDepartId是否为空，不为空判断其能否被解密，解密成功，添加院系查找条件
		//判断enMajorId是否为空，不为空判断其能否被解密，解密成功，添加专业查找条件
		//判断enClassId是否为空，不为空判断其能否被解密，解密成功，添加班级查找条件
		if(StringUtils.isNotBlank(enClassId) && StringUtils.isNoneBlank((code = QEncodeUtil.decryptId(enClassId))) ){
			student.setClassId(Long.valueOf(code));
		}
		
		return student;
	}
	/**
	 * 获取专业
	 * @param departId
	 * @return
	 */
	@RequestMapping(value = "/selectMajor/", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "获取专业列表！")
	public Object selectMajor(String departId) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取专业信息");
		try {
			if(StringUtils.isNotBlank(departId)){
				String decrypt = QEncodeUtil.decryptId(departId);
				List<Major> majorList =majorService.findMajorByDepartId(Long.valueOf(decrypt));
			    return majorList;
			}
			return null;
		} catch (Exception e) {
			logger.error("获取专业列表失败！", e);
			return null;
		}
	}
	/**
	 * 获取班级
	 * @param majorId
	 * @return
	 */
	@RequestMapping(value = "/selectClass/", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "获取班级列表！")
	public Object selectClass(String majorId) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取班级信息");
		try {
			if(StringUtils.isNotBlank(majorId)){
				String decrypt = QEncodeUtil.decryptId(majorId);
				List<StudentClass> classList =studentClassService.findClassByMajorId(Long.valueOf(decrypt));
			    return classList;
			}
			return null;
		} catch (Exception e) {
			logger.error("获取班级列表失败！", e);
			return null;
		}
	}
	
	/**
	 * @Title: addUI
	 * @Description: 前往添加学生信息页面
	 * @return
	 */
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	@LogCustom(desc = "前往添加学生信息页面！")
	public ModelAndView addUI() {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，添加学生信息页面。");
		ModelAndView mav = new ModelAndView();
		try {
			mav.addObject("departs", departmentService.getAll());
			mav.setViewName("back/student/addUI");
		} catch (Exception e) {
			logger.error("添加学生信息出错！", e);
			mav.setViewName("error/500");
		}
		return mav;
	}

	/**
	 * @Title: add
	 * @Description: 添加处理
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "添加学生信息！")
	public Object add(StudentVo studentVo) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，添加学生信息！");
		int res = 0;
		if(studentVo==null){
			return renderError("学生信息有误，请检查后重新填写。");
		}
		try {
			Student student = studentService.selectStudentClass(studentVo.getUserId());
			if(student!=null){
				return renderError("添加失败，该学号的学生已存在！");
			}
			res=studentService.addStudent(studentVo);
			if(res==1){
				return renderSuccess("添加学生信息成功！");
			}
			return renderError("添加学生信息失败！");
		} catch (Exception e) {
			logger.error("添加注册信息，add 添加处理出错{}", e);
			return renderError("添加失败，系统内部错误！");
		}
	}

	/**
	 * 
	 * @Title: deleteIDs
	 * @Description: 删除多个权限信息
	 * @return Object 返回类型
	 */
	@RequestMapping(value = "/deleteIDs", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "删除了多条学生信息！")
	public Object deleteIDs(String ids) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行多条学生信息删除");
		int res = 0;
		try {
			// 主键信息解密
			String[] keys = QEncodeUtil.decryptKeys(ids);
			if (keys != null && keys.length > 0) {
				res = studentService.deleteMore(keys);
			}
		} catch (DataIntegrityViolationException e) {
			logger.error("多条学生信息删除，deleteIDs 删除处理 出错，无法删除关联对象！", e);
			return renderError("删除失败，不能删除多条学生信息！");
		} catch (Exception e) {
			logger.error("多条学生信息删除，delete 删除处理 出错{}", e);
			return renderError("删除失败，系统内部出错！");
		}
		if (res == 0) {
			return renderError("删除失败，无法对多条教师信息进行删除！");
		}
		return renderSuccess("删除成功！");
	}

	/**
	 * @Title: delete
	 * @Description: 删除一条学生信息
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc = "删除了一条学生信息！")
	public Object delete(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行学生信息删除");
		int res = 0;
		try {
			String decrypt = QEncodeUtil.decryptId(id);// ID解密
			if (decrypt != null) {
				res = studentService.deleteStudent(Long.valueOf(decrypt));
			}
		} catch (DataIntegrityViolationException e) {
			logger.error("delete 删除处理 出错，无法删除关联对象！", e);
			return renderError("删除失败");
		} catch (Exception e) {
			logger.error("delete 删除处理 出错{}", e);
			return renderError("学生信息删除失败，系统内部出错！");
		}
		if (res == 0) {
			return renderError("学生信息删除失败，无法对该注册进行删除！");
		}
		return renderSuccess("注册信息删除成功！");
	}

	/**
	 * @Title: editUI
	 * @Description: 前往修改学生信息页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/editUI/{id}", method = RequestMethod.GET)
	@LogCustom(desc = "进入学生修改页面")
	public ModelAndView editUI(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，前往修改学生信息页面");
		ModelAndView mav = new ModelAndView();
		try {
			String decrypt = QEncodeUtil.decryptId(id);// ID解密
			if (decrypt == null) {
				logger.error("修改学生信息失败，系统无法解析表单信息！");
				mav.setViewName("error/400");
			} else {
				// 参数参数
				StudentVo studentVo = studentService.selectStudentVo(Long.valueOf(decrypt));
				List<StudentClass> studentClassList = studentClassService.selectAllClass();
				mav.addObject("studentClassList", studentClassList);
				if (studentVo != null) {
					mav.addObject("studentVo", studentVo);
					mav.setViewName("/back/student/editUI");
				}
			}
		} catch (Exception e) {
			logger.error("修改选课，editUI出错{}", e);
			mav.setViewName("error/500");
		}
		return mav;
	}

	/**
	 * @Title: edit
	 * @Description: 修改学生信息
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "进行了修改学生信息")
	public Object edit(StudentVo studentVo) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，修改学生信息");
		if(studentVo==null){
			return renderError("学生信息有误");
		}
		int res = 0;
		try {
			res = studentService.updateStudent(studentVo);
			if (res == 0) {
				return renderSuccess("修改学生信息成功！");
			}
			return renderSuccess("修改学生信息失败！");
		} catch (Exception e) {
			logger.error("修改学生信息出错，edit出错{}", e);
			return renderError("修改学生信息出错，系统内部出错！");
		}
	}
	
	/**
	 * @Title: changClassUI
	 * @Description: 前往修改学生信息页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/changClassUI/{id}", method = RequestMethod.GET)
	@LogCustom(desc = "进入学生班级修改页面")
	public ModelAndView changClassUI(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，前往修改学生信息页面");
		ModelAndView mav = new ModelAndView();
		try {
			String decrypt = QEncodeUtil.decryptId(id);// ID解密
			if (decrypt == null) {
				logger.error("修改学生班级失败，系统无法解析表单信息！");
				mav.setViewName("error/400");
			} else {
				// 参数参数
				StudentVo studentVo = studentService.selectStudentVoById(Long.valueOf(decrypt));
				List<Depart> departmentList=departmentService.getAll();
				mav.addObject("departmentList", departmentList);
				if (studentVo != null) {
					mav.addObject("studentVo", studentVo);
					mav.setViewName("/back/student/changClassUI");
				}
			}
		} catch (Exception e) {
			logger.error("修改选课，editUI出错{}", e);
			mav.setViewName("error/500");
		}
		return mav;
	}
	
	/**
	 * @Title: edit
	 * @Description: 修改学生信息
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value = "/changClass", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "进行了修改学生信息")
	public Object changClass(StudentVo studentVo, HttpServletRequest request) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，修改学生信息");
		if(studentVo==null){
			return renderError("信息有误");
		}
		int res = 0;
		try {
			Student student=studentService.selectStudent(studentVo.getUserId());
			
			if(studentVo.getClassId()!=null){
				if(studentVo.getClassId()!=student.getClassId()){
					res=studentService.updateClass(studentVo,request);					
				}else{
					return renderError("您没有修改班级！");
				}
			}
			if (res != 0) {
				return renderSuccess("修改学生信息成功！");
			}
			return renderSuccess("修改学生信息失败！");
		} catch (Exception e) {
			logger.error("修改学生信息出错，edit出错{}",e);
			return renderError("修改学生信息出错，系统内部出错！");
		}
	}
	
	@RequestMapping(value = "/resetUI/{id}", method = RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc = "重置学生密码！")
	public Object resetPassword(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，重置学生密码");
		int res = 0;
		try {
			String decrypt = QEncodeUtil.decryptId(id);// ID解密
			if (decrypt != null) {
				res = studentService.updateStudentPassWord(Long.valueOf(decrypt));
			}
			if(res==1){
				return renderSuccess("重置密码成功！密码为111333！");
			} else {
				return renderError("重置学生默认密码失败！");
			}
		} catch (Exception e) {
			logger.error("resetPassword失败", e);
			return renderError("重置学生默认密码失败，系统内部出错！");
		}
	}
	
	/**
	 * @Title: studentUploadUI
	 * @Description: 跳转至上传页面
	 * @return ModelAndView 返回类型
	 * @author zzy
	 * @throws @return
	 */
	@RequestMapping("/studentUploadUI")
	public ModelAndView studentUploadUI() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("back/student/studentUpload");
		return mav;

	}
	
	@RequestMapping("/import")
	@ResponseBody
	@LogCustom(desc="上传学生信息")
	public Result fileUpload(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request){
		try{
			String fileName = new Date().getTime()+file.getOriginalFilename();
	        String path= request.getSession().getServletContext().getRealPath("")+File.separator + "upload"+File.separator+fileName;		         
	        File newFile=new File(path);
	        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
	        file.transferTo(newFile);
			Result result = studentService.importStudent(path);
			if(path!=null){
				//上传成功后直接删除文件
				File file1 = new File(path);
				file1.delete();// "删除单个文件"+name+"成功！"
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "系统异常！");
		}
	}
	
	/**
	 * 学生端模版下载
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/excelDownload")
	public void excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("【下载学生模板开始】");
		String filePath = request.getSession().getServletContext().getRealPath("/upload"+File.separator+"template");
		File f = new File(filePath+File.separator+"student.xlsx");
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(("学生模板" + ".xlsx").getBytes(), "iso-8859-1"));// 下载文件的名称
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(f));
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	
	@RequestMapping(value="/pppUI/{id}",method=RequestMethod.GET)
	@LogCustom(desc="进入了个人信息修改页面")
	public ModelAndView editIdsUI(@PathVariable String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，去行个人信息修改页面");
		ModelAndView mav = new ModelAndView();
		try {
			User student = studentService.findUserById(getCurrentUser().getId());
			if (student != null) {
				student.setEncryptId(QEncodeUtil.encryptId(student.getId()));
				mav.addObject("student", student);
				mav.setViewName("/back/student/editIdsUI");
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
	@RequestMapping(value="/pppIds",method=RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc="进行了个人信息修改")
	public Object editIds(Student student){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行个人信息修改");
		int res = 0;
		try {
			String decr = student.decrypt();
			if (decr != null) {
				student.setId(Long.valueOf(decr));
			}
			student.setId(getUserId());
			
			//判断当前用户是否修改图片，若没有修改，把之前的路径再次赋值，以防路径错误
			res = studentService.updateByPrimaryKeySelective((Student) student);
		} catch (Exception e) {
			logger.error("学生修改，edit出错{}",e);
			return renderError("修改失败，系统内部出错！");
		}	
		if(res==0){
			return renderError("修改失败，无法对该学生进行修改！");
		}
		return renderSuccess("修改成功！");
	}
	
	@RequestMapping("/passwordUI/{id}")
	@LogCustom(desc="进入了用户的密码修改页面")
	public ModelAndView passwordManager(Long id) {
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行了用户密码修改页面");
		ModelAndView mav = new ModelAndView();
		try {
			User student=studentService.findUserById(getCurrentUser().getId());
			mav.addObject("student", student);
			mav.setViewName("back/student/password");
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
		User user = studentService.findUserById(getCurrentUser().getId());
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
			User user = studentService.findUserById(getCurrentUser().getId());
			String newP = Encrypt.SHA256(newPwd);
			user.setPassword(newP);
			studentService.updateByPrimaryKeySelective((Student) user);
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
