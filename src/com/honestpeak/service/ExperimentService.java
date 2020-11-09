package com.honestpeak.service;

import java.util.List;

import com.honestpeak.model.Experiment;

public interface ExperimentService {
	public List<Experiment> findSubject(Long courseId);
}
