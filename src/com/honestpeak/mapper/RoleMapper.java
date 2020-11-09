package com.honestpeak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Role;
import com.honestpeak.result.IdEncrypt;
import com.honestpeak.utils.Page;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	List<Map<Long, String>> findRolePrivilegeListByRoleId(@Param("id")Long roleId);

	List<Role> findRolePage(@Param("role")Role role, @Param("page")Page<Role> page);

	List<Long> findRolePrivilegeIdsByRoleId(@Param("id")Long id);

	List<Long> findPrivilegeIdListByRoleId(@Param("id")Long id);

	int deleteAll(@Param("ids")String[] ids);

	List<Map<Long, String>> findAllRoleMap();

	List<Role> findAll();

	Role getByRoleName(@Param("roleName")String roleName);

}