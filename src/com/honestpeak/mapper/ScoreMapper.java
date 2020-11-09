package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Score;
import com.honestpeak.vo.ExperimentVo;

public interface ScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Score record);

    int insertSelective(Score record);

    Score selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Score record);

    int updateByPrimaryKey(Score record);

	void updateScoreRepetition(@Param("temp")Double temp, @Param("id")Long id);
	
	void updateByselectcourseId(Long selectcourseId);
	
	Score selectBySelectcourseId(Long selectCourseId);
	int updatePath(Score record);
	
	int updateScore(Long selectCourseId);
}