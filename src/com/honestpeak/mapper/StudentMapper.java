package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Student;
import com.honestpeak.model.User;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.StudentVo;

public interface StudentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

	List<StudentVo> findStudentPage(@Param("student")Student student, @Param("page")Page<StudentVo> page);

	Student selectStudentClass(String userId);

	Long selectByUserId(String userId);

	int deleteMore(String[] ids);

	StudentVo selectStudentVoByPrimaryKey(Long studentId);
	
	StudentVo selectStudentVoById(Long studentId);

	User loginUserByLoginName(String username);

	int updateNewPassWord(String encryption, Long id);

	Long getId(String userName);

	User getById(Long id);

	User getUserByUserAccount(String username);

	Long[] selectIds(Long classId);

	void importStudent(List<StudentVo> studentVoList);

	Student selectStudent(String userId);

	Long getClassIdById(Long userId);
	
	List<Student> selectStudentByClassId(@Param("classId")Long classId,@Param("courseId")Long courseId);
	
	String selectStudentxh(Long studentid);
	
	Student selectStudentByselectCourseId(Long selectCourseId);
}