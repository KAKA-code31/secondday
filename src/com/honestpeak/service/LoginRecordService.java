package com.honestpeak.service;

import com.honestpeak.model.LoginRecord;

/**
 * @ClassName: LoginRecordService
 * @Description: 用户登陆状态 逻辑控制 接口
 * @author Jeabev
 * @date 2016年9月18日 下午4:39:50
 */
public interface LoginRecordService {
	
	public int insert(LoginRecord lr);
	
	/**
	 * @Title: userLoginSuccess
	 * @Description: 用户登陆 时 成功
	 * @return
	 */
	public int userLoginSuccess(String loginName,String ip);
	
	/**
	 * @Title: userLoginFailure
	 * @Description: 用户登陆 时 失败
	 * @param loginName
	 * @param ip
	 * @return
	 */
	public int userLoginFailure(String loginName, String ip, Integer failureType);
	
	/**
	 * @Title: userLogOutSuccess
	 * @Description: 用户 登出 时 成功
	 * @param loginName
	 * @param ip
	 * @return
	 */
	public int userLogOutSuccess(String loginName,String ip);
	
	/**
	 * @Title: userLogOutFailure
	 * @Description: 用户 登出 时 失败
	 * @param loginName
	 * @param ip
	 * @param failureType
	 * @return
	 */
	public int userLogOutFailure(String loginName, String ip, Integer failureType);
}
