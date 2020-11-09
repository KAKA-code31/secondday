package com.honestpeak.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.honestpeak.mapper.ScoreMapper;
import com.honestpeak.service.ScoreService;


@Service
public class ScoreServiceImpl implements ScoreService{
	@Resource
	private ScoreMapper scoreMapper;	
  public int updateScore(Long selectCourseId){
	  return scoreMapper.updateScore(selectCourseId);
  }
}
