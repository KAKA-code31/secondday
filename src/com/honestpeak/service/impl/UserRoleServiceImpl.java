package com.honestpeak.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.RoleMapper;
import com.honestpeak.mapper.UserRoleMapper;
import com.honestpeak.model.Role;
import com.honestpeak.model.Teacher;
import com.honestpeak.model.UserRole;
import com.honestpeak.service.UserRoleService;
import com.honestpeak.utils.Page;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Resource
	private UserRoleMapper userRoleMapper;
	@Resource
	private RoleMapper roleMapper;
	
	@Override
	public int save(Long userId, Long[] roleIds) {
		if (userId == null || roleIds == null || roleIds.length == 0) {
			return 0;
		} else {
			List<UserRole> urLiost = new ArrayList<UserRole>();
			for (Long roleId : roleIds) {
				UserRole ur = new UserRole(userId, roleId);
				urLiost.add(ur);
			}
			return userRoleMapper.save(urLiost);
		}
	}

	/*@Override
	public List<Role> findUserRoles(Long userId) {
		List<Long> lists = userRoleMapper.findRoleIdListByUserId(userId);
		List<Role> roles = roleMapper.findAll();
		for (Role r : roles) {
			int flag = 0;
			for (Long i : lists) {
				if (r.getId().equals(i)) {
					flag = 1;
				}
			}
			if (flag == 1) {
				r.setStatus(0);
			} else {
				r.setStatus(1);
			}
		}
		return roles;
	}*/

	@Override
	public int update(Long userId, Long[] roleIds) {
		if (userId == null || roleIds == null || roleIds.length == 0) {
			return 0;
		} else {
			//先把关联全删除
			userRoleMapper.deleteAllByUserId(userId);
			List<UserRole> urList = new ArrayList<UserRole>();
			for (Long roleId : roleIds) {
				UserRole ur = new UserRole(userId, roleId);
				urList.add(ur);
			}
			return userRoleMapper.save(urList);
		}
	}

	@Override
	public int deleteAll(String[] ids) {
		if(ids==null || ids.length==0){
			return 0;
		}
		return userRoleMapper.deleteAll(ids);
	}

	@Override
	public int delete(Long userId) {
		return userRoleMapper.deleteAllByUserId(userId);
	}

	@Override
	public int save(Long userId, String[] roleIds, Integer roleType) {
		//先判断用户信息是否存在，不存在就直接返回失败信息
		if (userId == null || roleType==null) {
			return 0;
		} else {
			List<UserRole> urLiost = new ArrayList<UserRole>();
			for (String roleId : roleIds) {
				//这里有一个转换，就是把string型的roleIds转换成long型
				UserRole ur = new UserRole(userId, Long.valueOf(roleId));
				urLiost.add(ur);
			}
			return userRoleMapper.save(urLiost);
		}
	}

	@Override
	public int update(Long userId, String[] roleIds, Integer roleType) {
		if (userId == null || roleType == null) {
			return 0;
		} else {
			//先把关联全删除
			userRoleMapper.deleteAllByUserId(userId);
			//再调用保存方法，重新保存该用户的角色管理信息
			return this.save(userId, roleIds, roleType);
		}
	}

	@Override
	public UserRole selectByUserId(Long id) {
		return userRoleMapper.selectByUserId(id);
	}

	@Override
	public Page<Teacher> findUserRolePage(Teacher user, int currentPage) {
		Page<Teacher> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<Teacher> teachers = userRoleMapper.findUserRolePage(user, page);
		page.setResultList(teachers);
		return page;
	}

	@Override
	public int update(Long userId, String[] roleIds, String[] clubIds) {
		if(userId==null){
			return 0;//用户信息出错，直接返回零
		}
		
		//先删除原始角色信息
		userRoleMapper.deleteAllByUserId(userId);
		//若新分配的角色信息不为空
		if(roleIds!=null && roleIds.length>0){
			//构造角色信息列表
			List<UserRole> urList = new ArrayList<>();
			for (String roleId : roleIds) {
				//构造角色信息，并放入角色列表中，为接下来的批量保存做准备
				urList.add( new UserRole(userId, Long.valueOf(roleId)) );
			}
			//新角色信息批量保存
			userRoleMapper.save(urList);
		}
		
		
		return 1;//执行成功，返回1
	}

	@Override
	@Transactional
	public void save(UserRole userRole) {
		userRoleMapper.insertSelective(userRole);
	}

	/**
	 * 修改
	 */
	@Override
	@Transactional
	public void updateUserRole(UserRole userRole) {
		userRoleMapper.updateByPrimaryKeySelective(userRole);
	}

	@Override
	public List<UserRole> getByUserId(Long userId) {
		return userRoleMapper.findByUserId(userId);
	}

	@Override
	public int countByTeacherId(Long userId) {
		return userRoleMapper.countByTeacherId(userId);
	}

}
