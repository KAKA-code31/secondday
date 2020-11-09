package com.honestpeak.service;

import java.util.List;

import com.honestpeak.model.School;
import com.honestpeak.utils.Page;

public interface SchoolService {
	Page<School> getAllForPage(int i, School school);
	
	
	void add(School School);

	void delete(Long id);

	School getById(Long id);

	int update(School School);

	int deleteAll(String[] split);

	List<School> getAll();

	School getByName(String school);

	int insertSelective(School newSchool);
 
}
