package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Experiment;

public interface ExperimentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Experiment record);

    int insertSelective(Experiment record);

    Experiment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Experiment record);

    int updateByPrimaryKey(Experiment record);

	List<Experiment> selctByCourseId(Long courseId);

	void insertExperiment(@Param("courseId")Long courseId, @Param("experimentCode")String experimentCode,
			@Param("experimentName")String experimentName, @Param("description")String description);

	Long selectByExperimentCodeAndCourseId(@Param("experimentCode")String experimentCode, @Param("courseId")Long courseId);

	List<Experiment> selectByCourseId(Long id);
	
	List<Experiment> selectSubject(Long courseId);
	
	String getNameByEid(Long experimentId);
}