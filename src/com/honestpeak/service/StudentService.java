package com.honestpeak.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.honestpeak.model.Student;
import com.honestpeak.model.User;
import com.honestpeak.result.Result;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.StudentVo;

/**
 * @Title: StudentService.java 
 * @Package com.honestpeak.service 
 * @Description: 学生信息管理业务 
 * @author BPC
 * @date 2017年7月22日 下午4:03:04 
 * @version V1.0
 */
public interface StudentService {

	/**
	 * @Title: findStudentPage
	 * @Description: 给定参数，分页查找出学生信息列表
	 * @param student 学生信息（查找条件）
	 * @param currentPage 当前页数（分页时用）
	 * @return
	 */
	public Page<StudentVo> findStudentPage(Student student, int currentPage);
	/**
	 * @Title: selectStudentClass
	 * @Description:根据学生学号查询学生姓名和班级
	 * @param userId
	 * @return
	 */

	public Student selectStudentClass(String userId);
	/**
	 * @Title: addStudent
	 * @Description: 添加学生
	 * @param student
	 * @return
	 */
	public int addStudent(StudentVo studentVo) throws Exception;
	
	/**
	 * @Title: deleteMore
	 * @Description:批量删除学生
	 * @param id
	 * @return
	 */
	public int deleteMore(String[] id);
	/**
	 * @Title: deleteStudent
	 * @Description:根据id删除学生addStudent
	 * @param id
	 * @return
	 */
	public int deleteStudent(Long ids);
	/**
	 * 根据学生id查询studentVo
	 * @param studentId
	 * @return
	 */
	public StudentVo selectStudentVo(Long studentId);
	/**
	 * 根据学生id查询studentVo
	 * @param studentId
	 * @return
	 */	
	public StudentVo selectStudentVoById(Long studentId);
	/**
	 * @Title: updateStudent
	 * @Description:修改学生信息
	 * @param studentVo
	 * @return
	 */
	public int updateStudent(StudentVo studentVo) throws Exception;
	
	public int updateClass(StudentVo studentVo,HttpServletRequest request);
	
	 /**
	 * @Title: updateStudentPassWord
	 * @Description: 重置学生密码未默认密码
	 * @param student
	 * @return
	 */
	public int updateStudentPassWord(long studentId);
	
	public User loginUserByLoginName(String username);
	
	public int updateNewPassWord(String encryption, Long id);
	/**
	 * 导入学生范围   Guo
	 * @param root
	 * @return
	 * @throws IOException
	 */
	public Student selectByPrimaryKey(Long id);
	public Long getId(String userName);
	public User findUserById(Long id);
	public int updateByPrimaryKeySelective(Student record);
	public User loginUserByUserAccount(String username);
	public Result importStudent(String path) throws IOException;
	public Student selectStudent(String userId);
	public Long getClassIdById(Long userId);
	/**
	 * 根据班级id获取学生
	 * @param classId
	 * @return
	 */
	public List<Student> findStudentByClassId(Long classId,Long courseId);
	
	/**
	 * 根据学生id查询学生学号
	 * @param studentid
	 * @return
	 */
	public String selectStudentxh(Long studentid);
	
	public Student selectStudentByselectCourseId(Long selectCourseId);
	
}
