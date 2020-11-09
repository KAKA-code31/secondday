package com.honestpeak.model;

import java.io.Serializable;

import com.honestpeak.result.IdEncrypt;

/**
 * @ClassName: User
 * @Description: 基础用户类
 * @author Jeabev
 * @date 2016年11月10日 上午10:49:26
 */
public class User extends IdEncrypt implements Serializable {
	private static final long serialVersionUID = -6304229259126362838L;
	
	public static final int TYPE_STUDENT = 0;
	public static final int TYPE_TEACHER = 1;
	public static final int TYPE_ADMIN = 2;
	
	protected String userId;
	protected String name;//姓名
	protected int adminType;//用户类型，教师1，管理员2，学生0
	public int getAdminType() {
		return adminType;
	}

	public void setAdminType(int adminType) {
		this.adminType = adminType;
	}

	protected String password;
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

}
