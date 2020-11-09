package com.honestpeak.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.DepartMapper;
import com.honestpeak.model.Depart;
import com.honestpeak.service.DepartmentService;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.DepartmentVo;


@Service
public class DepartmentServiceImpl implements DepartmentService{
	@Resource
	private DepartMapper departmentMapper;
	
	@Override
	public Page<DepartmentVo> getAllForPage(int i, String schoolName,DepartmentVo department ) {
		// 获取所有院系信息
		Page<DepartmentVo> page = new Page<>(i, PageConstants.PAGESIZE);
		List<DepartmentVo> departments = departmentMapper.findDepartmentPage(department,schoolName,page);
		page.setResultList(departments);
		return page;
	}

	@Override
	public void add(Depart department) {
		// 添加院系信息
		departmentMapper.insert(department);
	}

	@Override
	public void delete(Long id) {
		// 删除院系信息
		departmentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public DepartmentVo getById(Long id) {
		// 根据id获取院系
		return departmentMapper.selectByPrimaryKey1(id);
	}

	@Override
	public int update(Depart department) {
		// 更新院系信息
		return departmentMapper.updateByPrimaryKey(department);
		
	}

	@Override
	public int deleteAll(String[] ids) {
		// 批量删除院系信息
		return  departmentMapper.deleteByIds(ids);
		
	}

	@Override
	public List<Depart> getAll() {
		// 获取全部院系信息
		return departmentMapper.getAll();
	}

	@Override
	public Depart getByName(String departName) {
		return departmentMapper.selectByName(departName);
	}

	@Override
	public int insertSelective(Depart newDep) {
		return departmentMapper.insertSelective(newDep);
	}

	@Override
	public List<Depart> selectAllDepart() {
		return departmentMapper.selectAllDepart();
	}

	@Override
	public List<Depart> findDepartmentBySchoolId(Long SchoolId){
		return departmentMapper.findDepartmentBySchoolId(SchoolId);
	}
}
