package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Teacher;
import com.honestpeak.model.User;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.TeacherVo;
import com.honestpeak.vo.UserRoleVo;

public interface TeacherMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

	Teacher getUserByUserAccount(String username);

	UserRoleVo getUserRoleVoById(Long userId);

	List<Teacher> selectAllTeacher();

	String getIdentityByUserId(Long id);

	int updatePassWord(Long id, String defaultPassword);

	User loginUserByUserName(@Param("username")String username);

	String getUserIdById(Long userId);

	List<TeacherVo> findTeacherPage(@Param("user")TeacherVo user, @Param("page")Page<TeacherVo> page);

	int addTeacher(TeacherVo teacherVo);

	int updateTeacherPassWord(Long id);

	TeacherVo selectTeacherVo(Long id);

	int deleteMore(String[] keys);

	Teacher selectTeacher(String userId);
}