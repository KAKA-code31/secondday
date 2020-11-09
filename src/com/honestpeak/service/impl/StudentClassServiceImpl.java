package com.honestpeak.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.StudentClassMapper;
import com.honestpeak.model.StudentClass;
import com.honestpeak.service.StudentClassService;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.MajorVo;
import com.honestpeak.vo.StudentClassVo;
@Service
public class StudentClassServiceImpl implements StudentClassService {
@Resource
private StudentClassMapper studentClassMapper;
	@Override
	public Page<StudentClassVo> getAllForPage(int i,Long schoolId, Long departmentId, Long majorId,Long classId,String grade,StudentClassVo studentClassVo) {
		// TODO Auto-generated method stub
		Page<StudentClassVo> page=new Page<>(i,PageConstants.PAGESIZE);
		List<StudentClassVo> classes=studentClassMapper.getAllForPage(page,schoolId,departmentId,majorId,classId,grade,studentClassVo);
		page.setResultList(classes);
		return page;
	}
	@Override
	public StudentClassVo getById(Long id) {
		// TODO Auto-generated method stub
		return studentClassMapper.selectVoByPrimaryKey(id);
	}
	@Override
	public StudentClass selectClassByCode(String code) {
		return studentClassMapper.selectClassByCode(code);
	}
	@Override
	public StudentClass selectClassByName(String name) {
		return studentClassMapper.selectClassByName(name);
	}
	@Override
	public void update(StudentClassVo studentClassVo) {
		// TODO Auto-generated method stub
		studentClassMapper.updateByPrimaryKey(studentClassVo);
	}
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		studentClassMapper.deleteByPrimaryKey(id);
	}
	@Override
	public void deleteAll(String[] ids) {
		// TODO Auto-generated method stub
		studentClassMapper.deleteByIds(ids);
	}
	@Override
	public List<StudentClass> getAll() {
		// TODO Auto-generated method stub
		return studentClassMapper.getAll();
	}
	@Override
	public List<StudentClass> findClassByMajorId(Long majorId) {
		return studentClassMapper.findClassByMajorId(majorId);
	}
	@Override
	public List<StudentClass> selectAllClass() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public StudentClass findByClassNameAndMajorId(String className, Long majorId) {
		return studentClassMapper.findByClassNameAndMajorId(className , majorId);
	}
	@Override
	public void add(StudentClass studentClass) {
		studentClassMapper.insertSelective(studentClass);
	}

	
	@Override
	public void add(StudentClassVo studentClass) {
 		studentClassMapper.insert(studentClass);
	}
	
	@Override
	public List<StudentClass> findClassByDepartmentId(String departmentId) {
		return studentClassMapper.findClassByDepartmentId(departmentId);
	}
}
