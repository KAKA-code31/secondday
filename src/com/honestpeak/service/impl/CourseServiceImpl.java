package com.honestpeak.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.CourseMapper;
import com.honestpeak.mapper.ExperimentMapper;
import com.honestpeak.mapper.ExperimentTeacherMapper;
import com.honestpeak.mapper.JudgeMapper;
import com.honestpeak.model.Course;
import com.honestpeak.model.Experiment;
import com.honestpeak.service.CourseService;
import com.honestpeak.utils.Page;

@Service
public class CourseServiceImpl implements CourseService {

	@Resource
	private CourseMapper courseMapper;
	@Resource
	private ExperimentMapper experimentMapper;
	@Resource
	private ExperimentTeacherMapper experimentTeacherMapper;
	@Resource
	private JudgeMapper judgeMapper;

	@Override
	public Page<Course> selectCoursePage(Course course, int currentPage) {
		Page<Course> page = new Page<>(currentPage,PageConstants.FRONT_LIST_SIZE);
		/*List<Course> list = courseMapper.selectCoursePage(course, page);
		page.setResultList(list);*/
		return page;
	}

	@Override
	@Transactional
	public int addCourse(Course course) {
		if(course!=null){
			return courseMapper.insertSelective(course);
		}
		return 0;
	}
	
	/**
	 * 课程被按排的不能被删除
	 * @param ids
	 * @return
	 */
	@Override
	@Transactional
	public int delectMore(String[] ids) {
		
		return courseMapper.deleteMore(ids);
	}
	
	@Override
	@Transactional
	public int updateCourse(Course course) {
		if(course!=null){
			return courseMapper.updateByPrimaryKeyWithBLOBs(course);
		}
		return	0;
	}

	@Override
	public Course selectCourse(Long id) {
		return courseMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<Course> selectAllCourse(Long userId) {
		return courseMapper.selectAllCourse(userId);
	}

	@Override
	public void delete(Long id) {
		try {
		courseMapper.deleteByPrimaryKey(id);
		
			
				List<Experiment> eIds = experimentMapper.selectByCourseId(id);
				for(int j = 0;j<eIds.size();j++){
					experimentTeacherMapper.deleteByExperimentId(eIds.get(j).getId());
				}
				for(int j = 0;j<eIds.size();j++){
					experimentMapper.deleteByPrimaryKey(eIds.get(j).getId());
				}
				judgeMapper.deleteByCourseId(id);
			
			} catch (Exception e) {
				e.printStackTrace();
		}
	}

	@Override
	public List<Course> getByCondition(String yearTerm,Long teacherId, Long departmentId, Long placeId,Long classId) {
		return courseMapper.getByCondition(yearTerm,teacherId,departmentId,placeId,classId);
	}

	@Override
	public Course selectCourseByCourseCode(String bh) {
		return courseMapper.selectCourseByCourseCode(bh);
	}

	@Override
	public int insertCourse(Course c ) {
		return courseMapper.insertCourse(c);
	}

	@Override
	public int getCountById(Long id) {
		return courseMapper.getCountById(id);
	}
	@Override
	public String selectNameByEid(Long courseId){
		return courseMapper.getNameByEid(courseId);
	}
}
