package com.honestpeak.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.SchoolMapper;
import com.honestpeak.model.School;
import com.honestpeak.service.SchoolService;
import com.honestpeak.utils.Page;

@Service
public class SchoolServiceImpl implements SchoolService{
	@Resource
	private SchoolMapper schoolMapper;
	
	@Override
	public Page<School> getAllForPage(int i, School school) {
		// 获取所有院系信息
		Page<School> page = new Page<>(i, PageConstants.PAGESIZE);
		List<School> schools = schoolMapper.findSchoolPage(school, page);
		page.setResultList(schools);
		return page;
	}

	@Override
	public void add(School school) {
		// 添加院系信息
		schoolMapper.insert(school);
	}

	@Override
	public void delete(Long id) {
		// 删除院系信息
		schoolMapper.deleteByPrimaryKey(id);
	}

	@Override
	public School getById(Long id) {
		// 根据id获取院系
		return schoolMapper.selectByPrimaryKey(id);
	}

	@Override
	public int update(School school) {
		// 更新院系信息
		return schoolMapper.updateByPrimaryKey(school);
		
	}

	@Override
	public int deleteAll(String[] ids) {
		// 批量删除院系信息
		return  schoolMapper.deleteByIds(ids);
		
	}

	@Override
	public List<School> getAll() {
		// 获取全部院系信息
		return schoolMapper.getAll();
	}

	@Override
	public School getByName(String school) {
		return schoolMapper.selectByName(school);
	}

	@Override
	public int insertSelective(School newSchool) {
		return schoolMapper.insertSelective(newSchool);
	}

}
