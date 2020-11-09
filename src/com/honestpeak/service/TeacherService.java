package com.honestpeak.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Teacher;
import com.honestpeak.model.User;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.TeacherVo;
import com.honestpeak.vo.UserRoleVo;

public interface TeacherService {

	Teacher loginUserByUserAccount(String username);

	Teacher findUserById(Long id);
	
	/**
	 * @Title: getUserRoleVoById
	 * @Description: 通过用户Id获取用户管理的角色列表
	 * @param userId
	 * @return
	 */
	UserRoleVo getUserRoleVoById(Long userId);

	/**
	 * 查询所有的老师
	 * @return
	 */
	List<Teacher> selectAllTeacher();

	String getIdentityByUserId(Long id);

	int updatePassWord(Long id, String defaultPassword);

	User loginUserByUserName(String username);

	String getUserIdById(Long userId);

	Page<TeacherVo> findTeacherPage(TeacherVo user, int i);

	int deleteTeacher(Long id);

	int addTeacher(TeacherVo teacherVo);

	int updateTeacherPassWord(Long id);

	TeacherVo selectTeacherVo(Long id);

	int updateTeacher(TeacherVo teacherVo);

	int deleteMore(String[] keys);

	Teacher selectTeacher(String userId);

	int update(Teacher teacher);
}
