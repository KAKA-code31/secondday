package com.honestpeak.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.honestpeak.annotation.LogCustom;
import com.honestpeak.model.Course;
import com.honestpeak.service.CourseService;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;

/**
 * 
 * @author bpc
 *
 */
@Controller
@RequestMapping("/back/course")
@LogCustom(desc = "课程管理")
public class CourseController extends BaseController {
	@Autowired
    protected CourseService courseService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/manager")
	@LogCustom(desc = "获取了课程列表")
	public ModelAndView manager(Course course, Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行课程管理");
		ModelAndView mav = new ModelAndView();
		try {
			Page<Course> page = courseService.selectCoursePage(course, currentPage == null ? 1 : currentPage);
			// 列表项
			mav.addObject("courseList", page.getResultList());
			// 分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());

			// 搜索条件
			mav.addObject("course", course);

			mav.setViewName("back/course/manager");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("教师信息管理，manager出错{}", e);
		}
		return mav;
	}

	/**
	 * @Title: addUI
	 * @Description: 去添加页面
	 * @return
	 */
	@RequestMapping(value = "/addUI")
	@LogCustom(desc = "进入了管理员权限分配添加页面")
	public ModelAndView addUI() {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行课程添加页面！");
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("back/course/addUI");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("课程管理，AddUI出错{}", e);
		}
		return mav;
	}


	/**
	 * 
	 * @Title: add
	 * @Description: 课程添加
	 * @return Object 返回类型
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	@LogCustom(desc = "进行了课程分配添加保存")
	public Object add(Course course) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行课程添加保存");
		try {
			int flag = courseService.addCourse(course);
			if (flag == 0) {
				return renderError("系统无法保存该课程！");
			}
			return renderSuccess("保存成功！");
		} catch (Exception e) {
			logger.error("新增失败！{}", e);
			return renderError("系统无法新增该课程！");
		}
	}

	/**
	 * 
	 * @Title: deleteIDs
	 * @Description: 批量删除课程
	 * @return Object 返回类型
	 */
	@RequestMapping(value = "/deleteIDs")
	@ResponseBody
	@LogCustom(desc = "删除了多个课程")
	public Object deleteIDs(String ids) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行管理员批量删除！");
		try {
			if(ids == null) {
				logger.error("课程删除出错,参数为空！");
				return renderError("删除失败，参数为空！");
			}
			String[] keys = QEncodeUtil.decryptKeys(ids);
			courseService.delectMore(keys);
		} catch (DataIntegrityViolationException e) {
			logger.error("课程删除，deleteIDs 删除处理 出错，无法删除关联对象！", e);
			return renderError("删除失败，该课程关联选课信息不给予删除！");
		} catch (Exception e) {
			logger.error("课程删除，delete 删除处理 出错{}", e);
			return renderError("删除失败，该课程关联选课信息不给予删除！");
		}
		return renderSuccess("删除成功！");
	}

	/**
	 * @Title: editUI
	 * @Description: 去课程修改界面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/editUI/{id}", method = RequestMethod.GET)
	@LogCustom(desc = "进入了课程修改页面")
	public ModelAndView editUI(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，去行角色修改页面");
		ModelAndView mav = new ModelAndView();
		try {
			String decrypt = QEncodeUtil.decryptId(id);// ID解密
			if (decrypt == null) {
				logger.error("课程修改失败，系统无法解析表单信息！");
				mav.setViewName("error/400");
			} else {
				Course course = courseService.selectCourse(Long.parseLong(decrypt));
				mav.addObject("course", course);
				if (course != null) {
					mav.setViewName("/back/course/editUI");
				} else {
					logger.error("课程修改失败，找不到对应的课程信息！");
					mav.setViewName("error/500");
				}
			}
		} catch (Exception e) {
			logger.error("课程修改，editUI出错{}", e);
			mav.setViewName("error/500");
		}
		return mav;
	}

	/**
	 * @Title: edit
	 * @Description: 课程编辑处理
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc = "进行了课程修改")
	public Object edit(Course course) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行课程修改");
		int res = 0;
		try {
			String encrypt = course.decrypt();
			if (encrypt != null) {
				course.setId(Long.valueOf(encrypt));
				res = courseService.updateCourse(course);
			} 
		} catch (Exception e) {
			logger.error("课程修改，edit出错{}", e);
			return renderError("修改失败，系统内部出错！");
		}
		if (res == 0) {
			return renderError("修改失败，无法对该课程进行修改！");
		}
		return renderSuccess("修改成功！");
	}
	
	/**
	 * @Title: delete
	 * @Description: 删除一条课程信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc = "删除了一条课程信息！")
	public Object delete(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行课程信息删除");
		try {
			String decrypt = QEncodeUtil.decryptId(id);// ID解密
			if (decrypt != null) {
				courseService.delete(Long.valueOf(decrypt));
			}
		} catch (Exception e) {
			logger.error("delete 删除处理 出错{}", e);
			return renderError("删除失败，系统内部出错！");
		}
		return renderSuccess("删除成功！");
	}
	
	/**
	 * @Title: detailsUI
	 * @Description: 去课程信息维护查看界面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detailsUI/{id}")
	@LogCustom(desc = "进入了课程信息维护界面")
	public ModelAndView detailsUI(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，去课程信息维护页面");
		ModelAndView mav = new ModelAndView();
		try {
			String decrypt = QEncodeUtil.decryptId(id);
			if(decrypt == null){
				logger.error("课程查看失败，系统无法解析表单信息！");
				mav.setViewName("error/400");
			}else{
				Course course = courseService.selectCourse(Long.valueOf(decrypt));
				mav.addObject("course", course);
				if(course != null){
					mav.setViewName("/back/course/detailsUI");
				}else{
					logger.error("课程查看失败，找不到对应的课程信息！");
					mav.setViewName("error/500");
				}
			}
		} catch (Exception e) {
			logger.error("课程维护详情，editUI出错{}", e);
			mav.setViewName("error/500");
		}
		return mav;
	}
}
