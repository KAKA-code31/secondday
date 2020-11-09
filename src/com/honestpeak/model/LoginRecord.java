package com.honestpeak.model;

import java.io.Serializable;

import com.honestpeak.result.IdEncrypt;

public class LoginRecord extends IdEncrypt implements Serializable {

    private String loginName;

    private String loginTime;

    private String loginIP;

    private Integer loginType;

    private Integer loginStatus;

    private Integer failureType;

    private static final long serialVersionUID = 1L;

    
    /* 构造方法 */
	public LoginRecord() {
		super();
	}

	public LoginRecord(String loginName, String loginTime, String loginIP, Integer loginType, Integer loginStatus,
			Integer failureType) {
		super();
		this.loginName = loginName;
		this.loginTime = loginTime;
		this.loginIP = loginIP;
		this.loginType = loginType;
		this.loginStatus = loginStatus;
		this.failureType = failureType;
	}
	
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime == null ? null : loginTime.trim();
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP == null ? null : loginIP.trim();
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Integer getFailureType() {
        return failureType;
    }

    public void setFailureType(Integer failureType) {
        this.failureType = failureType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginName=").append(loginName);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", loginIP=").append(loginIP);
        sb.append(", loginType=").append(loginType);
        sb.append(", loginStatus=").append(loginStatus);
        sb.append(", failureType=").append(failureType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}