package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Judge;

public interface JudgeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Judge record);

    int insertSelective(Judge record);

    Judge selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Judge record);

    int updateByPrimaryKey(Judge record);

	List<Judge> findParentJudge();

	List<Judge> findAllJudgeByPid(long judgeId);

	List<Judge> findAllJudgeByTypeAndPid(@Param("privilegeType")int privilegeType, @Param("privilegeId")long privilegeId, @Param("courseId")Long courseId);
	int findJudgeById(Long id);

	List<Judge> findAllJudge();

	Object selectByPIdAndName(@Param("pId")Long pId, @Param("name")String name);

	Long selectMaxId();

	Long insertof(Judge judge);

	void updateJudges(@Param("id")Long id, @Param("courseId")Long flag);

	int getCountByCourseId(Long cid);

	List<Judge> getByCourseId(Long cid);

	int getCountByParentId(Long id);

	List<Judge> selectByPId(Long id);
	
	Judge getParentById(Long id);

	List<Judge> selectJudgeIdBySelId(Long selectcourseId);

	List<Judge> selectByPrimaryKey1(Long id);

	void deleteByCourseId(Long id);

	Double getScoreById(Long id);
}