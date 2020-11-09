package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Course;

public interface CourseMapper {
    void deleteByPrimaryKey(Long id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

	int deleteMore(String[] ids);

	int updateByPrimaryKeyWithBLOBs(Course course);

	List<Course> getByCondition(String yearTerm, Long teacherId, Long departmentId, Long placeId, Long classId);

	List<Course> selectAllCourse(Long userId);

	Course selectCourseByCourseCode(String bh);

	int insertCourse(Course c);

	Long selectIdByCourseCode(String courseCode);

	Long selectIdBySelectCourseId(Long id);

	int getCountById(Long id);
	
	String getNameByEid(Long courseId);

}