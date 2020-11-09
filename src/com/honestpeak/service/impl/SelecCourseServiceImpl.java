package com.honestpeak.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.CourseMapper;
import com.honestpeak.mapper.ExperimentMapper;
import com.honestpeak.mapper.ExperimentTeacherMapper;
import com.honestpeak.mapper.JudgeMapper;
import com.honestpeak.mapper.ScoreMapper;
import com.honestpeak.mapper.SelectCourseMapper;
import com.honestpeak.mapper.StudentMapper;
import com.honestpeak.model.Experiment;
import com.honestpeak.model.ExperimentTeacher;
import com.honestpeak.model.Judge;
import com.honestpeak.model.SelectCourse;
import com.honestpeak.model.Student;
import com.honestpeak.result.Tree;
import com.honestpeak.service.SelectCourseService;
import com.honestpeak.utils.Config;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;
import com.honestpeak.vo.ExperimentVo;
import com.honestpeak.vo.SelectCourseVo;
@Service
public class SelecCourseServiceImpl implements SelectCourseService{
	@Autowired
	private SelectCourseMapper selectCourseMapper;
	@Autowired
	private ExperimentMapper experimentMapper;
	@Autowired
	private ExperimentTeacherMapper experimentTeacherMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private JudgeMapper judgeMapper;
	@Autowired
	private ScoreMapper scoreMapper;
	@Override
	public Page<SelectCourseVo> selectCoursePage(SelectCourseVo selectCourseVo, int currentPage) {
		Page<SelectCourseVo> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<SelectCourseVo> selectCourses = selectCourseMapper.findSelectCoursePage(selectCourseVo, page);
		page.setResultList(selectCourses);
		return page;
	}
	@Override
	public int countByExprimentTeacherId(Long userId) {
		return selectCourseMapper.countByExprimentTeacherId(userId);
	}
	@Override
	public Page<ExperimentVo> selectExperiments(ExperimentVo experimentVo, Long id, int currentPage) {
		Page<ExperimentVo> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<ExperimentVo> experiments = selectCourseMapper.selectExperiments(experimentVo, id,page);
		page.setResultList(experiments);
		return page;
	}
	@Override
	public ExperimentVo selectExperimentBySid(Long id) {
		return selectCourseMapper.selectExperimentBySid(id);
	}
	@Override
	public void insertintoScore(ExperimentVo experimentVo) {
		selectCourseMapper.insertintoScore(experimentVo);
	}
	@Override
	public void updateStatus(Long id) {
		selectCourseMapper.updateStatus(id);
	}
	@Override
	public Page<ExperimentVo> selectStudentPage(ExperimentVo experimentVo, int currentPage) {
		Page<ExperimentVo> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<ExperimentVo> experiments = selectCourseMapper.selectStudents(experimentVo,page);
		page.setResultList(experiments);
		return page;
	}
	@Override
	public void insert(Long courseId, Long departmentId, Long majorId, Long classId,Long userId) {
		Long[] experimentTeacherIds = experimentTeacherMapper.selectId(courseId,userId);
		Long[] studentIds = studentMapper.selectIds(classId);
		for(int i = 0; i<experimentTeacherIds.length;i++){
			for(int j= 0;j<studentIds.length;j++){
				selectCourseMapper.inserts(classId,experimentTeacherIds[i],studentIds[j]);
			}
		}
	}
	@Override
	public void insertdesign(Long courseId, Long departmentId, Long majorId, Long classId,Long userId,Long studentIds[]) {
		Long[] experimentTeacherIds = experimentTeacherMapper.selectId(courseId,userId);
		/*Long[] studentIds = studentMapper.selectIds(classId);*/
		for(int i = 0; i<experimentTeacherIds.length;i++){
			for(int j= 0;j<studentIds.length;j++){
				selectCourseMapper.inserts(classId,experimentTeacherIds[i],studentIds[j]);
			}
		}
	}
	@Override
	public Page<ExperimentVo> selectAllCoursePage(ExperimentVo experimentVo, int currentPage) {
		Page<ExperimentVo> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<ExperimentVo> experiments = selectCourseMapper.selectAllCoursePage(experimentVo,page);
		page.setResultList(experiments);
		return page;
	}
	@Override
	public List<ExperimentVo> selectExperimentByCourseId(Long id) {
		return selectCourseMapper.selectExperimentByCourseId(id);
	}
	@Transactional
	public void insertExperiment(String courseCode, String[] experimentCode, String[] experimentName,
			String[] description,Long teacherId) {
		Long courseId = courseMapper.selectIdByCourseCode(courseCode);
		if(courseId != null){
			for(int i = 0; i<experimentCode.length;i++){
				experimentMapper.insertExperiment(courseId,experimentCode[i],experimentName[i],description[i]);
			}
			for(int i = 0; i<experimentCode.length;i++){
				Long experimentId = experimentMapper.selectByExperimentCodeAndCourseId(experimentCode[i],courseId);
				experimentTeacherMapper.insertET(experimentId,teacherId);
			}
		}
	}
	@Override
	public ExperimentVo selectScoreBySid(Long id) {
		return selectCourseMapper.selectScoreBySid(id);
	}
	@Override
	public List<Long> getSelIdByPath(List<String> tempPath) {
		List<Long> sids = new ArrayList<>();
		for(int i = 0; i<tempPath.size(); i++){
			Long sid = selectCourseMapper.getSelIdByPath(tempPath.get(i));
			sids.add(sid);
		}
		return sids;
	}
	@Override
	public List<Tree> findAllTrees(Long id) {
		List<Tree> trees = null;
		List<Judge> pps = judgeMapper.findParentJudge();
		if(pps!=null){
			trees = new ArrayList<>();
			for (Judge p : pps) {
				Tree t = new Tree();
				t.setId(p.getId());
				if(p.getScore()!=null){
					t.setName(p.getName()+p.getScore());
				}else{
					t.setName(p.getName());
				}
				t.setpId(0l);
				List<Tree> childs = initResourceChild(Config.RESOURCE_MENU, p.getId(),id);
				if(childs!=null && childs.size()>0){
					t.setChildren(childs);
				}else{
					t.setOpen(false);
				}
				trees.add(t);
			}
		}
		return trees;
	}
	
	private List<Tree> initResourceChild(int privilegeType,long privilegeId,Long id){
		List<Tree> trees = null;
		Long courseId = courseMapper.selectIdBySelectCourseId(id);
		List<Judge> childs = judgeMapper.findAllJudgeByTypeAndPid(privilegeType,privilegeId,courseId);
		if(childs!=null && childs.size()>0){
			trees = new ArrayList<>();
			for(Judge child : childs){
				Tree tree = new Tree();
				tree.setId(child.getId());
				if(child.getScore()!=null){
					tree.setName(child.getName()+child.getScore());
				}else{
					tree.setName(child.getName());
				}
				tree.setpId(privilegeId);
				tree.setChildren(this.initResourceChild(Config.RESOURCE_BUTTON, child.getId(),id));
				trees.add(tree);
			}
		}
		return trees;
	}
	@Override
	public void insertScore(Long id, Double score) {
		selectCourseMapper.insertScore(id,score);
	}
	@Override
	public void updateScoreRepetition(Double temp, Long id) {
		scoreMapper.updateScoreRepetition(temp,id);
	}
	@Override
	public List<ExperimentVo> getListOfScoreByCourse(Long cid,Long classId) {
		return selectCourseMapper.getListOfScoreByCourse(cid,classId);
	}
	@Override
	public List<ExperimentVo> selectExperimentsByCourseId(Long id) {
		return selectCourseMapper.selectExperimentByCourseId(id);
	}
	@Override
	public List<ExperimentVo> getListOfExperimentScoreByCourse(Long cid, Long classId, Long experimentId) {
		return selectCourseMapper.getListOfExperimentScoreByCourse(cid,classId,experimentId);
	}
	@Override
	public List<ExperimentVo> getListOfExperimentScoreByDesignCourse(Long cid, Long classId) {
		return selectCourseMapper.getListOfExperimentScoreByDesignCourse(cid,classId);
	}
	@Override
	public void updateScore(ExperimentVo experimentVo) {
		selectCourseMapper.updateScore(experimentVo);
	}
	@Override
	public Page<ExperimentVo> selectScorePage(ExperimentVo experimentVo, int currentPage) {
		Page<ExperimentVo> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<ExperimentVo> selectCourses = selectCourseMapper.selectScorePage(experimentVo, page);
		if(selectCourses.size()>0){
			for(int k=0; k<selectCourses.size();k++){
				List<Judge> j = judgeMapper.selectJudgeIdBySelId(selectCourses.get(k).getId());
				List<String> judgess = new ArrayList<String>();
				for(int i=0; i<j.size();i++){
					Judge judge = judgeMapper.selectByPrimaryKey(j.get(i).getParentId());
					String judgeContent = judge.getName()+j.get(i).getName();
					judgess.add(i, judgeContent);
				}
				selectCourses.get(k).setJudgeContent(StringUtils.strip(judgess.toString(),"[]"));
			}
		}
		page.setResultList(selectCourses);
		return page;
	}
	@Override
	public Page<ExperimentVo> selectHistoryPage(ExperimentVo experimentVo, int currentPage) {
		Page<ExperimentVo> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<ExperimentVo> selectCourses = selectCourseMapper.selectHistoryPage(experimentVo, page);
		if(selectCourses.size()>0){
			for(int k=0; k<selectCourses.size();k++){
				List<Judge> j = judgeMapper.selectJudgeIdBySelId(selectCourses.get(k).getId());
				List<String> judgess = new ArrayList<String>();
				for(int i=0; i<j.size();i++){
					Judge judge = judgeMapper.selectByPrimaryKey(j.get(i).getParentId());
					String judgeContent = judge.getName()+j.get(i).getName();
					judgess.add(i, judgeContent);
				}
				selectCourses.get(k).setJudgeContent(StringUtils.strip(judgess.toString(),"[]"));
			}
		}
		page.setResultList(selectCourses);
		return page;
	}
	@Override
	public List<ExperimentVo> getCoursesByStudentId(Long userId) {
		return selectCourseMapper.getCoursesByStudentId(userId);
	}
	@Override
	public List<ExperimentVo> getExperiment(Long courseId,Long stuId) {
		return selectCourseMapper.getExperiment(courseId,stuId);
	}
	@Override
	public Page<ExperimentVo> selectCoursePage1(ExperimentVo experimentVo, int currentPage) {
		Page<ExperimentVo> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<ExperimentVo> selectCourses = selectCourseMapper.findSelectCoursePage1(experimentVo, page);
		page.setResultList(selectCourses);
		return page;
	}
	@Override
	public List<ExperimentVo> getCoursesByTeacherId(Long userId) {
		return selectCourseMapper.getCoursesByTeacherId(userId);
	}
	@Override
	public List<ExperimentVo> getExperimentByCourseId(Long courseId) {
		return selectCourseMapper.getExperimentByCourseId(courseId);
	}
	@Override
	public Long getIdByStuIdAndEtId(Long etId, Long userId) {
		return selectCourseMapper.getIdByStuIdAndEtId(etId,userId);
	}
	@Override
	public List<ExperimentVo> getCoursesByTeacherIdAndCount(Long userId) {
		return selectCourseMapper.getCoursesByTeacherIdAndCount(userId);
	}
	@Override
	public boolean insertStudent(Long courseId, String code, Long departmentId, Long majorId, Long fakeclassId, Long userId) {
		Long[] experimentTeacherIds = experimentTeacherMapper.selectId(courseId,userId);
		Student stu = studentMapper.selectStudent(code);
		//S/ystem.out.println(stu.getStdentId());
		if(stu==null){
			return false;
		}else{
			for(int i = 0; i<experimentTeacherIds.length;i++){
				selectCourseMapper.inserts1(stu.getClassId(),stu.getStdentId(),experimentTeacherIds[i],fakeclassId);
			}
			return true;
		}
	}
	@Override
	public void insertStudentDsign(Long courseId,Long experimentId,Long departmentId, Long majorId, Long classId, Long userId,String[] ids){
		Long exprimentTeacherId = experimentTeacherMapper.selectIdfordesign(experimentId,userId);
		List<SelectCourse> selectCourseList = new ArrayList<SelectCourse>();
		for(int i=0;i<ids.length;i++){
			SelectCourse selectCourse=new SelectCourse();
			selectCourse.setClassId(classId);
			selectCourse.setStudentId(Long.parseLong(ids[i]));
			selectCourse.setExprimentTeacherId(exprimentTeacherId);
			selectCourseList.add(selectCourse);
		}
		selectCourseMapper.insertStudentDsign(selectCourseList);
	}
	@Override
	public boolean insertStudentDsign1(Long courseId, Long experimentId,Long departmentId, Long majorId, Long fakeclassId,Long userId,String code){
		Long exprimentTeacherId = experimentTeacherMapper.selectIdfordesign(experimentId,userId);
		Student stu = studentMapper.selectStudent(code);
		//S/ystem.out.println(stu.getStdentId());
		if(stu==null){
			return false;
		}else{
			selectCourseMapper.insertStudentDsign1(stu.getClassId(),stu.getStdentId(),exprimentTeacherId,fakeclassId);
			return true;
		}		
	}
	@Override
	public Long getClassIdByIdForDesign(Long userId,Long experimentId) {
		return selectCourseMapper.getClassIdByIdForDesign(userId,experimentId);
	}
	
	public Integer getFlag(Long userId,Long experimentId){
		return selectCourseMapper.getFlag(userId,experimentId);
	}
	@Override
	public void updateByselectcourseId(Long selectCourseId) {
		scoreMapper.updateByselectcourseId(selectCourseId);
	}
}

