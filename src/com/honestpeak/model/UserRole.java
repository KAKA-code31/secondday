package com.honestpeak.model;

import java.io.Serializable;

import com.honestpeak.result.IdEncrypt;

public class UserRole extends IdEncrypt implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

    private Long roleId;

    public UserRole() {
		super();
	}
    
    public UserRole(Long userId, Long roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
}