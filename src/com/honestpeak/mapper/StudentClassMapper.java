package com.honestpeak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.honestpeak.model.StudentClass;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.StudentClassVo;

public interface StudentClassMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StudentClass record);

    int insertSelective(StudentClass record);

    StudentClass selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StudentClass record);

    int updateByPrimaryKey(@Param("record")StudentClassVo record);

	List<StudentClassVo> getAllForPage(@Param("page")Page<StudentClassVo> page,@Param("schoolId") Long schoolId,@Param("departmentId") Long departmentId,@Param("majorId") Long majorId,@Param("classId") Long classId,
			@Param("grade")String grade,@Param("studentClassVo")StudentClassVo studentClassVo);

	StudentClassVo selectVoByPrimaryKey(Long id);

	void deleteByIds(String[] ids);

	List<StudentClass> getAll();

	List<StudentClass> findClassByMajorId(Long majorId);

	StudentClass findByClassNameAndMajorId(String className, Long majorId);

	List<StudentClass> findClassByDepartmentId(String departmentId);
	
	StudentClass selectClassByCode(String code);
	StudentClass selectClassByName(String name);

	StudentClass getByName(String className);
}