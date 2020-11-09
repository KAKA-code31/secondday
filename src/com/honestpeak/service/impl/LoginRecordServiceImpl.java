package com.honestpeak.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.honestpeak.constant.AccountConstants;
import com.honestpeak.mapper.LoginRecordMapper;
import com.honestpeak.model.LoginRecord;
import com.honestpeak.service.LoginRecordService;
import com.honestpeak.utils.DateTimeUtils;

@Service
public class LoginRecordServiceImpl implements LoginRecordService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	 
	@Resource
	private LoginRecordMapper loginRecordMapper;
	@Override
	public int userLoginSuccess(String loginName, String ip) {
		try {
			LoginRecord lr = new LoginRecord(loginName, DateTimeUtils.DateToDateTimeString(new Date()), ip, AccountConstants.LOGIN, AccountConstants.LOGSUCC, AccountConstants.DEFAULT);
			return loginRecordMapper.insert(lr);
		} catch (Exception e) {
			logger.error("用户登录成功方法记录信息时发生错误！",e);
			return 0;
		}
	}

	@Override
	public int userLoginFailure(String loginName, String ip, Integer failureType) {
		try {
			LoginRecord lr = new LoginRecord(loginName, DateTimeUtils.DateToDateTimeString(new Date()), ip, AccountConstants.LOGIN, AccountConstants.LOGFAIL, failureType);
			return loginRecordMapper.insert(lr);
		} catch (Exception e) {
			logger.error("用户登录失败方法记录信息时发生错误！",e);
			return 0;
		}
	}

	@Override
	public int userLogOutSuccess(String loginName, String ip) {
		try {
			LoginRecord lr = new LoginRecord(loginName, DateTimeUtils.DateToDateTimeString(new Date()), ip, AccountConstants.LOGOUT, AccountConstants.LOGSUCC, AccountConstants.DEFAULT);
			return loginRecordMapper.insert(lr);
		} catch (Exception e) {
			logger.error("用户登出成功方法记录信息时发生错误！",e);
			return 0;
		}
	}

	@Override
	public int userLogOutFailure(String loginName, String ip, Integer failureType) {
		try {
			LoginRecord lr = new LoginRecord(loginName, DateTimeUtils.DateToDateTimeString(new Date()), ip, AccountConstants.LOGOUT, AccountConstants.LOGFAIL, failureType);
			return loginRecordMapper.insert(lr);
		} catch (Exception e) {
			logger.error("用户登出失败方法记录信息时发生错误！",e);
			return 0;
		}
	}

	@Override
	public int insert(LoginRecord lr) {
		return loginRecordMapper.insert(lr);
	}
	
}
