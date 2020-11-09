package com.honestpeak.service;

import java.util.List;

import com.honestpeak.model.StudentClass;
import com.honestpeak.utils.Page;
import com.honestpeak.vo.MajorVo;
import com.honestpeak.vo.StudentClassVo;

public interface StudentClassService {

	Page<StudentClassVo> getAllForPage(int i,Long schoolId, Long departmentId, Long majorId,Long classId, String grade,StudentClassVo studentClassVo);

	StudentClassVo getById(Long valueOf);
	StudentClass selectClassByCode(String code);
	StudentClass selectClassByName(String name);

	void update(StudentClassVo studentClass);

	void delete(Long id);

	void deleteAll(String[] ids);

	List<StudentClass> getAll();

	void add(StudentClassVo studentClass);

	List<StudentClass> findClassByMajorId(Long majorId);

	List<StudentClass> selectAllClass();

	/**
	 * 根据班级名称和专业查询班级是否存在
	 * @param className
	 * @param MajorId
	 * @return
	 */
	StudentClass findByClassNameAndMajorId(String className, Long majorId);

	void add(StudentClass studentClass);

	List<StudentClass> findClassByDepartmentId(String departmentId);
}
