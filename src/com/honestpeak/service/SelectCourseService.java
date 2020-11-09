package com.honestpeak.service;

import java.util.List;

import com.honestpeak.model.Experiment;
import com.honestpeak.result.Tree;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.ExperimentVo;
import com.honestpeak.vo.SelectCourseVo;

public interface SelectCourseService {

	Page<SelectCourseVo> selectCoursePage(SelectCourseVo selectCourseVo, int currentPage);

	int countByExprimentTeacherId(Long userId);

	Page<ExperimentVo> selectExperiments(ExperimentVo experimentVo, Long id,int currentPage);

	ExperimentVo selectExperimentBySid(Long id);

	void insertintoScore(ExperimentVo experimentVo);

	void updateStatus(Long id);

	Page<ExperimentVo> selectStudentPage(ExperimentVo experimentVo, int currentPage);

	void insert(Long courseId, Long departmentId, Long majorId, Long classId, Long userId);

	void insertdesign(Long courseId, Long departmentId, Long majorId, Long classId, Long userId,Long studentIds[]);
	
	Page<ExperimentVo> selectAllCoursePage(ExperimentVo experimentVo, int currentPage);

	List<ExperimentVo> selectExperimentByCourseId(Long id);

	void insertExperiment(String courseCode, String[] experimentCode, String[] experimentName, String[] description, Long teacherId);

	ExperimentVo selectScoreBySid(Long id);

	List<Long> getSelIdByPath(List<String> tempPath);

	List<Tree> findAllTrees(Long id);

	void insertScore(Long id, Double score);

	void updateScoreRepetition(Double temp, Long id);

	List<ExperimentVo> getListOfScoreByCourse(Long cid, Long classId);

	List<ExperimentVo> selectExperimentsByCourseId(Long id);

	List<ExperimentVo> getListOfExperimentScoreByCourse(Long cid, Long classId, Long experimentId);
	
	List<ExperimentVo> getListOfExperimentScoreByDesignCourse(Long cid, Long classId);
	
	void updateScore(ExperimentVo experimentVo);

	Page<ExperimentVo> selectScorePage(ExperimentVo experimentVo, int currentPage);

	Page<ExperimentVo> selectHistoryPage(ExperimentVo experimentVo, int currentPage);

	List<ExperimentVo> getCoursesByStudentId(Long userId);

	List<ExperimentVo> getExperiment(Long courseId, Long stuId);

	Page<ExperimentVo> selectCoursePage1(ExperimentVo experimentVo, int currentPage);

	List<ExperimentVo> getCoursesByTeacherId(Long userId);

	List<ExperimentVo> getExperimentByCourseId(Long courseId);

	Long getIdByStuIdAndEtId(Long etId, Long userId);

	List<ExperimentVo> getCoursesByTeacherIdAndCount(Long userId);

	boolean insertStudent(Long courseId, String code, Long departmentId, Long majorId, Long classId, Long userId);
	
	void insertStudentDsign(Long courseId,Long experimentId,Long departmentId, Long majorId, Long classId, Long userId,String[] ids);
	
	boolean insertStudentDsign1(Long courseId, Long experimentId,Long departmentId, Long majorId, Long fakeclassId,Long userId,String code);

	public Long getClassIdByIdForDesign(Long userId,Long experimentId);
	
	Integer getFlag(Long userId,Long experimentId);
	
	public void updateByselectcourseId(Long selectCourseId);

}
