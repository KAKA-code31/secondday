package com.honestpeak.mapper;

import com.honestpeak.model.JudgeType;

public interface JudgeTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(JudgeType record);

    int insertSelective(JudgeType record);

    JudgeType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JudgeType record);

    int updateByPrimaryKey(JudgeType record);
}