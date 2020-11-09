package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.Major;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.MajorVo;

public interface MajorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Major record);

    int insertSelective(Major record);

    Major selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Major record);

    int updateByPrimaryKey(@Param("major")MajorVo major);

	List<MajorVo> findMajorPage(@Param("major")MajorVo major, @Param("departmentName")String departmentName, @Param("page")Page<MajorVo> page);

	MajorVo getById(Long id);

	List<Major> getAll();

	List<Major> findMajorByDepartId(Long departId);

	Major selectByPrimaryDepartIdAndMajorName(@Param("departId")Long departId, @Param("majorName")String majorName);

	void add(@Param("major")MajorVo major);

	int deleteByIds(String[] ids);

	MajorVo getByName(String majorName);

	Major getByName1(String majorName);
}