package com.honestpeak.vo;

import com.honestpeak.model.Judge;
import com.honestpeak.model.JudgeType;

public class JudgeVo extends Judge{
	private static final long serialVersionUID = 1L;
	private JudgeType judgeType;
	public JudgeType getJudgeType() {
		return judgeType;
	}
	public void setJudgeType(JudgeType judgeType) {
		this.judgeType = judgeType;
	}
}
