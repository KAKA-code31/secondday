package com.honestpeak.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honestpeak.constant.Constants;
import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.TeacherMapper;
import com.honestpeak.mapper.UserRoleMapper;
import com.honestpeak.model.Student;
import com.honestpeak.model.Teacher;
import com.honestpeak.model.User;
import com.honestpeak.model.UserRole;
import com.honestpeak.service.TeacherService;
import com.honestpeak.utils.Encrypt;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.StudentVo;
import com.honestpeak.vo.TeacherVo;
import com.honestpeak.vo.UserRoleVo;

@Service
public class TeacherServiceImpl implements TeacherService{
	@Resource
	private TeacherMapper teacherMapper;
	@Resource
	private UserRoleMapper userRoleMapper;
	@Override
	public Teacher loginUserByUserAccount(String username) {
		return teacherMapper.getUserByUserAccount(username);
	}
	@Override
	public Teacher findUserById(Long id) {
		return teacherMapper.selectByPrimaryKey(id);
	}
	@Override
	public UserRoleVo getUserRoleVoById(Long userId) {
		
		return teacherMapper.getUserRoleVoById(userId);
	}
	
	@Override
	public List<Teacher> selectAllTeacher() {
		return teacherMapper.selectAllTeacher();
	}
	@Override
	public String getIdentityByUserId(Long id) {
		return teacherMapper.getIdentityByUserId(id);
	}
	@Override
	public int updatePassWord(Long id, String defaultPassword) {
		return teacherMapper.updatePassWord(id, defaultPassword);		
	}
	@Override
	public User loginUserByUserName(String username) {
		return teacherMapper.loginUserByUserName(username);
	}
	@Override
	public String getUserIdById(Long userId) {
		return teacherMapper.getUserIdById(userId);
	}
	@Override
	public Page<TeacherVo> findTeacherPage(TeacherVo user, int currentPage) {
		Page<TeacherVo> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<TeacherVo> teacherVos = teacherMapper.findTeacherPage(user, page);
		page.setResultList(teacherVos);
		return page;
	}
	@Override
	public int deleteTeacher(Long id) {
		return teacherMapper.deleteByPrimaryKey(id);
	}
	@Override
	public int addTeacher(TeacherVo teacherVo) {
		teacherVo.setPassword(Encrypt.SHA256("111333"));
		int i =  teacherMapper.addTeacher(teacherVo);
		if(i>0){
			if(teacherVo.getId()!= null){
				UserRole userRole = new UserRole();
				userRole.setUserId(teacherVo.getId());
				userRole.setRoleId(7l);
				return userRoleMapper.insertSelective(userRole);
			}
			
		}
		return i;
	}
	@Override
	public int updateTeacherPassWord(Long id) {
		Teacher t = teacherMapper.selectByPrimaryKey(id);
		if(t != null) {
			String passWord = Constants.DEFAULT_PASSWORD;
			t.setPassword(Encrypt.SHA256(passWord));
			return teacherMapper.updateByPrimaryKeySelective(t);
		}
		return 0;
	}
	@Override
	public TeacherVo selectTeacherVo(Long id) {
		return teacherMapper.selectTeacherVo(id);
	}
	@Override
	public int updateTeacher(TeacherVo teacherVo) {
		return teacherMapper.updateByPrimaryKeySelective(teacherVo);
	}
	@Override
	public int deleteMore(String[] keys) {
		if(keys!=null){
			return teacherMapper.deleteMore(keys);
		}
		return 0;
	}
	@Override
	public Teacher selectTeacher(String userId) {
		return teacherMapper.selectTeacher(userId);
	}
	@Override
	public int update(Teacher teacher) {
		return teacherMapper.updateByPrimaryKeySelective(teacher);
	}
}
