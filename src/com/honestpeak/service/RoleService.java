package com.honestpeak.service;

import java.util.List;
import java.util.Map;

import com.honestpeak.model.Role;
import com.honestpeak.utils.Page;

public interface RoleService {
	
	int save(Role role);

	List<Long> findStuRoleIdListByUserId(String userId);

	List<Map<Long, String>> findRolePrivilegeListByRoleId(Long roleId);
	
	/**
	 * @Title: findRolePage
	 * @Description: 角色查询，带分页
	 * @param role 角色
	 * @param currentPage 当前页
	 * @return
	 */
	Page<Role> findRolePage(Role role, int currentPage);

	int updateRolePrivilege(Long id, String[] privilegeIds);

	List<Long> findPrivilegeIdListByRoleId(Long id);
	
	/**
	 * @Title: delete
	 * @Description: 批量删除
	 * @param split
	 * @return
	 */
	int delete(String[] split);
	
	/**
	 * @Title: delete
	 * @Description: 单个
	 * @param id
	 * @return
	 */
	int delete(Long id);

	Role selectById(Long id);

	int update(Role role);
	
	/**
	 * @Title: findAllRoleMap
	 * @Description: 查询系统所有可见角色
	 * @return
	 */
	List<Map<Long, String>> findAllRoleMap();
	
	/**
	 * @Title: findPrivilegeIdsByRoleId
	 * @Description: 这里返回的是加密串！！
	 * @param id
	 * @throws Exception
	 */
	String findPrivilegeIdsByRoleId(Long id) throws Exception;

	List<Role> findAllRole();

	List<Long> findTeacherRoleIdListByUserId(String userId);
}
