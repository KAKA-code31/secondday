package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Depart;
import com.honestpeak.model.School;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.DepartmentVo;

public interface DepartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Depart record);

    int insertSelective(Depart record);

    Depart getById(Long id);
    
    Depart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Depart record);

    int updateByPrimaryKey(Depart record);

    List<DepartmentVo> findDepartmentPage(@Param("department")DepartmentVo department,@Param("schoolName")String schoolName, @Param("page")Page<DepartmentVo> page);
	int deleteByIds(String[] ids);

	List<Depart> getAll();

	Depart selectByName(String departName);
	
	List<Depart> findDepartmentBySchoolId(Long SchoolId);

	List<Depart> selectAllDepart();

	DepartmentVo selectByPrimaryKey1(Long id);
}