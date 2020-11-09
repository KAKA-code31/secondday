package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Privilege;
import com.honestpeak.model.School;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.Pagination;

public interface SchoolMapper {
    int deleteByPrimaryKey(Long id);

    int insert(School record);

    int insertSelective(School record);

    School selectByPrimaryKey(Long id);
    
    School getById(Long id);
    
	List<School> findSchoolPage(@Param("school")School school,@Param("page")Pagination page);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);
    
	List<School> getAll();

	School selectByName(String schoolName);

	int deleteByIds(String[] ids);

	

}