package com.honestpeak.vo;

import com.honestpeak.model.SelectCourse;

public class ExperimentVo extends SelectCourse{

	private static final long serialVersionUID = 1L;
	private Long courseId;
	private String courseCode;
	private String courseName;
	private String experimentCode;
	private String experimentName;
	private String departName;
	private String majorName;
	private String className;
	private String studentCode;
	private String studentName;
	private String reportPath;
	private String picturePath;
	private String sourcecodePath;
	private Double score;
	private Integer checkflag;
	private String teacherId;
	private String teacherName;
	private Integer year;
	private Integer term;
	private String scId;//student和course的复合concat
	private String description;//实验简介
	private String cdescription;//课程简介
	private String score1;//分数（多门实验的成绩拼接）
	private int count;
	private Long etId;//expeiment_teacher_id
	private Double repetition;
	private Long experimentId;
	private Long departId;
	private Long majorId;
	private Long classId;
	private String JudgeContent;
	
	public String getJudgeContent() {
		return JudgeContent;
	}
	public void setJudgeContent(String judgeContent) {
		JudgeContent = judgeContent;
	}
	public Long getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(Long experimentId) {
		this.experimentId = experimentId;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public Long getMajorId() {
		return majorId;
	}
	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public Double getRepetition() {
		return repetition;
	}
	public void setRepetition(Double repetition) {
		this.repetition = repetition;
	}
	public String getScore1() {
		return score1;
	}
	public void setScore1(String score1) {
		this.score1 = score1;
	}
	public Long getEtId() {
		return etId;
	}
	public void setEtId(Long etId) {
		this.etId = etId;
	}
	public String getCdescription() {
		return cdescription;
	}
	public void setCdescription(String cdescription) {
		this.cdescription = cdescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getScId() {
		return scId;
	}
	public void setScId(String scId) {
		this.scId = scId;
	}

	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getSourcecodePath() {
		return sourcecodePath;
	}
	public void setSourcecodePath(String sourcecodePath) {
		this.sourcecodePath = sourcecodePath;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getExperimentCode() {
		return experimentCode;
	}
	public void setExperimentCode(String experimentCode) {
		this.experimentCode = experimentCode;
	}
	public String getExperimentName() {
		return experimentName;
	}
	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getCheckflag() {
		return checkflag;
	}
	public void setCheckflag(Integer checkflag) {
		this.checkflag = checkflag;
	}
	
}
