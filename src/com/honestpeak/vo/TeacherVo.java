package com.honestpeak.vo;

import com.honestpeak.model.Depart;
import com.honestpeak.model.School;
import com.honestpeak.model.Teacher;

public class TeacherVo extends Teacher{

	private static final long serialVersionUID = -97789944045467316L;
	private School school;
	private Depart depart;
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public Depart getDepart() {
		return depart;
	}
	public void setDepart(Depart depart) {
		this.depart = depart;
	}
	
}
