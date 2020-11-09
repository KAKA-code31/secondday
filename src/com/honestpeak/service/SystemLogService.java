package com.honestpeak.service;

import com.honestpeak.model.SystemLog;
import com.honestpeak.utils.Page;

public interface SystemLogService {
	/**
	 * 
	 * @Title: insertLog
	 * @Description: 日志插入
	 * @return int 返回类型
	 */
	
	public int insertLog(SystemLog sysLog);

	/**
	 * @Description: 日志分页显示
	 * @Title: findSystemLogPage 
	 * @param systemLog 查找条件
	 * @param currentPage 当前页
	 * @return Page<SystemLog> 返回类型
	 */
	public Page<SystemLog> findSystemLogPage(SystemLog systemLog, int currentPage);
	
	/**
	 * @Title: getById
	 * @Description: 根据主键获取SystemLog
	 * @param id
	 * @return
	 */
	public SystemLog getById(Long id);
}
