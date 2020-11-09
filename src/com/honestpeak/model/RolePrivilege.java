package com.honestpeak.model;

import java.io.Serializable;

import com.honestpeak.result.IdEncrypt;

public class RolePrivilege extends IdEncrypt implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long roleId;

    private Long privilegeId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

}