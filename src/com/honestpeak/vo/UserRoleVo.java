package com.honestpeak.vo;

import java.util.ArrayList;
import java.util.List;

import com.honestpeak.model.Teacher;

public class UserRoleVo extends Teacher {
	
	private static final long serialVersionUID = -6247324735904581261L;
	
	/** 角色列表 */
	private List<InsVo> roles = new ArrayList<>();
	
	public List<InsVo> getRoles() {
		return roles;
	}
	public void setRoles(List<InsVo> roles) {
		this.roles = roles;
	}
	
}
