package com.honestpeak.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.RoleMapper;
import com.honestpeak.mapper.RolePrivilegeMapper;
import com.honestpeak.mapper.UserRoleMapper;
import com.honestpeak.model.Role;
import com.honestpeak.model.RolePrivilege;
import com.honestpeak.service.RoleService;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;
/*
 * @ClassName: RoleServiceImpl
 * @Description: 角色控制
 * @author Jeabev
 * @date 2016年7月30日 下午5:07:31
 */
@Service
public class RoleServiceImpl implements RoleService{
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private RolePrivilegeMapper rolePrivilegeMapper;
	@Resource
	private UserRoleMapper userRoleMapper;

	@Override
	public List<Long> findStuRoleIdListByUserId(String userId) {
		List<Long> roleIds = userRoleMapper.findStuRoleIdListByUserId(userId);
		return roleIds;
	}

	@Override
	public List<Map<Long, String>> findRolePrivilegeListByRoleId(Long roleId) {
		
		return roleMapper.findRolePrivilegeListByRoleId(roleId);
	}

	@Override
	public Page<Role> findRolePage(Role role, int currentPage) {
		Page<Role> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<Role> roles = roleMapper.findRolePage(role,page);
		page.setResultList(roles);
		return page;
	}

	@Override
	public int updateRolePrivilege(Long id, String[] privilegeIds) {
		//查询出来，全部删除
		List<Long> rolePrivileges = roleMapper.findRolePrivilegeIdsByRoleId(id);
		if(rolePrivileges!=null && rolePrivileges.size()>0){
			Long[] rpArray = new Long[rolePrivileges.size()];
			rolePrivilegeMapper.deleteAll(rolePrivileges.toArray(rpArray));
		}
		//传入权限信息不为空
		if(privilegeIds!=null && privilegeIds.length > 0){
			RolePrivilege rp = null;
			for(String string : privilegeIds){
				rp = new RolePrivilege();
				rp.setPrivilegeId(Long.parseLong(string));
				rp.setRoleId(id);
				rolePrivilegeMapper.insert(rp);
			}
		}
		return 1;
	}

	@Override
	public List<Long> findPrivilegeIdListByRoleId(Long id) {
		if(id!=null){
			return roleMapper.findPrivilegeIdListByRoleId(id);
		}else{
			return null;
		}
	}

	@Override
	public int save(Role role) {
		return roleMapper.insertSelective(role);
	}

	@Override
	public int delete(String[] ids) {
		return roleMapper.deleteAll(ids);
	}

	@Override
	public int delete(Long id) {
		return roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Role selectById(Long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int update(Role role) {
		return roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public List<Map<Long, String>> findAllRoleMap() {
		return roleMapper.findAllRoleMap();
	}

	@Override
	public String findPrivilegeIdsByRoleId(Long id) throws Exception {
		if(id!=null){
			List<Long> list = roleMapper.findPrivilegeIdListByRoleId(id);
			//循环迭代出 num,num,num 这样的串
			if(list!=null && list.size()>0){
				StringBuffer sb = new StringBuffer("");
				for (Long pid:list) {
					sb.append(QEncodeUtil.encryptId(pid)+",");
				}
				//将最后一个,去掉
				return sb.substring(0, sb.length()-1);
			}
		}
		return null;
	}

	@Override
	public List<Role> findAllRole() {
		return roleMapper.findAll();
	}

	@Override
	public List<Long> findTeacherRoleIdListByUserId(String userId) {
		List<Long> roleIds = userRoleMapper.findTeacherRoleIdListByUserId(userId);
		return roleIds;
	}
}
