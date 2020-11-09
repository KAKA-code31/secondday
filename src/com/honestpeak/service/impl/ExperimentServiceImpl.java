package com.honestpeak.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honestpeak.mapper.ExperimentMapper;
import com.honestpeak.model.Experiment;
import com.honestpeak.service.ExperimentService;

@Service
public class ExperimentServiceImpl implements ExperimentService{
	@Resource
	private ExperimentMapper experimentMapper;
	public List<Experiment> findSubject(Long courseId){
		return experimentMapper.selectSubject(courseId);
	}
}
