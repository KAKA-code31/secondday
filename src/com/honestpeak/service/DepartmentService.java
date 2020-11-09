package com.honestpeak.service;

import java.util.List;

import com.honestpeak.model.Depart;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.DepartmentVo;

/**
 * @Title: DepartService.java 
 * @Package com.honestpeak.service 
 * @Description: depaetment service 
 * @author BPC
 * @date 2017年7月20日 下午2:19:57 
 * @version V1.0
 */
public interface DepartmentService {

	Page<DepartmentVo> getAllForPage(int i, String schoolName,DepartmentVo department );

	void add(Depart Depart);

	void delete(Long id);

	DepartmentVo getById(Long id);

	int update(Depart Depart);

	int deleteAll(String[] split);

	List<Depart> getAll();

	Depart getByName(String departName);

	int insertSelective(Depart newDep);
	
	List<Depart> findDepartmentBySchoolId(Long SchoolId);

	List<Depart> selectAllDepart();

}
