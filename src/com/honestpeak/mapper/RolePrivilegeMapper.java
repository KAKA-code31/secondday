package com.honestpeak.mapper;

import com.honestpeak.model.RolePrivilege;

public interface RolePrivilegeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePrivilege record);

    int insertSelective(RolePrivilege record);

    RolePrivilege selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePrivilege record);

    int updateByPrimaryKey(RolePrivilege record);

	void deleteAll(Long[] ids);
}