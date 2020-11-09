package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.SelectCourse;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.ExperimentVo;
import com.honestpeak.vo.SelectCourseVo;

public interface SelectCourseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SelectCourse record);

    int insertSelective(SelectCourse record);

    SelectCourse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SelectCourse record);

    int updateByPrimaryKey(SelectCourse record);
    
    int updateClassByStudentId(SelectCourse record);

	List<SelectCourseVo> findSelectCoursePage(@Param("selectCourseVo")SelectCourseVo selectCourseVo, @Param("page")Page<SelectCourseVo> page);

	int countByExprimentTeacherId(Long userId);

	List<ExperimentVo> selectExperiments(@Param("experimentVo")ExperimentVo experimentVo, @Param("id")Long id, @Param("page")Page<ExperimentVo> page);

	ExperimentVo selectExperimentBySid(Long id);

	void insertintoScore(ExperimentVo experimentVo);

	void updateStatus(Long id);

	List<ExperimentVo> selectStudents(@Param("experimentVo")ExperimentVo experimentVo, @Param("page")Page<ExperimentVo> page);

	void inserts(@Param("classId")Long classId, @Param("etId")Long etId, @Param("studentId")Long studentId);

	List<ExperimentVo> selectAllCoursePage(@Param("experimentVo")ExperimentVo experimentVo, Page<ExperimentVo> page);

	List<ExperimentVo> selectExperimentByCourseId(Long id);

	ExperimentVo selectScoreBySid(Long id);

	Long getSelIdByPath(String path);

	void insertScore(@Param("id")Long id, @Param("score")Double score);

	List<ExperimentVo> getListOfScoreByCourse(@Param("cid")Long cid,@Param("classId")Long classId);

	List<ExperimentVo> selectExperimentByCId(Long id);

	List<ExperimentVo> getListOfExperimentScoreByCourse(@Param("cid")Long cid, @Param("classId")Long classId, @Param("experimentId")Long experimentId);

	List<ExperimentVo> getListOfExperimentScoreByDesignCourse(@Param("cid")Long cid, @Param("classId")Long classId);
	
	void updateScore(ExperimentVo experimentVo);

	List<ExperimentVo> selectScorePage(@Param("experimentVo")ExperimentVo experimentVo, @Param("page")Page<ExperimentVo> page);

	List<ExperimentVo> selectHistoryPage(@Param("experimentVo")ExperimentVo experimentVo, @Param("page")Page<ExperimentVo> page);

	List<ExperimentVo> getCoursesByStudentId(Long userId);

	List<ExperimentVo> getExperiment(@Param("courseId")Long courseId, @Param("stuId")Long stuId);

	List<ExperimentVo> findSelectCoursePage1(@Param("experimentVo")ExperimentVo experimentVo, @Param("page")Page<ExperimentVo> page);

	List<ExperimentVo> getCoursesByTeacherId(Long userId);

	List<ExperimentVo> getExperimentByCourseId(Long courseId);

	Long getIdByStuIdAndEtId(@Param("etId")Long etId, @Param("userId")Long userId);

	List<ExperimentVo> getCoursesByTeacherIdAndCount(Long userId);

	void inserts1(@Param("classId")Long classId, @Param("studentId")Long studentId, @Param("etId")Long etId,@Param("fakeclassId")Long fakeclassId);
	
	void insertStudentDsign(List<SelectCourse> selectCourseList);
	
	void insertStudentDsign1(@Param("classId")Long classId, @Param("studentId")Long studentId,@Param("exprimentTeacherId")Long exprimentTeacherId, @Param("fakeclassId")Long fakeclassId);
	
	Long getClassIdByIdForDesign(@Param("userId")Long userId, @Param("experimentId")Long experimentId);
	
	Integer getFlag(@Param("userId")Long userId, @Param("experimentId")Long experimentId);
	
	List<SelectCourse> selectByStudentId(Long studentId);
}