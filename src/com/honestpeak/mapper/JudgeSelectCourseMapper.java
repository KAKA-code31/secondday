package com.honestpeak.mapper;

import java.util.List;

import com.honestpeak.model.JudgeSelectCourse;

public interface JudgeSelectCourseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(JudgeSelectCourse record);

    int insertSelective(JudgeSelectCourse record);

    JudgeSelectCourse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JudgeSelectCourse record);

    int updateByPrimaryKey(JudgeSelectCourse record);

	List<Long> findJudgeIdListBySelectcourseId(Long id);

	List<Long> findJudgeSelectcourse(Long id);

	void deleteAll(Long[] array);
}