package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.SystemLog;
import com.honestpeak.utils.Page;

public interface SystemLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemLog record);

    int insertSelective(SystemLog record);

    SystemLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemLog record);

    int updateByPrimaryKey(SystemLog record);
    
    /**
     * @param systemLog 
     * @Title: findSystemLogPage
     * @Description: 分页查询系统日志信息
     * @param page 分页信息
     * @return
     */
    List<SystemLog> findSystemLogPage(@Param("systemLog")SystemLog systemLog, @Param("page")Page<SystemLog> page);
}