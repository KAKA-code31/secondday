package com.honestpeak.mapper;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.ExperimentTeacher;

public interface ExperimentTeacherMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExperimentTeacher record);

    int insertSelective(ExperimentTeacher record);

    ExperimentTeacher selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExperimentTeacher record);

    int updateByPrimaryKey(ExperimentTeacher record);

	Long[] selectId(@Param("courseId")Long courseId, @Param("userId")Long userId);

	void insertET(@Param("experimentId")Long experimentId, @Param("teacherId")Long teacherId);

	void deleteByExperimentId(Long i);

	Long getEtIdByEid(Long experimentId);
	
	Long selectIdfordesign(@Param("experimentId")Long experimentId,@Param("userId")Long userId);
	
	Long getExperimentByEid(Long EtId);
}