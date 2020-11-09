package com.honestpeak.vo;

import com.honestpeak.model.ExperimentTeacher;
import com.honestpeak.model.SelectCourse;
import com.honestpeak.model.StudentClass;

public class SelectCourseVo extends SelectCourse{

	private static final long serialVersionUID = 1L;
	private Integer courseId;
	private String coursecode;
	private String coursename;
	private String teacherId;
	private String teachername;
	private StudentVo studentVo;
	private StudentClass studentClass;
	private ExperimentTeacher experimentTeacher;
	private Integer year;
	private Integer term;
	
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
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
	public String getCoursecode() {
		return coursecode;
	}
	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeachername() {
		return teachername;
	}
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	public StudentVo getStudentVo() {
		return studentVo;
	}
	public void setStudentVo(StudentVo studentVo) {
		this.studentVo = studentVo;
	}
	public StudentClass getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(StudentClass studentClass) {
		this.studentClass = studentClass;
	}
	public ExperimentTeacher getExperimentTeacher() {
		return experimentTeacher;
	}
	public void setExperimentTeacher(ExperimentTeacher experimentTeacher) {
		this.experimentTeacher = experimentTeacher;
	}

}
