package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Privilege;
import com.honestpeak.utils.Pagination;

public interface PrivilegeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Privilege record);

    int insertSelective(Privilege record);

    Privilege selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Privilege record);

    int updateByPrimaryKey(Privilege record);
    
    List<Privilege> findPrivilegePage(@Param("privilege")Privilege privilege, @Param("page")Pagination page);

	int deleteAll(String[] ids);
	
	/**
	 * @Title: findAllPrivilegeByType
	 * @Description: 按权限项类型查找所有权限列表集合
	 * @param privilegeType
	 * @return
	 */
	List<Privilege> findAllPrivilegeByType(Integer privilegeType);
	
	List<Privilege> findAllPrivileg(@Param("privilege") Privilege privilege);
	/**
	 * @Title: findParentPrivilege
	 * @Description: 查询所有顶层节点
	 * @return
	 */
	List<Privilege> findParentPrivilege();
	
	/**
	 * @Title: findAllPrivilegeByTypeAndPid
	 * @Description: 按类型以及父节点主键，查找所有子节点
	 * @param ptriType
	 * @param privilegeId
	 * @return
	 */
	List<Privilege> findAllPrivilegeByTypeAndPid(@Param("priType") Integer priType, @Param("privilegeId") Long privilegeId);

}