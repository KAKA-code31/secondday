package com.honestpeak.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.SystemLogMapper;
import com.honestpeak.model.SystemLog;
import com.honestpeak.service.SystemLogService;
import com.honestpeak.utils.Page;

@Service
public class SystemLogServiceImpl implements SystemLogService {
	
	@Resource
	private SystemLogMapper systemLogMapper;
	
	@Override
	public int insertLog(SystemLog sysLog) {
		return systemLogMapper.insert(sysLog);
	}

	@Override
	public Page<SystemLog> findSystemLogPage(SystemLog systemLog, int currentPage) {
		Page<SystemLog> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<SystemLog> systemLogs = systemLogMapper.findSystemLogPage(systemLog, page);
		page.setResultList(systemLogs);
		return page;
	}

	@Override
	public SystemLog getById(Long id) {
		return systemLogMapper.selectByPrimaryKey(id);
	}

}
