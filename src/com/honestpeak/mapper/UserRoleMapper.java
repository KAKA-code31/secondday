package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Teacher;
import com.honestpeak.model.UserRole;
import com.honestpeak.utils.Page;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

	List<Long> findStuRoleIdListByUserId(@Param("userId")String userId);

	int save(List<UserRole> urList);

	int deleteAllByUserId(@Param("userId")Long userId);

	int deleteAll(String[] ids);

	UserRole selectByUserId(Long id);

	List<Teacher> findUserRolePage(@Param("user")Teacher user, @Param("page")Page<Teacher> page);

	List<UserRole> findByUserId(@Param("userId")Long userId);

	List<Long> findTeacherRoleIdListByUserId(@Param("userId")String userId);

	Object checkUserRole(@Param("name")String name, @Param("studentId")Long studentId);

	int countByTeacherId(Long userId);

}