package com.honestpeak.service;

import java.util.List;

import com.honestpeak.model.Course;
import com.honestpeak.utils.Page;

public interface CourseService {


	/**
	 * 加载列表
	 * @param course
	 * @param current 当前页
	 * @return
	 */
	public Page<Course> selectCoursePage(Course course,int currentPage);
	
	/**
	 * 增加课程信息
	 * @param course
	 * @return
	 */
	public int addCourse(Course course);
	
	/**
	 * 批量删除课程信息
	 * @param id
	 * @return 
	 */
	public int delectMore(String[] ids);
	
	/**
	 * 单个删除课程信息
	 * @param id
	 * @return 
	 */
	void delete(Long id);
	
	/**
	 * 更新课程信息
	 * @param course
	 * @return
	 */
	public int updateCourse(Course course);
	
	/**
	 * 加载课程信息详情
	 * @param id
	 * @return
	 */
	public Course selectCourse(Long id);
	
	/**
	 * @param long1 
	 * @Title: selectAllCourse
	 * @Description:查找所有课程Id和名称
	 * @return
	 */
	public List<Course> selectAllCourse(Long userId);
/**
 * 本学期满足条件的课程
 * @param yearTerm
 * @param teacherId
 * @param departmentId
 * @param placeId
 * @param classId 
 * @return
 */
	public List<Course> getByCondition(String yearTerm, Long teacherId, Long departmentId, Long placeId, Long  classId);

	public Course selectCourseByCourseCode(String bh);

	public int insertCourse(Course c);

	public int getCountById(Long id);

	public String selectNameByEid(Long courseId);
	
}

