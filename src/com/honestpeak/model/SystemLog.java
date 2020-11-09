package com.honestpeak.model;

import java.io.Serializable;
import java.util.Date;

import com.honestpeak.constant.Constants;
import com.honestpeak.result.IdEncrypt;

public class SystemLog extends IdEncrypt implements Serializable {

    private String loginName;

    private String optContent;

    private String optIp;

    private Date optTime;

    private static final long serialVersionUID = 1L;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getOptContent() {
        return optContent;
    }

    public void setOptContent(String optContent) {
        this.optContent = optContent == null ? null : optContent.trim();
    }

    public String getOptIp() {
        return optIp;
    }

    public void setOptIp(String optIp) {
        this.optIp = optIp == null ? null : optIp.trim();
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }
    
    /**
     * @Title: optContentFront
     * @Description: 前台操作信息显示类
     * @return
     */
    public String optContentFront() {
    	String opt = optContent==null?null:(optContent.length()>Constants.CONTENT_FRON_LENGTH ? optContent.substring(0, Constants.CONTENT_FRON_LENGTH).concat("...") : optContent);
        return opt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginName=").append(loginName);
        sb.append(", optContent=").append(optContent);
        sb.append(", optIp=").append(optIp);
        sb.append(", optTime=").append(optTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}