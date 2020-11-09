package com.honestpeak.service;

import java.util.List;

import com.honestpeak.model.Major;
import com.honestpeak.model.StudentClass;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.MajorVo;

public interface MajorService {

	Page<MajorVo> getAllForPage(int i, String departmentName, MajorVo major);

	int deleteAll(String[] split);

	MajorVo getById(Long valueOf);

	void update(MajorVo major);

	void delete(Long id);

	void add(Major newMajor);

	List<Major> getAll();

	List<Major> findMajorByDepartId(Long departId);

	/**
	 * 根据院系id和专业名称查询专业是否存在
	 * @param id
	 * @param majorName
	 * @return
	 */
	public Major findMajorByDepartIdAndMajorName(Long departId, String majorName);

	void addMajorVo(MajorVo major);

	MajorVo getByName(String majorName);

	Major getByName1(String majorName);

}
