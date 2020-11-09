package com.honestpeak.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honestpeak.constant.Constants;
import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.MajorMapper;
import com.honestpeak.mapper.RoleMapper;
import com.honestpeak.mapper.ScoreMapper;
import com.honestpeak.mapper.SelectCourseMapper;
import com.honestpeak.mapper.StudentClassMapper;
import com.honestpeak.mapper.StudentMapper;
import com.honestpeak.mapper.UserRoleMapper;
import com.honestpeak.model.Depart;
import com.honestpeak.model.Major;
import com.honestpeak.model.Score;
import com.honestpeak.model.SelectCourse;
import com.honestpeak.model.Student;
import com.honestpeak.model.StudentClass;
import com.honestpeak.model.User;
import com.honestpeak.model.UserRole;
import com.honestpeak.result.Result;
import com.honestpeak.service.DepartmentService;
import com.honestpeak.service.MajorService;
import com.honestpeak.service.StudentClassService;
import com.honestpeak.service.StudentService;
import com.honestpeak.utils.Encrypt;
import com.honestpeak.utils.ExcelUtils;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;
import com.honestpeak.utils.StringUtil;
import com.honestpeak.vo.ExperimentVo;
import com.honestpeak.vo.MajorVo;
import com.honestpeak.vo.StudentImportVo;
import com.honestpeak.vo.StudentVo;

import net.sf.json.JSONObject;
/**
 * @Title: StudentServiceImpl.java 
 * @Package com.honestpeak.service.impl 
 * @Description: 学生业务逻辑具体实现 
 * @author BPC
 * @date 2017年8月1日 上午10:25:54 
 * @version V1.0
 */
@Service
public class StudentServiceImpl implements StudentService {
	
	@Resource
	private StudentMapper studentMapper;
	@Resource
	private StudentClassMapper studentClassMapper;
	@Resource
	private SelectCourseMapper selectCourseMapper;
	@Resource
	private ScoreMapper scoreMapper;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private MajorService majorService;
	@Resource
	private MajorMapper majorMapper;
	@Resource
	private StudentClassService studentClassService;
	@Resource
	private UserRoleMapper userRoleMapper;
	@Resource
	private RoleMapper roleMapper;
	
	@Override
	public Page<StudentVo> findStudentPage(Student student, int currentPage) {
		Page<StudentVo> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<StudentVo> studentVos = studentMapper.findStudentPage(student, page);
		page.setResultList(studentVos);
		return page;
	}


	@Override
	public Student selectStudentClass(String userId) {
		return studentMapper.selectStudentClass(userId);
	}

	@Override
	@Transactional
	public int addStudent(StudentVo studentVo) throws Exception{
		Long roleId=roleMapper.getByRoleName("学生").getId();
		Student student =new Student();
		if(studentVo!=null){
			student.setClassId(Long.valueOf(QEncodeUtil.decryptId(studentVo.getStudentClass().getEncryptId())));
			student.setUserId(studentVo.getUserId());
			student.setName(studentVo.getName());
			student.setSex(studentVo.getSex());
			if(studentVo.getPhoneno() != null){
				student.setPhoneno(studentVo.getPhoneno());
			}
			if(studentVo.getEmail() != null){
				student.setEmail(studentVo.getEmail());
			}
			String passWord = "";
			passWord = Constants.DEFAULT_PASSWORD;
			student.setPassword(Encrypt.SHA256(passWord));
			student.setDefaultPassword(Encrypt.SHA256(passWord));
			studentMapper.insertSelective(student);
			Long studentId = studentMapper.selectByUserId(student.getUserId());
			if(studentId!= null){
				UserRole userRole = new UserRole();
				userRole.setUserId(studentId);
				userRole.setRoleId(roleId);
				return userRoleMapper.insertSelective(userRole);
			}
		}
		return 0;
	}

	@Override
	@Transactional
	public int deleteMore(String[] ids) {
		if(ids!=null){
			return studentMapper.deleteMore(ids);
		}
		return 0;
	}

	@Override
	@Transactional
	public int deleteStudent(Long id) {
		if(id!=null){
			return studentMapper.deleteByPrimaryKey(id);
		}
		return 0;
	}
	
	@Override
	public StudentVo selectStudentVo(Long studentId) {
		if(studentId!=null){
			return studentMapper.selectStudentVoByPrimaryKey(studentId);
		}
		return null;
	}
	@Override
	public StudentVo selectStudentVoById(Long studentId){
		if(studentId!=null){
			return studentMapper.selectStudentVoById(studentId);
		}
		return null;		
	}

	@Override
	@Transactional
	public int updateStudent(StudentVo studentVo)  throws Exception{
		Student student =new Student();
		if(studentVo!=null){
			student.setId(Long.valueOf(QEncodeUtil.decryptId(studentVo.getEncryptId())));
			student.setUserId(studentVo.getUserId());
			student.setName(studentVo.getName());
			student.setSex(studentVo.getSex());
			if(studentVo.getPhoneno() != null){
				student.setPhoneno(studentVo.getPhoneno());
			}
			if(studentVo.getEmail() != null){
				student.setEmail(studentVo.getEmail());
			}
			student.setPassword(Encrypt.SHA256("111333"));
			return studentMapper.updateByPrimaryKeySelective(student);
		}
		return 0;
	}

	public int updateClass(StudentVo studentVo,HttpServletRequest request){
		Student student =new Student();
		if(studentVo!=null){
			student.setId(Long.valueOf(QEncodeUtil.decryptId(studentVo.getEncryptId())));
			student.setClassId(studentVo.getClassId());
			if(studentMapper.updateByPrimaryKeySelective(student)==1){
				//if(selectCourseMapper.updateClassByStudentId(SelectCourse)>0){
					List<SelectCourse> selectCourseList=selectCourseMapper.selectByStudentId(Long.valueOf(QEncodeUtil.decryptId(studentVo.getEncryptId())));
					for(int i=0;i<selectCourseList.size();i++){
						SelectCourse SelectCourse=new SelectCourse();
						SelectCourse.setClassId(studentVo.getClassId());
						SelectCourse.setStatus(selectCourseList.get(i).getStatus());
						SelectCourse.setFlag(selectCourseList.get(i).getFlag());
						SelectCourse.setStudentId(Long.valueOf(QEncodeUtil.decryptId(studentVo.getEncryptId())));
						selectCourseMapper.updateClassByStudentId(SelectCourse);
						Score scoreForUpdata=new Score();
						scoreForUpdata.setSelectcourseId(selectCourseList.get(i).getId());
						Score score=scoreMapper.selectBySelectcourseId(selectCourseList.get(i).getId());
						if(score!=null){
					        String path = request.getSession().getServletContext().getRealPath("");
					        File directory = new File(path);
					        File file = new File(directory.getParentFile().toString()).getParentFile();
					        path =  file.toString();
					        path = path + "\\file\\"+studentVo.getClassId()+File.separator+selectCourseList.get(i).getExprimentTeacherId();
							System.out.println(score.getReportpath());
							if(score.getReportpath()!=null){
								File Ysfile = new File(score.getReportpath());//移动之前
				        		File Xzfile = new File(path+"\\report"); //移动到的文件夹
				        		if(!Xzfile.exists()){ 
				        			Xzfile.mkdirs(); 
				        		}
				        		scoreForUpdata.setReportpath(path+"\\report"+"\\"+Ysfile.getName());
								if(Ysfile.renameTo(new File(path+"\\report"+"\\"+Ysfile.getName()))){
									Ysfile.delete();
								}							
							}
							if(score.getPicturepath()!=null){
								File Ysfile = new File(score.getPicturepath());
				        		File Xzfile = new File(path+"\\picture"); //移动到的文件夹
				        		if(!Xzfile.exists()){ 
				        			Xzfile.mkdirs(); 
				        		}
				        		scoreForUpdata.setPicturepath(path+"\\picture"+"\\"+Ysfile.getName());
								if(Ysfile.renameTo(new File(path+"\\picture"+"\\"+Ysfile.getName()))){
									Ysfile.delete();
								}							
							}
							if(score.getSourcecodepath()!=null){
								File Ysfile = new File(score.getSourcecodepath());
				        		File Xzfile = new File(path+"\\sourcecode"); //移动到的文件夹
				        		if(!Xzfile.exists()){ 
				        			Xzfile.mkdirs(); 
				        		}
				        		scoreForUpdata.setSourcecodepath(path+"\\sourcecode"+"\\"+Ysfile.getName());
								if(Ysfile.renameTo(new File(path+"\\sourcecode"+"\\"+Ysfile.getName()))){
									Ysfile.delete();
								}							
							}
							scoreMapper.updatePath(scoreForUpdata);
						}
					}
				}
			return 1;
			}
		//}
		return 0;
	}
	@Override
	@Transactional
	public int updateStudentPassWord(long studentId) {
		Student stu = studentMapper.selectByPrimaryKey(studentId);
		if(stu != null) {
			String passWord = Constants.DEFAULT_PASSWORD;
			stu.setPassword(Encrypt.SHA256(passWord));
			return studentMapper.updateByPrimaryKeySelective(stu);
		}
		return 0;
	}


	@Override
	public User loginUserByLoginName(String username) {
		
		return studentMapper.loginUserByLoginName(username);
	}


	@Override
	public int updateNewPassWord(String encryption, Long id) {
		return studentMapper.updateNewPassWord(encryption, id);
	}


	@Override
	public Student selectByPrimaryKey(Long id) {
		return studentMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public Long getId(String userName) {
		// 根据学生姓名查找主键
		return studentMapper.getId(userName);
	}
	
	@Override
	public User findUserById(Long id) {
		
		return studentMapper.getById(id);
	}


	@Override
	public int updateByPrimaryKeySelective(Student record) {
		return studentMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public User loginUserByUserAccount(String username) {
		return studentMapper.getUserByUserAccount(username);
	}


	@Override
	public Result importStudent(String root) throws IOException{
		Long roleId = roleMapper.getByRoleName("学生").getId();
		String rootLastStr = root.substring(root.length() - 1, root.length());
		File file = new File(root);
		int count = 0;
		InputStream is = new FileInputStream(file);
		// 添加验证规则
		Map<Integer, String> rule = new HashMap<>();
		rule.put(0, "学号:userId");
    	rule.put(1, "姓名:name");
    	rule.put(2, "性别:sex");
    	rule.put(3, "班级:className");
    	rule.put(4, "专业:majorName");
    	rule.put(5, "系别:departName");
    	rule.put(6, "手机:phoneno");
    	rule.put(7, "邮箱:email");
		Result result = ExcelUtils.readExcel(is, StringUtil.getRootLastString(rootLastStr), rule);
		// 信息读取错误直接返回result
		if (!result.isSuccess()) {
			return result;
		}
		
		// json 转java对象
		@SuppressWarnings("unchecked")
		List<String> datas = (List<String>) result.getObj();
		StudentClass sc = null;
		List<StudentVo> studentVoList = new ArrayList<StudentVo>();
		StudentVo studentVo = null;
		for (String json : datas) {
			studentVo = new StudentVo();
			StudentImportVo csv = new StudentImportVo();
			JSONObject obj = JSONObject.fromObject(json);
			csv = (StudentImportVo) JSONObject.toBean(obj, StudentImportVo.class);

			studentVo.setUserId(csv.getUserId()!=null?csv.getUserId():null);
			studentVo.setName(csv.getName()!=null?csv.getName():null);
			studentVo.setPhoneno(csv.getPhoneno()!=null?csv.getPhoneno():null);
			studentVo.setEmail(csv.getEmail()!=null?csv.getEmail():null);
			studentVo.setPassword(csv.getUserId()!=null?Encrypt.SHA256("111333"):null);
			Integer sex = 2;
			if("男".equals(csv.getSex()!=null?csv.getSex():null)){
				sex = 1;
			}else{
				sex = 2;
			}
			studentVo.setSex(sex);
			//判断院系是否存在,存在就取，不存在就在初始化日志表中插入记录
			if(csv.getDepartName() != null){
				Depart department = departmentService.getByName(csv.getDepartName());
				if(department != null && csv.getMajorName() != null){
					Major major = majorService.getByName1(csv.getMajorName());
					if(major != null){
						if((major.getDepartId() == department.getId()) && csv.getClassName() != null){
						StudentClass studentClass =  studentClassMapper.getByName(csv.getClassName());
							if(studentClass != null && (studentClass.getMajorId() == major.getId())){
								studentVo.setClassId(studentClass.getId());
								studentVoList.add(studentVo);
								studentMapper.importStudent(studentVoList);
								for(int i=0;i<studentVoList.size();i++){
									Long studentId = studentMapper.selectByUserId(studentVoList.get(i).getUserId());
									if(studentId!= null){
										if(userRoleMapper.checkUserRole("学生",studentId) == null){
											UserRole userRole = new UserRole();
											userRole.setUserId(studentId);
											userRole.setRoleId(roleId);
											userRoleMapper.insertSelective(userRole);
										}
									}
								}
							}
						}
					}else{
						continue;
					}
				}else{
					continue;
				}
			}else{
				continue;
			}
		}
		return result;
	}


	@Override
	public Student selectStudent(String userId) {
		return studentMapper.selectStudent(userId);
	}


	@Override
	public Long getClassIdById(Long userId) {
		return studentMapper.getClassIdById(userId);
	}
	@Override
	public List<Student> findStudentByClassId(Long classId,Long courseId){
		return studentMapper.selectStudentByClassId(classId,courseId);
	}
	@Override
	public String selectStudentxh(Long studentid){
		return studentMapper.selectStudentxh(studentid);
	}
	
	@Override
	public Student selectStudentByselectCourseId(Long selectCourseId){
		return studentMapper.selectStudentByselectCourseId(selectCourseId);
	}

}
