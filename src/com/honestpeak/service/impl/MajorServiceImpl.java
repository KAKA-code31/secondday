package com.honestpeak.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.MajorMapper;
import com.honestpeak.model.Major;
import com.honestpeak.model.StudentClass;
import com.honestpeak.service.MajorService;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.MajorVo;
@Service
public class MajorServiceImpl implements MajorService {
@Resource
private MajorMapper majorMapper;
	@Override
	public Page<MajorVo> getAllForPage(int i,String departmentName, MajorVo major) {
		//获取所有专业信息
		Page<MajorVo> page=new Page<>(i,PageConstants.PAGESIZE);
		List<MajorVo> majors=majorMapper.findMajorPage(major,departmentName,page);
		page.setResultList(majors);
		return page;
	}
	@Override
	public int deleteAll(String[] ids) {
		return majorMapper.deleteByIds(ids);
	}
	@Override
	public MajorVo getById(Long id) {
		return majorMapper.getById(id);
	}
	@Override
	public void update(MajorVo major) {
		majorMapper.updateByPrimaryKey(major);
		
	}
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		majorMapper.deleteByPrimaryKey(id);
	}
	@Override
	public void add(Major newMajor) {
		majorMapper.insertSelective(newMajor);
	}
	@Override
	public List<Major> getAll() {
		return majorMapper.getAll();
	}
	@Override
	public List<Major> findMajorByDepartId(Long departId) {
		return majorMapper.findMajorByDepartId(departId);
	}
	@Override
	public Major findMajorByDepartIdAndMajorName(Long departId, String majorName) {
		return majorMapper.selectByPrimaryDepartIdAndMajorName(departId, majorName);
	}
	@Override
	public void addMajorVo(MajorVo major) {
		majorMapper.add(major);
		
	}
	@Override
	public MajorVo getByName(String majorName) {
		return majorMapper.getByName(majorName);
	}
	@Override
	public Major getByName1(String majorName) {
		return majorMapper.getByName1(majorName);
	}


}
