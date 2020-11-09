package com.honestpeak.mapper;

import com.honestpeak.model.LoginRecord;

public interface LoginRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoginRecord record);

    int insertSelective(LoginRecord record);

    LoginRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoginRecord record);

    int updateByPrimaryKey(LoginRecord record);
    
    /**
     * @Title: getLastLoginRecord
     * @Description: 根据用户登录名，获取最后一次登录信息
     * @param loginName
     * @return
     */
    LoginRecord getLastLoginRecord(String loginName);
    
}