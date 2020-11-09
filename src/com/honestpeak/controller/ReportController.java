package com.honestpeak.controller;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.IOUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.aspectj.weaver.ast.Test;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang.StringUtils;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.honestpeak.annotation.LogCustom;
import com.honestpeak.mapper.ExperimentMapper;
import com.honestpeak.mapper.ExperimentTeacherMapper;
import com.honestpeak.mapper.SelectCourseMapper;
import com.honestpeak.mapper.StudentClassMapper;
import com.honestpeak.model.Course;
import com.honestpeak.model.Depart;
import com.honestpeak.model.Experiment;
import com.honestpeak.model.Judge;
import com.honestpeak.model.Major;
import com.honestpeak.model.Student;
import com.honestpeak.model.StudentClass;
import com.honestpeak.model.Teacher;
import com.honestpeak.result.Result;
import com.honestpeak.result.Tree;
import com.honestpeak.service.CourseService;
import com.honestpeak.service.DepartmentService;
import com.honestpeak.service.ExperimentService;
import com.honestpeak.service.JudgeService;
import com.honestpeak.service.MajorService;
import com.honestpeak.service.ScoreService;
import com.honestpeak.service.SelectCourseService;
import com.honestpeak.service.StudentClassService;
import com.honestpeak.service.StudentService;
import com.honestpeak.service.UserRoleService;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.FileOperateUtil;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;
import com.honestpeak.utils.WriteExcelUtils;
import com.honestpeak.utils.ZipUtil;
import com.honestpeak.vo.ExperimentVo;
import com.honestpeak.vo.SelectCourseVo;
/**
 * 
 * @author bpc
 *
 */
@Controller
@RequestMapping("/back/report")
@LogCustom(desc = "报告管理")
public class ReportController extends BaseController{
	@Autowired
	private SelectCourseService selectCourseService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private MajorService majorService;
	@Autowired
	private StudentClassService studentClassService;
	@Autowired
	private StudentClassMapper studClassMapper;
	@Autowired
	private ExperimentMapper experimentMapper;
	@Autowired
	private CourseService courseService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private JudgeService judgeService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ExperimentService experimentService;
	@Autowired
	private ExperimentTeacherMapper experimentTeacherMapper;
	
	@Autowired
	private SelectCourseMapper selectCourseMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/submitList")
	@LogCustom(desc = "获取了课程列表查看")
	public ModelAndView submitList( SelectCourseVo selectCourseVo,Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行课程列表查看");
		ModelAndView mav = new ModelAndView();
		try {
			if(getCurrentUser().getAdminType() == 0){
				selectCourseVo.setStudentId(getUserId());
			}else{
				int count = userRoleService.countByTeacherId(getUserId());
				/*int count = selectCourseService.countByExprimentTeacherId(getUserId());*/
				if(count != 0)
					selectCourseVo.setTeacherId(teacherService.getUserIdById(getUserId()));
			}
			Page<SelectCourseVo> page = selectCourseService.selectCoursePage(selectCourseVo, currentPage == null ? 1 : currentPage);
			// 列表项
			mav.addObject("selectCourseList", page.getResultList());
			// 分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());

			// 搜索条件
			mav.addObject("selectCourseVo", selectCourseVo);

			mav.setViewName("back/selectCourse/manager");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("个人课程列表查看，submitList出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
	@LogCustom(desc = "查看报告提交记录！")
	public ModelAndView search(ExperimentVo experimentVo,@PathVariable("id") Long id,Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，查看报告提交记录");
		ModelAndView mav = new ModelAndView();
		try{
			if(getCurrentUser().getAdminType() == 0){
				experimentVo.setStudentId(getUserId());
			}else{
				int count = userRoleService.countByTeacherId(getUserId());
				if(count != 0)
					experimentVo.setTeacherId(teacherService.getUserIdById(getUserId()));
			}
			Page<ExperimentVo> page = selectCourseService.selectExperiments(experimentVo,id,currentPage == null ? 1 : currentPage);
			List<Depart> departmentList = departmentService.selectAllDepart();
			List<ExperimentVo> experimentListof = selectCourseService.selectExperimentsByCourseId(id);
			mav.addObject("departmentList", departmentList);
			mav.addObject("experimentListof", experimentListof);
			mav.addObject("experimentList", page.getResultList());
			mav.addObject("experimentId",experimentVo.getExperimentId());
			mav.addObject("departId",experimentVo.getDepartId());
			mav.addObject("majorId",experimentVo.getMajorId());
			mav.addObject("classId",experimentVo.getClassId());
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());
			mav.addObject("id", id);
			// 搜索条件
			mav.addObject("experimentVo", experimentVo);
			mav.setViewName("back/selectCourse/detail");
		} catch (Exception e) {
			mav = new ModelAndView("error/500");
			e.printStackTrace();
			logger.error("查看报告提交记录，search出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping(value = "/submitUI/{id}", method = RequestMethod.GET)
	public ModelAndView submitUI(ModelAndView mv,@PathVariable("id")Long id) {
		ExperimentVo experiment = selectCourseService.selectExperimentBySid(id);
		mv.addObject("experiment", experiment);
		mv.addObject("id", id);
		mv.setViewName("/back/selectCourse/submitUI");
		return mv;
	}
	@RequestMapping(value = "/updateUI/{id}", method = RequestMethod.GET)
	public ModelAndView updateUI(ModelAndView mv,@PathVariable("id")Long id) {
		ExperimentVo experiment = selectCourseService.selectExperimentBySid(id);
		mv.addObject("experiment", experiment);
		mv.addObject("id", id);
		mv.setViewName("/back/selectCourse/updateUI");
		return mv;
	}

	@RequestMapping(value="/update")
	@ResponseBody
	@LogCustom(desc="报告修改！")
	public Object update( @RequestParam(value="attachs",required=false)CommonsMultipartFile[] attachs,
			ExperimentVo experimentVo, HttpServletRequest request) {
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行报告提交");
		try {
			String studentNo=studentService.selectStudentxh(getUserId());
			//定义两个上传文件的路径
	        String reportpath = null;
	        String picturepath = null;
	        String sourcecodepath = null;
	        //定义上传过程管理标记
	        boolean flag = true;
	        //定义文件保存的位置
	        String path = request.getSession().getServletContext().getRealPath("");
	        File directory = new File(path);
	        File file = new File(directory.getParentFile().toString()).getParentFile();
	        path =  file.toString();
	        path = path + "\\file\\"+experimentVo.getClassId()+File.separator+experimentVo.getEtId();
	        /*String sqlPath = File.separator+"statics"+File.separator+"uploadfiles";*/
	        //循环读取文件信息
	        for(int i=0;i<attachs.length;i++){
	        	CommonsMultipartFile attach = attachs[i];
	            //获取源文件名
	            String fileName= attach.getOriginalFilename();
	            if(fileName == null ||fileName == ""){
	            	continue;
	            }
	            fileName=createFileName(fileName,i,experimentVo,studentNo);
	            String tempPath = null;
                //创建新的文件，用于接收用户上传的文件流
	            if(i==0){
	            	tempPath = path + File.separator + "report\\";
                }else if(i==1){
                	tempPath = path + File.separator + "picture\\";
                }else{
                	tempPath = path + File.separator+ "sourcecode\\";
                }
                File targetFile = new File(tempPath, fileName);
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }
                //将上传的文件保存
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    flag = false;
                } 
                //更新上传的路径
                if(i==0){
                	reportpath = tempPath + fileName;
                }else if(i==1){
                	picturepath = tempPath +  fileName;
                }else{
                	sourcecodepath = tempPath + fileName;
                }
	        }        
	        //准备experimentVo
	        if(flag){
	        	if(reportpath != null && reportpath != ""){
	        		experimentVo.setReportPath(reportpath);
	        	}
	        	if(picturepath != null && picturepath != ""){
	        		experimentVo.setPicturePath(picturepath);
	        	}
	        	if(sourcecodepath != null && sourcecodepath != ""){
	        		experimentVo.setSourcecodePath(sourcecodepath);
	        	}
	        }  
	        selectCourseService.updateScore(experimentVo);
		} catch (Exception e) {
			logger.error("我的提交添加失败，add出错{}", e);
			e.printStackTrace();
			return renderError("保存失败，系统内部错误！");
		}
		return renderSuccess("保存成功!");
	}
	
	@RequestMapping(value="/submit")
	@ResponseBody
	@LogCustom(desc="报告提交！")
	public Object submit( @RequestParam(value="attachs",required=false)CommonsMultipartFile[] attachs,
			ExperimentVo experimentVo, HttpServletRequest request) {
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行报告提交");
		try {
			//定义两个上传文件的路径
	        String reportpath = null;
	        String picturepath = null;
	        String sourcecodepath = null;
	        //定义上传过程管理标记
	        boolean flag = true;
	        //定义文件保存的位置
	        String path = request.getSession().getServletContext().getRealPath("");
	        File directory = new File(path);
	        File file = new File(directory.getParentFile().toString()).getParentFile();
	        path =  file.toString();
	        path = path + "\\file\\"+experimentVo.getClassId()+File.separator+experimentVo.getEtId();
	        /*String sqlPath = File.separator+"statics"+File.separator+"uploadfiles";*/
	        //循环读取文件信息
	        for(int i=0;i<attachs.length;i++){
	        	CommonsMultipartFile attach = attachs[i];
	            //获取源文件名
	            String fileName= attach.getOriginalFilename();
	            String tempPath = null;
                //创建新的文件，用于接收用户上传的文件流
	            if(i==0){
	            	tempPath = path + File.separator + "report\\";
                }else if(i==1){
                	tempPath = path + File.separator + "picture\\";
                }else{
                	tempPath = path + File.separator+ "sourcecode\\";
                }
                File targetFile = new File(tempPath, fileName);
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }
                //将上传的文件保存
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    flag = false;
                } 
                //更新上传的路径
                if(i==0){
                	reportpath = tempPath + fileName;
                }else if(i==1){
                	picturepath = tempPath +  fileName;
                }else{
                	sourcecodepath = tempPath + fileName;
                }
	        }        
	        //准备experimentVo
	        if(flag){
	        	experimentVo.setReportPath(reportpath);
	        	experimentVo.setPicturePath(picturepath);
	        	experimentVo.setSourcecodePath(sourcecodepath);
	        }  
	        selectCourseService.insertintoScore(experimentVo);
	        selectCourseService.updateStatus(experimentVo.getId());
		} catch (Exception e) {
			logger.error("我的提交添加失败，add出错{}", e);
			e.printStackTrace();
			return renderError("保存失败，系统内部错误！");
		}
		return renderSuccess("保存成功!");
	}
	
	@RequestMapping(value = "/selectClass")
	@LogCustom(desc = "获取了学生选课列表")
	public ModelAndView selectClass( ExperimentVo experimentVo,Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行选课列表查看");
		ModelAndView mav = new ModelAndView();
		try {
			if(getCurrentUser().getAdminType() == 0){
				experimentVo.setStudentId(getUserId());
			}else{
				int count = userRoleService.countByTeacherId(getUserId());
				if(count != 0)
					experimentVo.setTeacherId(teacherService.getUserIdById(getUserId()));
			}
			Page<ExperimentVo> page = selectCourseService.selectStudentPage(experimentVo, currentPage == null ? 1 : currentPage);
			// 列表项
			mav.addObject("selectStudentList", page.getResultList());
			// 分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());

			// 搜索条件
			mav.addObject("experimentVo", experimentVo);

			mav.setViewName("back/selectCourse/selectStudent");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("个人选课列表查看，submitList出错{}", e);
		}
		return mav;
	}
	/**
	 * 添加班级学生
	 * @param mav
	 * @return
	 */	
	@RequestMapping("/selectCourseAddUI")
	public ModelAndView selectCourseAddUI(ModelAndView mav){
		List<Course> courseList = courseService.selectAllCourse(getUserId());
		mav.addObject("courseList", courseList);
		List<Depart> departmentList = departmentService.selectAllDepart();
		mav.addObject("departmentList", departmentList);
		mav.setViewName("back/selectCourse/selectCourseAdd");
		return mav;
	}
	/**
	 * 添加重修学生
	 * @param mav
	 * @return
	 */
	@RequestMapping("/selectCourseAddUI1")
	public ModelAndView selectCourseAddUI1(ModelAndView mav){
		List<Course> courseList = courseService.selectAllCourse(getUserId());
		mav.addObject("courseList", courseList);
		List<Depart> departmentList = departmentService.selectAllDepart();
		mav.addObject("departmentList", departmentList);
		mav.setViewName("back/selectCourse/selectCourseAdd1");
		return mav;
	}
	/**
	 * 课程设计关联学生
	 * @param mav
	 * @return
	 */
	@RequestMapping("/selectCourseDesignAddUI")
	public ModelAndView selectCourseDesignAddUI(ModelAndView mav){
		List<Course> courseList = courseService.selectAllCourse(getUserId());
		mav.addObject("courseList", courseList);
		List<Depart> departmentList = departmentService.selectAllDepart();
		mav.addObject("departmentList", departmentList);
		mav.setViewName("back/selectCourse/selectCourseDesignAdd");
		return mav;
	}	
	/**
	 * 课程设计添加重修学生
	 * @param mav
	 * @return
	 */
	@RequestMapping("/selectCourseDesignAddUI1")
	public ModelAndView selectCourseDesignAddUI1(ModelAndView mav){
		List<Course> courseList = courseService.selectAllCourse(getUserId());
		mav.addObject("courseList", courseList);
		List<Depart> departmentList = departmentService.selectAllDepart();
		mav.addObject("departmentList", departmentList);
		mav.setViewName("back/selectCourse/selectCourseDesignAdds");
		return mav;
	}	
	/**
	 * 根据课程联动题目
	 */
	@RequestMapping("seletSubject")
	@ResponseBody
	@LogCustom(desc="课程设计题目")
	public Object seletSubject(Long courseId){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取课程设计题目信息！");
		try {
		if(courseId!=null){
				List<Experiment> experimentList= experimentService.findSubject(courseId);
				return renderSuccess(experimentList);
		}
		} catch (Exception e) {
			logger.error("获取课程设计题目失败！", e);
			return renderError("获取课程设计题目失败！");
		}
		return renderSuccess("没有课程设计题目信息！");
	}
	@RequestMapping("seletMajor")
	@ResponseBody
	@LogCustom(desc="专业联动")
	public Object seletMajor(Long departmentId){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取专业信息！");
		try {
		if(departmentId!=null){
				List<Major> majorList= majorService.findMajorByDepartId(departmentId);
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
	@RequestMapping("selectStudentClass")
	@ResponseBody
	@LogCustom(desc="班级联动")
	public Object selectClass(Long majorId){
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
	 * 根据班级联动学生
	 */
	@RequestMapping("selectStudent")
	@ResponseBody
	@LogCustom(desc="学生联动")
	public Object selectStudent(Long classId,Long courseId){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取学生信息！");
		try {
		if(classId!=null){
				List<Student> studentList=studentService.findStudentByClassId(classId,courseId);
				return renderSuccess(studentList);
		}
		} catch (Exception e) {
			logger.error("获取学生失败！", e);
			return renderError("获取学生失败！");
		}
		return renderSuccess("没有学生信息！");
	}	
	
	
	@RequestMapping("selectCourseAdd")
	@ResponseBody
	@LogCustom(desc="进行了选课添加")
	public Result selectCourseAdd(Long courseId,Long departmentId,Long majorId,Long classId){
		try {
			selectCourseService.insert(courseId,departmentId,majorId,classId,getUserId());
			return new Result(true, "添加成功");
		} catch (Exception e) {
			logger.error("选课添加,selectCourseAdd出错{}");
			e.printStackTrace();
			return new Result(false, "添加失败");
		}
	}
	
	@RequestMapping("selectCourseAdd1")
	@ResponseBody
	@LogCustom(desc="进行了选课添加")
	public Result selectCourseAdd1(Long courseId,String code,Long departmentId,Long majorId,Long fakeclassId){
		try {
			boolean f=selectCourseService.insertStudent(courseId,code,departmentId,majorId,fakeclassId,getUserId());
			if(f==true){
				return new Result(true, "添加成功");				
			}else{
				return new Result(false, "学生不存在");	
			}
		} catch (Exception e) {
			logger.error("选课添加,selectCourseAdd1出错{}");
			e.printStackTrace();
			return new Result(false, "添加失败");
		}
	}
	/**
	 * 课程设计关联学生
	 * @param mav
	 * @param courseId
	 * @param departmentId
	 * @param majorId
	 * @param classId
	 * @param studentIds
	 * @return
	 */
	@RequestMapping("selectCourseDesignAdd")
	@ResponseBody
	@LogCustom(desc="进行了选课添加")
	public Result selectCourseDesignAdd(Long courseId,Long experimentId,Long departmentId,Long majorId,Long classId,String[] ids){
		try {
			selectCourseService.insertStudentDsign(courseId, experimentId, departmentId, majorId, classId, getUserId(), ids);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			logger.error("选课添加,selectCourseAdd出错{}");
			e.printStackTrace();
			return new Result(false, "添加失败");
		}
	}

	/**
	 * 课程设计关联重修学生
	 * @param mav
	 * @param courseId
	 * @param departmentId
	 * @param majorId
	 * @param classId
	 * @param studentIds
	 * @return
	 */
	@RequestMapping("selectCourseDesignAdds")
	@ResponseBody
	@LogCustom(desc="进行了选课添加")
	public Result selectCourseDesignAdds(Long courseId,Long experimentId,Long departmentId,Long majorId,Long fakeclassId,String code){
		try {
			System.out.print(code);
			boolean f= selectCourseService.insertStudentDsign1(courseId, experimentId, departmentId, majorId, fakeclassId, getUserId(), code);
			if(f==true){
				return new Result(true, "添加成功");				
			}else{
				return new Result(false, "学生不存在");	
			}
		} catch (Exception e) {
			logger.error("选课添加,selectCourseAdd出错{}");
			e.printStackTrace();
			return new Result(false, "添加失败");
		}
	}
	
	@RequestMapping(value = "/courseManage")
	@LogCustom(desc = "获取了课程管理查看")
	public ModelAndView courseManage( ExperimentVo experimentVo,Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行课程管理查看");
		ModelAndView mav = new ModelAndView();
		try {
			int count = userRoleService.countByTeacherId(getUserId());
			if(count != 0)
				experimentVo.setTeacherId(teacherService.getUserIdById(getUserId()));
			Page<ExperimentVo> page = selectCourseService.selectAllCoursePage(experimentVo, currentPage == null ? 1 : currentPage);
			// 列表项
			mav.addObject("courseList", page.getResultList());
			// 分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());

			// 搜索条件
			mav.addObject("experimentVo", experimentVo);

			mav.setViewName("back/selectCourse/courseManage");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("课程管理查看，courseManage出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@LogCustom(desc = "查看课程详情！")
	public ModelAndView detail(@PathVariable("id") Long id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，查看课程详情");
		ModelAndView mav = new ModelAndView();
		try{
			List<ExperimentVo> experimentList = selectCourseService.selectExperimentByCourseId(id);
			mav.addObject("experimentList", experimentList);
			mav.addObject("id", id);
			mav.setViewName("back/selectCourse/courseDetail");
		} catch (Exception e) {
			mav = new ModelAndView("error/500");
			logger.error("查看课程详情，detail出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping(value = "/courseAddUI")
	@LogCustom(desc = "进入课程添加页面")
	public ModelAndView courseAddUI() {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行课程添加页面！");
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("back/selectCourse/courseAddUI");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("课程添加页面，courseAddUI出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping(value = "selectCourseCode", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "判断课程编号")
	public Object selectCourseCode(String courseCode) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，获取课程编号！");
		try {
			String bh=courseCode.trim();
			if(bh!=null && bh!=""){
				Course course = courseService.selectCourseByCourseCode(bh);
				if(course!=null){
					return renderError("该课程编号已存在，请填写新编号！");
				}
				return renderSuccess("");
			}
			return 1;
		} catch (Exception e) {
			logger.error("获取课程编号出错！", e);
			return renderError("获取课程编号出错！");
		}

	}
	
	@RequestMapping("courseAdd")
	@ResponseBody
	public Object courseAdd(String courseCode,String courseName,String cdescription,
			String[] experimentCode,String[] experimentName,String[] description,String treeIds) {
		try {
			int size = experimentCode.length;
			Course c = new Course();
			c.setCode(courseCode);
			c.setName(courseName);
			c.setDescription(cdescription);
			c.setCount(size);
			int flag = courseService.insertCourse(c);
			if(flag != 0 && !treeIds.isEmpty()){
				String[] treeIdss = treeIds.split(",");
				for(String s:treeIdss){
					if(s.equals("1")){
						continue;
					}else{
						judgeService.updateJudges(Long.valueOf(s),c.getId());
					}
				}
			}
			Long teacherId = getUserId();
			if(flag != 0 && size>0){
				selectCourseService.insertExperiment( courseCode, experimentCode, experimentName,description,teacherId);
				return renderSuccess("课程添加成功！");
			}
			return renderSuccess("课程添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return renderError("课程添加出错！");
		}
	}
	
	@RequestMapping(value = "/rcUI/{id}", method = RequestMethod.GET)
	public ModelAndView checkUI(ModelAndView mv,@PathVariable("id")Long id,HttpServletRequest request) {
		ExperimentVo experiment = selectCourseService.selectScoreBySid(id);
		Student student=studentService.selectStudentByselectCourseId(id);
		mv.addObject("experiment", experiment);
		mv.addObject("student", student);
		mv.addObject("id", id);
		mv.setViewName("/back/selectCourse/checkUI");
		return mv;
	}

	@RequestMapping(value="/check")
	@ResponseBody
	@LogCustom(desc="报告提交！")
	public Object submit( HttpServletRequest request) {
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行报告提交");
		try {
		} catch (Exception e) {
			logger.error("我的提交添加失败，add出错{}", e);
			e.printStackTrace();
			return renderError("保存失败，系统内部错误！");
		}
		return renderSuccess("保存成功!");
	}
	
	@RequestMapping(value="downloadReport/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了报告下载")
	public Object downloadReport(@PathVariable("id")Long id,HttpServletRequest request, HttpServletResponse response){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行报告下载");
		try {
			ExperimentVo experiment = selectCourseService.selectScoreBySid(id);
	        String fileName = experiment.getReportPath();
	        downLoad(fileName, request, response);
	    } catch (Exception e) {
			logger.error("报告下载失败，lookReport出错{}", e);
			e.printStackTrace();
		}
		return renderSuccess(""); 
	}
	@RequestMapping(value="downloadPicture/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了截图下载")
	public Object downloadPicture(@PathVariable("id")Long id,HttpServletRequest request, HttpServletResponse response){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行截图下载");
		try {
			ExperimentVo experiment = selectCourseService.selectScoreBySid(id);
	        String fileName = experiment.getPicturePath();
	        downLoad(fileName, request, response);
	    } catch (Exception e) {
			logger.error("报告下载失败，lookReport出错{}", e);
			e.printStackTrace();
		}
		return renderSuccess(""); 
	}
	
	@RequestMapping(value="downloadScource/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了源码下载")
	public Object downloadResource(@PathVariable("id")Long id,HttpServletRequest request, HttpServletResponse response){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行源码下载");
		try {
			ExperimentVo experiment = selectCourseService.selectScoreBySid(id);
	        String fileName = experiment.getSourcecodePath();
	        downLoad(fileName, request, response);
	    } catch (Exception e) {
			logger.error("源码下载失败，lookReport出错{}", e);
			e.printStackTrace();
		}
		return renderSuccess(""); 
	}
	
	@RequestMapping(value="lookReport/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了报告预览")
	public Object lookReport(@PathVariable("id")Long id,HttpServletRequest request, HttpServletResponse response){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行源码预览");
		try {
			request.setCharacterEncoding("UTF-8");
			ExperimentVo experiment = selectCourseService.selectScoreBySid(id);
	        String Address = experiment.getReportPath();
	        look(request,response,Address);
	        selectCourseService.updateByselectcourseId(id);
	    }catch (Exception e) {
			logger.error("报告预览失败，lookReport出错{}", e);
			e.printStackTrace();
	    }
		return renderSuccess("");
	}
	@RequestMapping(value="lookPicture/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了图片预览")
	public Object lookPicture(@PathVariable("id")Long id,HttpServletRequest request, HttpServletResponse response){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行图片预览");
		try {
			request.setCharacterEncoding("UTF-8");
			ExperimentVo experiment = selectCourseService.selectScoreBySid(id);
			String Address = experiment.getPicturePath();
			look(request,response,Address);
		}catch (Exception e) {
			logger.error("图片预览失败，lookPicture出错{}", e);
			e.printStackTrace();
		}
		return renderSuccess("");
	}
	@RequestMapping(value="lookScource/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了源码预览")
	public Object lookScource(@PathVariable("id")Long id,HttpServletRequest request, HttpServletResponse response){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行源码预览");
		try {
			request.setCharacterEncoding("UTF-8");
			ExperimentVo experiment = selectCourseService.selectScoreBySid(id);
			String Address = experiment.getSourcecodePath();
			look(request,response,Address);
		}catch (Exception e) {
			logger.error("报告下载失败，lookReport出错{}", e);
			e.printStackTrace();
		}
		return renderSuccess("");
	}
	
	public void look(HttpServletRequest request, HttpServletResponse response,String Address){
		try {
			request.setCharacterEncoding("UTF-8");
			if (!getLicense()) {          // 验证License 若不验证则转化出的PDP文档会有水印产生           
				 return;       
			 } 
				InputStream in = null;
				OutputStream out = null;
				String path = request.getSession().getServletContext().getRealPath("");
		        File directory = new File(path);
		        File filePath = new File(directory.getParentFile().toString()).getParentFile();
		        path =  filePath.toString();
		        path = path + "\\file"+File.separator+"temp.pdf";
				File file = new File(path);  //新建一个空白pdf文档       		
				file.delete();
				FileOutputStream os = new FileOutputStream(file);  
				Document doc = new Document(Address);                    //Address是将要被转化的word文档           
				doc.save(os, SaveFormat.PDF);                            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换            os.close();       
				String  filePaths = path;
				if(filePaths !=null){
					in = new FileInputStream(filePaths);
				}
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/pdf");
				out = response.getOutputStream();
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
				}
				if (out != null) {
				out.close();
				}
				if (in != null) {
				in.close();
				}
	    }catch (Exception e) {
			e.printStackTrace();
	    }
	}
	
	//apose权限
	public static boolean getLicense() {       
		boolean result = false;       
		try {           
			java.io.InputStream is = Test.class.getClassLoader().getResourceAsStream("license.xml"); //  wordlicense.xml应放在..\WebRoot\WEB-INF\classes路径下           
			com.aspose.words.License aposeLic = new com.aspose.words.License();           
			aposeLic.setLicense(is);           
			 result = true;       
		 }
		catch (Exception e) {                          
			e.printStackTrace();       
		}       
		return result;   
	}
	
	//下载文件
	public void downLoad(String fileName,HttpServletRequest request, HttpServletResponse response){
		try {
		//获取输入流  
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));  
        //截取最后一个“\”后面的字符
        String filename = fileName.substring(fileName.lastIndexOf("\\")+1);  
        //转码，免得文件名中文乱码  
        filename = URLEncoder.encode(filename,"UTF-8");  
        //解决转码时空格变成加号
        filename=filename.replaceAll("\\+",  "%20");
        //设置文件下载头  
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型    
        response.setContentType("multipart/form-data");   
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
        int len = 0;  
        while((len = bis.read()) != -1){  
            out.write(len);  
            out.flush();  
        }  
        out.close();  
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/cnki/{id}/{type}", method = RequestMethod.GET)
	public ModelAndView cnki(ModelAndView mv,@PathVariable("id")Long id,@PathVariable("type")int type,HttpServletRequest request,HttpServletResponse response) {
		ExperimentVo experiment = selectCourseService.selectExperimentBySid(id);
		try {
			/*EditDistance*/
			Long classId;//报告提交的班级（重修+正常）
			if(experiment.getFlag()==1){
				classId=experiment.getFakeclassId();//重修同学的查重班级（文件）
			}else{
				classId=experiment.getClassId();//正常同学的查重班级（文件）
			}			
			String[] text = null;//存储学生论文的字符串数组
			String[] name = null;//存储学生姓名等信息
			Object[]  object=null;//存储学生论文相似的学生信息
			List<String> tempPath = new ArrayList<>();//存储所有重复率大于50%文件路径
			List<Long> experimentIds = new ArrayList<>();//存储所有重复率大于50%文件的成绩id
			LunWenChaCong l = new LunWenChaCong();
			String singlePath = experiment.getReportPath();//要查重学生文件路径
			String path = request.getSession().getServletContext().getRealPath("");
	        File directory = new File(path);
	        File file = new File(directory.getParentFile().toString()).getParentFile();
	        path =  file.toString();
	        String renamePath = path;
			/*	        String dpath = path + "\\file\\"+experiment.getClassId()+File.separator+experiment.getEtId();
				        path = path + "\\file\\"+experiment.getClassId()+File.separator+experiment.getEtId()+"\\report";//该班级实验提交的文件夹路径
			*/	
			String dpath = path + "\\file\\"+classId+File.separator+experiment.getEtId();
	        path = path + "\\file\\"+classId+File.separator+experiment.getEtId()+"\\report";//该班级实验提交的文件夹路径
	        List<File> fileListbefore = new ArrayList<File>(); 
	        List<File> fileList = new ArrayList<File>(); 
	        fileListbefore = traverseFolder(fileListbefore,path);//该班级实验提交的所有文件
	        for(int i = 0;i<fileListbefore.size();i++){
	        	if(selectCourseMapper.getSelIdByPath(fileListbefore.get(i).getPath())==null){
	        		System.out.print(fileListbefore.get(i).getPath());
	        		File fileDy =new File(fileListbefore.get(i).getPath());     		
	        		String strPath = renamePath+"\\extrafile\\"+classId+File.separator+experiment.getEtId()+"\\report"; 
	        		File exfile = new File(strPath); 
	        		if(!exfile.exists()){ 
	        		 exfile.mkdirs(); 
	        		}
	        		if(fileDy.renameTo(new File(strPath+"\\"+fileDy.getName()))){//+File.separator+experiment.getEtId()+"\\report"
	        			fileDy.delete();
	        		}
	        	}	        	
	        }
	        fileList = traverseFolder(fileList,path);//该班级实验提交的所有文件
	        FileInputStream in = null;
	        text=new String[fileList.size()];
	        name=new String[fileList.size()];
	        int flag = -1;
	        for(int i = 0;i<fileList.size();i++){
	        	/*in = new FileInputStream(new File(fileList.get(i).getPath()));*/
				// 创建WordExtractor
				WordExtractor extractor = null;
				 if(fileList.get(i).getPath().endsWith(".doc")){   //doc为后缀的
	                in = new FileInputStream(new File(fileList.get(i).getPath()));
	                extractor = new WordExtractor(in);
					text[i]=extractor.getText();
	            }
	            if(fileList.get(i).getPath().endsWith(".docx")){  //docx为后缀的
	                XWPFWordExtractor docx = new XWPFWordExtractor(POIXMLDocument.openPackage(fileList.get(i).getPath())); 
	                text[i] = docx.getText();
	            }
	            name[i] = fileList.get(i).getPath().substring(fileList.get(i).getPath().lastIndexOf("\\")+1);
	            if(fileList.get(i).getPath().equals(singlePath)){
	            	 flag = i;
	            }else{
	            	tempPath.add(fileList.get(i).getPath());
	            }
	        }
	        if(type == 0){
	        	/*EditDistance*/
	        	object=l.EDcalculateByClass(text, name,flag);
			}else if (type == 1){
				/*CosineSimilarAlgorithm*/
				object=l.CalculateSimilarityAlgrithmByClass(text, name,flag);
			}else{
				/*JianDanMoHu*/
				object=l.CalculateSimilarityJianDanMoHuByClass(text, name,flag);
			}
	        if(object.length == 0){
	        	selectCourseService.updateScoreRepetition(null,experiment.getId());
	        	mv.addObject("msg", "查重成功，没有重复率大于50%的文件!");
	        	mv.setViewName("back/selectCourse/checkResult");
	        	return mv;
	        }
	        if(tempPath.size() > 0){
	        	List<Long> selId = selectCourseService.getSelIdByPath(tempPath);
	        	mv.addObject("selId", selId);
	        }
	        String tempName = singlePath.substring(singlePath.lastIndexOf("\\")+1, singlePath.lastIndexOf('.'));
	        String filefolder=dpath+"\\checkDuplicate";
	        String writePath=dpath+"\\checkDuplicate"+File.separator+tempName+".xls"; // 文件保存路径
	        File file2 = new File(filefolder);
	        if (!file2.exists()) {
				 file2.mkdirs();
			}
			else{}
	        l.SaveByClass(object,writePath,flag);
	        ArrayList TotalNameList = (ArrayList) object[0];
			ArrayList TotalSimilaity = (ArrayList) object[1];
			Double temp = 0.0;
			for(int i = 0;i<TotalSimilaity.size();i++){
				Double similarity = Double.valueOf(((String) TotalSimilaity.get(i)).split("\\%")[0]);
				if(temp<similarity){
					temp = similarity;
				}
			}
			selectCourseService.updateScoreRepetition(temp,experiment.getId());
	        /*
	        //把excel转化成html
	        String html = FilePreview.convertExceltoHtml(writePath);
	        mv.addObject("html", html);*/
			mv.addObject("TotalNameList", TotalNameList);
			mv.addObject("TotalSimilaity", TotalSimilaity);
	        mv.setViewName("back/selectCourse/checkResult");
	        return mv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	public List<File> traverseFolder(List<File> fileList,String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return fileList;
            } else {
                 //循环遍历文件夹下面所有文件，包括含文件夹
            	/*for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder(fileList,file2.getAbsolutePath());
                    } else {
                    	fileList.add(file2);
                        System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }*/
            	//遍历一个文件夹下面所有文件
            	for(int i = 0;i<files.length;i++){
            		fileList.add(files[i]);
            	}
                return fileList;
            }
        } else {
        	return fileList;
        }
    }
	@RequestMapping(value="/findAllTree/{id}",method=RequestMethod.GET)
	@ResponseBody
	public List<Tree> findAllTree(@PathVariable("id")Long id){
		return selectCourseService.findAllTrees(id);
	}
	@RequestMapping(value="/findJudgeIdListBySelectcourseId",method=RequestMethod.POST)
    @ResponseBody
    public Object findJudgeIdListByJudgeSelectcourseId(Long id) {
    	if(id!=null){
    		List<Long> judges = judgeService.findJudgeIdListBySelectcourseId(id);
            try {
				return renderSuccess(judges);
			} catch (Exception e) {
				logger.error("授权页面页面根据角色查询资源,失败！",e);
			}
    	}
        return null;
    }
	
	
	@RequestMapping(value="/jjReport")
	@ResponseBody
	@LogCustom(desc="报告提交！")
	public Object checkReport(Long id, String judges) {
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行报告批阅");
		try {
			if(id!=null){
    			judgeService.updateJudgeSelectcourse(id, judges);
    			StringTokenizer st = new StringTokenizer(judges, ",");
    			String[] strArray = new String[st.countTokens()];
    			int strLeng = st.countTokens();
    			for (int i=0; i<strLeng; i++) {
    			strArray[i] = st.nextToken();
    			}
    			Double scores = 0.0;
    			if(strArray.length>0){
    				for(String string : strArray){
    					Double score = judgeService.getScoreById(Long.parseLong(string));
    					if(score != null){
    						scores += score;
    					}
    				}
    			}
    			selectCourseService.insertScore(id,scores);
    		}else{
    			logger.error("提交失败，checkReport方法异常，ID解析失败！");
    			return renderError("提交失败，数据解析失败！");
    		}
		} catch (Exception e) {
			logger.error("我的提交添加失败，add出错{}", e);
			e.printStackTrace();
			return renderError("保存失败，系统内部错误！");
		}
		return renderSuccess("保存成功!");
	}
	
	
	@RequestMapping(value="/upcc")
	@ResponseBody
	@LogCustom(desc="报告提交！")
	public Object updateReport(Long id, String judges) {
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行报告批阅");
		try {
			if(id!=null){
    			judgeService.updateJudgeSelectcourse(id, judges);
    			StringTokenizer st = new StringTokenizer(judges, ",");
    			String[] strArray = new String[st.countTokens()];
    			int strLeng = st.countTokens();
    			for (int i=0; i<strLeng; i++) {
    			strArray[i] = st.nextToken();
    			}
    			Double scores = 0.0;
    			if(strArray.length>0){
    				for(String string : strArray){
    					Double score = judgeService.getScoreById(Long.parseLong(string));
    					if(score != null){
    						scores += score;
    					}
    				}
    			}
    			selectCourseService.insertScore(id,scores);
    		}else{
    			logger.error("提交失败，checkReport方法异常，ID解析失败！");
    			return renderError("提交失败，数据解析失败！");
    		}
		} catch (Exception e) {
			logger.error("我的提交添加失败，add出错{}", e);
			e.printStackTrace();
			return renderError("保存失败，系统内部错误！");
		}
		return renderSuccess("保存成功!");
	}	
	@RequestMapping(value = "deleteJudge")
	@ResponseBody
	public String deleteJudge(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") String id) {
		return judgeService.deleteJudge(id);
	}
	
	@RequestMapping(value = "updateJudgeName")
	@ResponseBody
	public String updateJudgeName(HttpServletRequest request, HttpServletResponse response, Judge judge) {
		//	String createname = (String) request.getSession().getAttribute(Constants.CURRENT_USER_NAME);
		return judgeService.addOrUpdateJudge(judge);
	}
	
	@RequestMapping(value = "saveJudge")
	@ResponseBody
	public Long saveJudge(HttpServletRequest request, HttpServletResponse response,Judge judge,String name,Long parentId) {
		 judge = new Judge();
		 judge.setParentId(parentId);
		 String [] strs = name.split("[-]");
		 if(strs.length == 2){
			 judge.setName(strs[0]);
			 judge.setScore(Double.valueOf(strs[1]));
		 }else{
			 judge.setName(name);
		 }
		 Long id = judgeService.insertJudgeof(judge);
		 return judge.getId();
	}
	
	@RequestMapping(value = "findAllJudge")
	@ResponseBody
	public List<Object> findAllJudge(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ztree");
		List<Object> objects = judgeService.findAllJudge();
		for (Object object : objects) {
			System.out.println("ssss" + object);
		}
		return judgeService.findAllJudge();

	}
	
	@RequestMapping(value = "score/exportAll/{cid}/{classId}", method = RequestMethod.GET)
	@LogCustom(desc = "导出了成绩信息！")
	public void exportAll(HttpServletRequest request, HttpServletResponse response,@PathVariable("cid") Long cid,@PathVariable("classId")Long classId) throws Exception {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行成绩导出");
		int size =  courseService.getCountById(cid);
		String[] title = new String[6+size];
		title[0] ="学号";
		title[1] ="姓名";
		title[2] ="班级";
		title[3] ="课程";
		title[4] ="教师";
		if(size != 0){
			for(int i = 5;i<5+size;i++){
				title[i] = "第"+(i-4)+"次实验成绩";
			}
		}
		title[5+size] ="平均分";
		List<ExperimentVo> list=selectCourseService.getListOfScoreByCourse(cid,classId);
        List<Object[]>  dataList = new ArrayList<Object[]>();     
        for(int i=0;i<list.size();i++){
            Object[] obj=new Object[6+size];
            if(list.get(i).getStudentCode() != null){
            	obj[0]=list.get(i).getStudentCode();
            }else{
            	obj[0]= "无";
            }
            if( list.get(i).getStudentName() !=null){
            	obj[1]=list.get(i).getStudentName();
            }else{
            	obj[1]= "无";
            }
            if(list.get(i).getClassName() != null){
            	obj[2]=list.get(i).getClassName();
            }else{
            	obj[2]= "无";
            }
            if(list.get(i).getCourseName() != null){
            	obj[3]=list.get(i).getCourseName();
            }else{
            	obj[3]= "无";
            }
            if(list.get(i).getTeacherName() != null){
            	obj[4]=list.get(i).getTeacherName();
            }else{
            	obj[4]= "无";
            }
           
            String[] score = list.get(i).getScore1().split(",");//如果score为空则会是0分
            double avg = 0.0;
            if(score.length==size){
                if(size != 0){
        			for(int j = 0;j<size;j++){
    					obj[j+5] = score[j];
    					avg += Double.valueOf(score[j]);
        			}
        			avg = avg/size;
        		}
                obj[5+size] = avg;            	
            }else{
                if(size != 0){
        			for(int j = 0;j<size;j++){
    					obj[j+5] = score[0];
    					avg += Double.valueOf(score[0]);
        			}
        			avg = avg/size;
        		}
                obj[5+size] = avg;   
    			for(int j = 0;j<size;j++){
					obj[j+5] = score[0];
					avg += Double.valueOf(score[0]);
    			} 
            }
            dataList.add(obj);
        }
        WriteExcelUtils ex = new WriteExcelUtils(title, dataList);
        InputStream is = ex.export();
        response.setContentType("application/x-msdownload"); 
        if(list.size()>0){
        	String fileName = list.get(0).getYear()+"年第"+list.get(0).getTerm()+"学期"+list.get(0).getClassName()
        			+list.get(0).getCourseName()+"学生各门实验成绩.xls";
	        String rName = null;
	        //对IE浏览器进行下载兼容处理
	        if (request.getHeader("User-Agent").contains("MSIE")) {
				//IE浏览器
				rName = URLEncoder.encode(fileName, "UTF-8");
			}else {
				//其他浏览器
				rName=new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
	        response.setHeader("Content-Disposition", "attachment;filename=" + rName);
        }else{
        	String fileName = "无实验成绩.xls";
	        String rName = null;
	        //对IE浏览器进行下载兼容处理
	        if (request.getHeader("User-Agent").contains("MSIE")) {
				//IE浏览器
				rName = URLEncoder.encode(fileName, "UTF-8");
			}else {
				//其他浏览器
				rName=new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
	        response.setHeader("Content-Disposition", "attachment;filename=" + rName);
        }
		ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(is, output);
	}
	
	@RequestMapping(value = "score/exportOne/{cid}/{classId}/{experimentId}", method = RequestMethod.GET)
	@LogCustom(desc = "导出了成绩信息！")
	public void exportOne(HttpServletRequest request, HttpServletResponse response,@PathVariable("cid") Long cid,@PathVariable("classId")Long classId,
			@PathVariable("experimentId") Long experimentId) throws Exception {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行一次实验成绩导出");
		int size =  judgeService.getCountByCourseId(cid);
		List<Judge> judgeList = judgeService.getByCourseId(cid);
		String[] title = new String[8+size];
		title[0] ="学号";
		title[1] ="姓名";
		title[2] ="班级";
		title[3] ="课程";
		title[4] ="教师";
		title[5] ="实验";
		title[6] ="重复率";
		title[7] ="成绩";
		if(size != 0){
			for(int i = 0;i<size;i++){
				title[i+8] = judgeList.get(i).getName();
			}
		}
		List<ExperimentVo> list=selectCourseService.getListOfExperimentScoreByCourse(cid,classId,experimentId);
        List<Object[]>  dataList = new ArrayList<Object[]>();     
        for(int i=0;i<list.size();i++){
            Object[] obj=new Object[8+size];
            if(list.get(i).getStudentCode() != null){
            	obj[0]=list.get(i).getStudentCode();
            }else{
            	obj[0]= "无";
            }
            if(list.get(i).getStudentName() != null){
            	obj[1]=list.get(i).getStudentName();
            }else{
            	obj[1]= "无";
            }
            if(list.get(i).getClassName() != null){
            	obj[2]=list.get(i).getClassName();
            }else{
            	obj[2]= "无";
            }
            if(list.get(i).getCourseName() != null){
            	obj[3]=list.get(i).getCourseName();
            }else{
            	obj[3]= "无";
            }
            if(list.get(i).getTeacherName() != null){
            	obj[4]=list.get(i).getTeacherName();
            }else{
            	obj[4]= "无";
            }
            if(list.get(i).getExperimentName() != null){
            	obj[5]=list.get(i).getExperimentName();
            }else{
            	obj[5]= "无";
            }
            if(list.get(i).getRepetition() != null){
            	obj[6]=list.get(i).getRepetition();
            }else{
            	obj[6] = "无";
            }
            if(list.get(i).getScore() != null){
            	obj[7]=list.get(i).getScore();
            }else{
            	obj[7] = 0;
            }
            Long selectcourseId = list.get(i).getId();
            List<Judge> jList = judgeService.selectJudgeIdBySelId(selectcourseId);
            for(int k=0;k<judgeList.size();k++){
            	List<String> jc = new ArrayList<>();
        		List<String> jcdata = new ArrayList<>();
        		jcdata.clear();
            	jc = jc(jList,judgeList.size(),judgeList.get(k).getId(),jc,jcdata);
            	if(jc != null && jc.size() >0){
            		obj[8+k]=jc.toString();
            	}else{
            		obj[8+k]="无";
            	}
            	/*for(int v=0;v<jList.size();v++){
            		if(jList.get(v).getParentId() ==judgeList.get(k).getId()){
            			String jc = jList.get(v).getName();
            		}else{
            			List<Judge> judges = judgeService.selectByPId(jList.get(v).getId());
            		}
            	}*/
            	
            }
          /*  if(size != 0){
    			for(int j = 0;j<size;j++){
					obj[j+5] = score[j];
    			}
    		}*/
            dataList.add(obj);
        }
        WriteExcelUtils ex = new WriteExcelUtils(title, dataList);
        InputStream is = ex.export();
        response.setContentType("application/x-msdownload"); 
        if(list.size()>0){
        	String fileName = list.get(0).getYear()+"年第"+list.get(0).getTerm()+"学期"+list.get(0).getClassName()
        			+list.get(0).getCourseName()+list.get(0).getExperimentName()+"学生成绩.xls";
	        String rName = null;
	        //对IE浏览器进行下载兼容处理
	        if (request.getHeader("User-Agent").contains("MSIE")) {
				//IE浏览器
				rName = URLEncoder.encode(fileName, "UTF-8");
			}else {
				//其他浏览器
				rName=new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
	        response.setHeader("Content-Disposition", "attachment;filename=" + rName);
        }else{
        	String fileName = "无实验成绩.xls";
	        String rName = null;
	        //对IE浏览器进行下载兼容处理
	        if (request.getHeader("User-Agent").contains("MSIE")) {
				//IE浏览器
				rName = URLEncoder.encode(fileName, "UTF-8");
			}else {
				//其他浏览器
				rName=new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
	        response.setHeader("Content-Disposition", "attachment;filename=" + rName);
        }
		ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(is, output);
	}

	@RequestMapping(value = "score/exportDesign/{cid}/{classId}", method = RequestMethod.GET)
	@LogCustom(desc = "导出了成绩信息！")
	public void exportDesign(HttpServletRequest request, HttpServletResponse response,@PathVariable("cid") Long cid,@PathVariable("classId")Long classId) throws Exception {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行课程设计成绩导出");
		int size =  judgeService.getCountByCourseId(cid);
		List<Judge> judgeList = judgeService.getByCourseId(cid);
		String[] title = new String[8+size];
		title[0] ="学号";
		title[1] ="姓名";
		title[2] ="班级";
		title[3] ="课程";
		title[4] ="教师";
		title[5] ="实验";
		title[6] ="重复率";
		title[7] ="成绩";
		if(size != 0){
			for(int i = 0;i<size;i++){
				title[i+8] = judgeList.get(i).getName();
			}
		}
		List<ExperimentVo> list=selectCourseService.getListOfExperimentScoreByDesignCourse(cid,classId);
        List<Object[]>  dataList = new ArrayList<Object[]>();     
        for(int i=0;i<list.size();i++){
            Object[] obj=new Object[8+size];
            if(list.get(i).getStudentCode() != null){
            	obj[0]=list.get(i).getStudentCode();
            }else{
            	obj[0]= "无";
            }
            if(list.get(i).getStudentName() != null){
            	obj[1]=list.get(i).getStudentName();
            }else{
            	obj[1]= "无";
            }
            if(list.get(i).getClassName() != null){
            	obj[2]=list.get(i).getClassName();
            }else{
            	obj[2]= "无";
            }
            if(list.get(i).getCourseName() != null){
            	obj[3]=list.get(i).getCourseName();
            }else{
            	obj[3]= "无";
            }
            if(list.get(i).getTeacherName() != null){
            	obj[4]=list.get(i).getTeacherName();
            }else{
            	obj[4]= "无";
            }
            if(list.get(i).getExperimentName() != null){
            	obj[5]=list.get(i).getExperimentName();
            }else{
            	obj[5]= "无";
            }
            if(list.get(i).getRepetition() != null){
            	obj[6]=list.get(i).getRepetition();
            }else{
            	obj[6] = "无";
            }
            if(list.get(i).getScore() != null){
            	obj[7]=list.get(i).getScore();
            }else{
            	obj[7] = 0;
            }
            Long selectcourseId = list.get(i).getId();
            List<Judge> jList = judgeService.selectJudgeIdBySelId(selectcourseId);
            for(int k=0;k<judgeList.size();k++){
            	List<String> jc = new ArrayList<>();
        		List<String> jcdata = new ArrayList<>();
        		jcdata.clear();
            	jc = jc(jList,judgeList.size(),judgeList.get(k).getId(),jc,jcdata);
            	if(jc != null && jc.size() >0){
            		obj[8+k]=jc.toString();
            	}else{
            		obj[8+k]="无";
            	}
            	/*for(int v=0;v<jList.size();v++){
            		if(jList.get(v).getParentId() ==judgeList.get(k).getId()){
            			String jc = jList.get(v).getName();
            		}else{
            			List<Judge> judges = judgeService.selectByPId(jList.get(v).getId());
            		}
            	}*/
            	
            }
          /*  if(size != 0){
    			for(int j = 0;j<size;j++){
					obj[j+5] = score[j];
    			}
    		}*/
            dataList.add(obj);
        }
        WriteExcelUtils ex = new WriteExcelUtils(title, dataList);
        InputStream is = ex.export();
        response.setContentType("application/x-msdownload"); 
        if(list.size()>0){
        	String fileName = list.get(0).getYear()+"年第"+list.get(0).getTerm()+"学期"+list.get(0).getClassName()
        			+list.get(0).getCourseName()+list.get(0).getExperimentName()+"学生成绩.xls";
	        String rName = null;
	        //对IE浏览器进行下载兼容处理
	        if (request.getHeader("User-Agent").contains("MSIE")) {
				//IE浏览器
				rName = URLEncoder.encode(fileName, "UTF-8");
			}else {
				//其他浏览器
				rName=new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
	        response.setHeader("Content-Disposition", "attachment;filename=" + rName);
        }else{
        	String fileName = "无实验成绩.xls";
	        String rName = null;
	        //对IE浏览器进行下载兼容处理
	        if (request.getHeader("User-Agent").contains("MSIE")) {
				//IE浏览器
				rName = URLEncoder.encode(fileName, "UTF-8");
			}else {
				//其他浏览器
				rName=new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
	        response.setHeader("Content-Disposition", "attachment;filename=" + rName);
        }
		ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(is, output);
	}	
	
	
	private List<String> jc(List<Judge> jList,int si, Long id,List<String> jc,List<String> jcdata) {
		for(int v=0;v<jList.size();v++ ){
			jc.add(jList.get(v).getName());
			jc=GetName(jList.get(v).getId(), jList.get(v).getParentId(), jc);
			if(Long.valueOf(jc.get(jc.size()-1)).longValue()==id.longValue()){
				jc.remove(jc.size()-1);
				jc.remove(jc.size()-1);
				Collections.reverse(jc);
				jcdata.add(StringUtils.strip(jc.toString(),"[]"));
				jc.clear();
			}else{
				jc.clear();
			}
			
		}
		return jcdata;
	}
	
	private List<String> GetName(Long id,Long parentId, List<String> jc){
		if(parentId==1){
			jc.add(id.toString());
			return jc;
		}else if(parentId!=null&&parentId>1){
			Judge judge= judgeService.getParentById(parentId);
			jc.add(judge.getName());
			GetName(judge.getId(),judge.getParentId().longValue(),jc);
		}
		return jc;
	}
	
	@RequestMapping(value = "/scoreDetail/{id}", method = RequestMethod.GET)
	public ModelAndView scoreDetail(ModelAndView mv,@PathVariable("id")Long id,HttpServletRequest request) {
		ExperimentVo experiment = selectCourseService.selectScoreBySid(id);
		Student student=studentService.selectStudentByselectCourseId(id);
		mv.addObject("experiment", experiment);
		mv.addObject("student", student);
		mv.addObject("id", id);
		mv.setViewName("/back/selectCourse/scoreDetail");
		return mv;
	}
	@RequestMapping(value = "/scoreDetail1/{id}", method = RequestMethod.GET)
	public ModelAndView scoreDetail1(ModelAndView mv,@PathVariable("id")Long id,HttpServletRequest request) {
		ExperimentVo experiment = selectCourseService.selectScoreBySid(id);
		mv.addObject("experiment", experiment);
		mv.addObject("id", id);
		mv.setViewName("/back/selectCourse/scoreDetail1");
		return mv;
	}
	
	@RequestMapping(value = "/teacherInfo/{userId}")
	@LogCustom(desc = "教师信息查看")
	public ModelAndView userInfo(@PathVariable("userId")String userId) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行教师信息查看");
		ModelAndView mav = new ModelAndView();
		try {
			Teacher teacher = teacherService.selectTeacher(userId);
			mav.addObject("teacher", teacher);
			mav.setViewName("back/teacher/teacherInfo");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("教师信息查看，userInfo出错{}", e);
		}
		return mav;
	}
	@RequestMapping(value = "/studentInfo/{userId}")
	@LogCustom(desc = "学生信息查看")
	public ModelAndView studentInfo(@PathVariable("userId")String userId) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行学生信息查看");
		ModelAndView mav = new ModelAndView();
		try {
			Student student = studentService.selectStudent(userId);
			mav.addObject("student", student);
			mav.setViewName("back/student/studentInfo");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("学生信息查看，studentInfo出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping(value = "/lookScore")
	@LogCustom(desc = "成绩查看")
	public ModelAndView lookScore( ExperimentVo experimentVo,Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行成绩查看");
		ModelAndView mav = new ModelAndView();
		try {
			if(getCurrentUser().getAdminType() == 0){
				experimentVo.setStudentId(getUserId());
			}else{
				int count = userRoleService.countByTeacherId(getUserId());
				/*int count = selectCourseService.countByExprimentTeacherId(getUserId());*/
				if(count != 0)
					experimentVo.setTeacherId(teacherService.getUserIdById(getUserId()));
			}
			Page<ExperimentVo> page = selectCourseService.selectScorePage(experimentVo, currentPage == null ? 1 : currentPage);
			// 列表项
			mav.addObject("selectCourseList", page.getResultList());
			// 分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());

			// 搜索条件
			mav.addObject("experimentVo", experimentVo);

			mav.setViewName("back/selectCourse/scorePage");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("成绩查看，submitList出错{}", e);
		}
		return mav;
	}
	@RequestMapping(value = "/historyrecord")
	@LogCustom(desc = "历史提交")
	public ModelAndView historyrecord( ExperimentVo experimentVo,Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行成绩查看");
		ModelAndView mav = new ModelAndView();
		try {
			if(getCurrentUser().getAdminType() == 0){
				experimentVo.setStudentId(getUserId());
			}else{
				int count = userRoleService.countByTeacherId(getUserId());
				/*int count = selectCourseService.countByExprimentTeacherId(getUserId());*/
				if(count != 0)
					experimentVo.setTeacherId(teacherService.getUserIdById(getUserId()));
			}
			Page<ExperimentVo> page = selectCourseService.selectHistoryPage(experimentVo, currentPage == null ? 1 : currentPage);
			// 列表项
			mav.addObject("selectCourseList", page.getResultList());
			// 分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());
			
			// 搜索条件
			mav.addObject("experimentVo", experimentVo);
			
			mav.setViewName("back/selectCourse/history");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("历史提交，historyrecord出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping(value = "/worksubmit")
	@LogCustom(desc = "提交作业")
	public ModelAndView worksubmit(Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，提交作业");
		ModelAndView mav = new ModelAndView();
		try {
			List<ExperimentVo> courses = selectCourseService.getCoursesByStudentId(getUserId());
			mav.addObject("courses", courses);
			mav.setViewName("back/selectCourse/worksubmit");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("提交作业，historyrecord出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping("getExperiment")
	@ResponseBody
	@LogCustom(desc="通过课程获取实验")
	public Object getExperiment(Long courseId){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，通过课程获取实验！");
		try {
		if(courseId!=null){
				List<ExperimentVo> expList= selectCourseService.getExperiment(courseId,getUserId());
				return renderSuccess(expList);
		}
		} catch (Exception e) {
			logger.error("获取专业失败！", e);
			return renderError("获取专业失败！");
		}
		return renderSuccess("没有专业信息！");
	}
	
	@RequestMapping("getExperiment1")
	@ResponseBody
	@LogCustom(desc="通过课程获取实验")
	public Object getExperiment1(Long courseId){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，通过课程获取实验！");
		try {
		if(courseId!=null){
				List<ExperimentVo> expList= selectCourseService.getExperimentByCourseId(courseId);
				return renderSuccess(expList);
		}
		} catch (Exception e) {
			logger.error("获取专业失败！", e);
			return renderError("获取专业失败！");
		}
		return renderSuccess("没有专业信息！");
	}
	@RequestMapping(value="courseDelete/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了单个课程删除")
	public Result courseDelete(@PathVariable("id")String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行课程删除，删除项："+id);
		try {
			if(id != null){
				courseService.delete(Long.valueOf(id));
			}
			return new Result(true,"删除成功");
		} catch (DataIntegrityViolationException e) {
			logger.error("课程删除，删除处理 出错，无法删除关联对象！",e);
			return new Result(false,"删除失败，有关联对象");}
			catch (Exception e) {
			logger.error("课程删除,courseDelete出错{}");
			return new Result(false,"删除失败");
		}
	}
	
	@RequestMapping(value = "/submitList1")
	@LogCustom(desc = "获取了作业管理")
	public ModelAndView submitList1( ExperimentVo experimentVo,Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行作业管理");
		ModelAndView mav = new ModelAndView();
		try {
			List<ExperimentVo> courses = selectCourseService.getCoursesByTeacherIdAndCount(getUserId());
			List<Depart> departmentList = departmentService.selectAllDepart();
			mav.addObject("departmentList", departmentList);
			mav.addObject("departId",experimentVo.getDepartId());
			mav.addObject("majorId",experimentVo.getMajorId());
			mav.addObject("classId",experimentVo.getClassId());
			mav.addObject("courseId",experimentVo.getCourseId());
			mav.addObject("experimentId",experimentVo.getExperimentId());
			mav.addObject("courses", courses);
			int count = userRoleService.countByTeacherId(getUserId());
			/*int count = selectCourseService.countByExprimentTeacherId(getUserId());*/
			if(count != 0)
				experimentVo.setTeacherId(teacherService.getUserIdById(getUserId()));
			Page<ExperimentVo> page = selectCourseService.selectCoursePage1(experimentVo, currentPage == null ? 1 : currentPage);
			// 列表项
			mav.addObject("selectCourseList", page.getResultList());
			// 分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());

			// 搜索条件
			mav.addObject("experimentVo", experimentVo);

			mav.setViewName("back/selectCourse/manager1");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("个人作业管理，submitList1出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping(value = "/scoreManagement")
	@LogCustom(desc = "获取了成绩管理")
	public ModelAndView scoreManagement( ExperimentVo experimentVo,Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行成绩管理");
		ModelAndView mav = new ModelAndView();
		try {
			List<ExperimentVo> courses = selectCourseService.getCoursesByTeacherId(getUserId());
			mav.addObject("courses", courses);
			int count = userRoleService.countByTeacherId(getUserId());
			/*int count = selectCourseService.countByExprimentTeacherId(getUserId());*/
			if(count != 0)
				experimentVo.setTeacherId(teacherService.getUserIdById(getUserId()));
			Page<ExperimentVo> page = selectCourseService.selectScorePage(experimentVo, currentPage == null ? 1 : currentPage);
			// 列表项
			List<Depart> departmentList = departmentService.selectAllDepart();
			mav.addObject("departmentList", departmentList);
			mav.addObject("departId",experimentVo.getDepartId());
			mav.addObject("majorId",experimentVo.getMajorId());
			mav.addObject("classId",experimentVo.getClassId());
			mav.addObject("courseId",experimentVo.getCourseId());
			mav.addObject("experimentId",experimentVo.getExperimentId());
			
			mav.addObject("selectCourseList", page.getResultList());
			// 分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());

			// 搜索条件
			mav.addObject("experimentVo", experimentVo);

			mav.setViewName("back/selectCourse/scorePageOfTeacher");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("个人作业管理，scoreManagement出错{}", e);
		}
		return mav;
	}
	
	@RequestMapping(value="/submit1")
	@ResponseBody
	@LogCustom(desc="报告提交！")
	public Object submit1( @RequestParam(value="attachs",required=false)CommonsMultipartFile[] attachs,
			ExperimentVo experimentVo, HttpServletRequest request) {
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行报告提交");
		try {
			Long classId;
			System.out.print(selectCourseService.getFlag(getUserId(),experimentVo.getExperimentId()));
			if(selectCourseService.getFlag(getUserId(),experimentVo.getExperimentId())==1){
				classId = selectCourseService.getClassIdByIdForDesign(getUserId(),experimentVo.getExperimentId());
			}else{
				classId = studentService.getClassIdById(getUserId());
			}
			System.out.print(classId);
			Long etId = experimentTeacherMapper.getEtIdByEid(experimentVo.getExperimentId());
			if(etId != null){
				experimentVo.setId(selectCourseService.getIdByStuIdAndEtId(etId,getUserId()));
			}
			String studentNo=studentService.selectStudentxh(getUserId());
			//定义两个上传文件的路径
	        String reportpath = null;
	        String picturepath = null;
	        String sourcecodepath = null;
	        //定义上传过程管理标记
	        boolean flag = true;
	        //定义文件保存的位置
	        String path = request.getSession().getServletContext().getRealPath("");
	        File directory = new File(path);
	        File file = new File(directory.getParentFile().toString()).getParentFile();
	        path =  file.toString();
	        path = path + "\\file\\"+classId+File.separator+etId;
	        /*String sqlPath = File.separator+"statics"+File.separator+"uploadfiles";*/
	        //循环读取文件信息
	        for(int i=0;i<attachs.length;i++){
	        	CommonsMultipartFile attach = attachs[i];
	            //获取源文件名
	            //String fileName= attach.getOriginalFilename();
	        	String fileName=attach.getOriginalFilename();  
	        	if(!fileName.isEmpty()){
		            if(!fileName.substring(0,studentNo.length()).equals(studentNo)){
		            	return renderError("文件命名不规范学号未能匹配！");
		            }
		            fileName=createFileName(fileName,i,experimentVo,studentNo);
		            String tempPath = null;
	                //创建新的文件，用于接收用户上传的文件流
		            if(i==0){
		            	tempPath = path + File.separator + "report\\";
	                }else if(i==1){
	                	tempPath = path + File.separator + "picture\\";
	                }else{
	                	tempPath = path + File.separator+ "sourcecode\\";
	                }
	                File targetFile = new File(tempPath, fileName);
	                if(!targetFile.exists()){
	                    targetFile.mkdirs();
	                }
	                //将上传的文件保存
	                try {
	                    attach.transferTo(targetFile);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    flag = false;
	                } 	
	                //更新上传的路径
	                if(i==0){
	                	reportpath = tempPath + fileName;
	                }else if(i==1){
	                	picturepath = tempPath +  fileName;
	                }else{
	                	sourcecodepath = tempPath + fileName;
	                }
	        	}
	        }        
	        //准备experimentVo
	        if(flag){
	        	experimentVo.setReportPath(reportpath);
	        	experimentVo.setPicturePath(picturepath);
	        	experimentVo.setSourcecodePath(sourcecodepath);
	        }  
	        selectCourseService.insertintoScore(experimentVo);
	        selectCourseService.updateStatus(experimentVo.getId());
		} catch (Exception e) {
			logger.error("我的提交添加失败，add出错{}", e);
			e.printStackTrace();
			return renderError("保存失败，系统内部错误！");
		}
		return renderSuccess("保存成功!");
	}
	public String createFileName(String fileName,int i,ExperimentVo experimentVo,String studentNo){
        int index = fileName.lastIndexOf(".");     
        if (index == -1) {
            return null;
        }
        String result = fileName.substring(index + 1);
        String experimentName;//实验题目名称
        if(experimentVo.getExperimentId()==null){
        	experimentName = experimentMapper.getNameByEid(experimentTeacherMapper.getExperimentByEid(experimentVo.getEtId()));
        }else{
        	experimentName = experimentMapper.getNameByEid(experimentVo.getExperimentId());
        }
        String cuorseName=courseService.selectNameByEid(experimentVo.getCourseId());
        if(i==0){
        	fileName=studentNo+"-"+getUserName()+"-"+cuorseName+"-"+experimentName+"-报告."+result;
        }
        else if(i==1){
        	fileName=studentNo+"-"+getUserName()+"-"+cuorseName+"-"+experimentName+"-截图."+result;
        }else{
        	fileName=studentNo+"-"+getUserName()+"-"+cuorseName+"-"+experimentName+"-源码."+result;
        }
		return fileName;
	} 
	/*@RequestMapping(value = "/exportOne/{cid}/{classId}/{experimentId}", method = RequestMethod.GET)
	@LogCustom(desc = "导出了成绩信息！")
	public String exportReport(HttpServletRequest request, HttpServletResponse response,@PathVariable("cid") Long cid,@PathVariable("classId")Long classId,
			@PathVariable("experimentId") Long experimentId)  throws ServletException, IOException {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行一次实验报告导出");
			try {
				List<File> files = new ArrayList<File>();
		        StudentClass stuclass =  studClassMapper.selectByPrimaryKey(classId);
		        Experiment experiment = experimentMapper.selectByPrimaryKey(experimentId);
		        Long etId = experimentTeacherMapper.getEtIdByEid(experimentId);
		        String path = request.getSession().getServletContext().getRealPath("");
		        File directory = new File(path);
		        File file = new File(directory.getParentFile().toString()).getParentFile();
		        path =  file.toString();
		        path = path + "\\file\\"+classId+File.separator+etId;
		        File srcdir = new File(path);   
		        if (!srcdir.exists()){   
		        	srcdir.mkdir();
		        }
		           
		        Project prj = new Project();   
		        Zip zip = new Zip();   
		        String fileName = stuclass.getName()+experiment.getName()+"-"+UUID.randomUUID().toString() + ".zip";
		        File zipFile =  new File(path+"\\"+fileName);
		        zip.setProject(prj);   
		        zip.setDestFile(zipFile);   
		        FileSet fileSet = new FileSet();   
		        fileSet.setProject(prj);   
		        fileSet.setDir(srcdir);   
		        zip.addFileset(fileSet);   
		           
		        zip.execute(); 
	        	downLoad(path+"\\"+fileName, request, response);
		        System.gc();
		        zipFile.delete();
		       
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}*/
	
	@RequestMapping(value = "/exportOne/{cid}/{classId}/{experimentId}", method = RequestMethod.GET)
	@LogCustom(desc = "导出了成绩信息！")
	public String exportReport(HttpServletRequest request, HttpServletResponse response,@PathVariable("cid") Long cid,@PathVariable("classId")Long classId,
			@PathVariable("experimentId") Long experimentId)  throws ServletException, IOException {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行一次实验报告导出");
			try {
				List<File> files = new ArrayList<File>();
		        StudentClass stuclass =  studClassMapper.selectByPrimaryKey(classId);
		        Experiment experiment = experimentMapper.selectByPrimaryKey(experimentId);
		        Long etId = experimentTeacherMapper.getEtIdByEid(experimentId);
		        String path = request.getSession().getServletContext().getRealPath("");
		        File directory = new File(path);
		        File file = new File(directory.getParentFile().toString()).getParentFile();
		        path =  file.toString();
		        path = path + "\\file\\"+classId+File.separator+etId;
		        String fileName = stuclass.getName()+experiment.getName()+"实验报告"+ ".zip";
		        FileSystemView fsv = FileSystemView.getFileSystemView();
		        File com =fsv.getHomeDirectory();    //读取桌面路径的方法了
		        //String outDir = com.getPath()+"/"+fileName;
		        String outDir = com.getPath()+"\\"+fileName;
		        String[] srcDir = { path+"\\"+"report", path+"\\"+"sourcecode", path+"\\"+"picture" };
		        ZipUtil.toZip(srcDir, outDir, true);
		        downLoad(outDir, request, response);
		        File outfile = new File(outDir);
		        System.gc();
		        outfile.delete();
		        /*File srcdir = new File(path);   
		        if (!srcdir.exists()){   
		        	srcdir.mkdir();
		        }
		           
		        Project prj = new Project();   
		        Zip zip = new Zip();   
		        String fileName = stuclass.getName()+experiment.getName()+"-"+UUID.randomUUID().toString() + ".zip";
		        File zipFile =  new File(path+"\\"+fileName);
		        zip.setProject(prj);   
		        zip.setDestFile(zipFile);   
		        FileSet fileSet = new FileSet();   
		        fileSet.setProject(prj);   
		        fileSet.setDir(srcdir);   
		        zip.addFileset(fileSet);   
		           
		        zip.execute(); 
	        	downLoad(path+"\\"+fileName, request, response);
		        System.gc();
		        zipFile.delete();*/
		       
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	/**
	 * @Title: update
	 * @Description: 设置了一条学生报告重做信息
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value = "/setRedo/{id}", method = RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc = "设置了一条学生报告重做信息！")
	public Object setRedo(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行学生报告设置重做信息");
		int res = 0;
		try {
			res = scoreService.updateScore(Long.valueOf(id));
		} catch (DataIntegrityViolationException e) {
			logger.error("update 处理 出错，无法设置重做！", e);
			return renderError("设置重做失败");
		} catch (Exception e) {
			logger.error("update 设置重做处理 出错{}", e);
			return renderError("设置重做失败，系统内部出错！");
		}
		if (res == 0) {
			return renderError("报告设置重做失败，无法对该报告进行设置重做！");
		}
		return renderSuccess("该报告设置重做成功！");
	}	
}
