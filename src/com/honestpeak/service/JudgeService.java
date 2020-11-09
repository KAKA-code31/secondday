package com.honestpeak.service;

import java.util.List;

import com.honestpeak.model.Judge;

public interface JudgeService {

	List<Long> findJudgeIdListBySelectcourseId(Long id);

	int updateJudgeSelectcourse(Long id, String judges);

	String deleteJudge(String id);

	String addOrUpdateJudge(Judge judge);

	List<Object> findAllJudge();

	Object selectByPIdAndName(String pId, String name);

	Long selectMaxId();

	Long insertJudgeof(Judge judge);

	void updateJudges(Long id, Long courseId);

	int getCountByCourseId(Long cid);

	List<Judge> getByCourseId(Long cid);

	List<Judge> selectByPId(Long id);

    Judge getParentById(Long id);
	
	List<Judge> selectJudgeIdBySelId(Long selectcourseId);

	List<Judge> selectByPrimaryKey(Long id);

	Double getScoreById(Long parseLong);

}
