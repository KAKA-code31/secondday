package com.honestpeak.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.honestpeak.annotation.LogCustom;
import com.honestpeak.model.Teacher;
import com.honestpeak.service.UserRoleService;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;

/**
 * 
 * @author bpc
 *
 */
@Controller
@RequestMapping("/back/userRole")
@LogCustom(desc="用户权限管理")
public class UserRoleController extends BaseController {
	@Autowired
	private UserRoleService userRoleService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * @Title: manager
	 * @Description: 管理员权限分配管理
	 * @return
	 */
	@RequestMapping(value = "/manager")
	@LogCustom(desc="获取了管理员权限列表")
	public ModelAndView manager(Teacher user, Integer currentPage) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行管理员权限分配管理");
		ModelAndView mav = new ModelAndView();
		try {
			Page<Teacher> page = userRoleService.findUserRolePage(user, currentPage == null ? 1 : currentPage);
			// 列表项
			mav.addObject("userVoList", page.getResultList());
			// 分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());

			// 搜索条件
			mav.addObject("user", user);

			mav.setViewName("back/userRole/manager");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("管理员权限分配管理，manager出错{}", e);
		}
		return mav;
	}

	/**
	 * @Title: addUI
	 * @Description: 去添加页面
	 * @return
	 */
	@RequestMapping(value = "/addUI")
	@LogCustom(desc="进入了管理员权限分配添加页面")
	public ModelAndView addUI() {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行管理员权限分配添加页面！");
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("back/userRole/addUI");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("管理员权限分配管理，AddUI出错{}", e);
		}
		return mav;
	}
	/**
	 * 
	 * @Title: add
	 * @Description: 权限添加
	 * @return Object 返回类型
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	@LogCustom(desc="进行了管理员权限分配添加保存")
	public Object add(String userId, String roleIds, Integer roleType) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行管理员权限分配添加保存");
		try {
			String userCode = QEncodeUtil.decryptId(userId);//ID解密
			String[] roleCods = QEncodeUtil.decryptKeys(roleIds);//角色ID解密
			//roleType,看看需不需要，后面好显示管理员、俱乐部负责人
			if(userCode!=null){
				int flag = userRoleService.save(Long.valueOf(userCode), roleCods, roleType);
				if (flag == 0) {
					return renderError("系统无法保存该管理员！");
				}
				return renderSuccess("保存成功！");
			} else {
				return renderError("表单信息解析失败，请稍后重试！");
			}
		} catch (Exception e) {
			logger.error("管理员-角色无法完成绑定！{}", e);
			return renderError("系统无法保存该管理员！");
		}
	}
	/**
	 * 
	 * @Title: grantUI
	 * @Description: 进入权限授权
	 * @return ModelAndView 返回类型
	 */
	@RequestMapping(value = "/userGrantUI/{id}", method = RequestMethod.GET)
	@LogCustom(desc="进入了管理员授权更改页面")
	public ModelAndView userGrantUI(@PathVariable String id) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进入管理员授权更改页面！");
		ModelAndView mav = new ModelAndView();
		try {
			String userCode = QEncodeUtil.decryptId(id);//ID解密
			if (userCode != null) {
				mav.addObject("user", teacherService.getUserRoleVoById(Long.valueOf(userCode)));
				mav.setViewName("back/userRole/grantUI");
			} else {
				mav = new ModelAndView("/error/400");
				logger.error("管理员角色授权，用户主键解密失败！");
			}
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("管理员角色授权，grantUI出错{}", e);
		}
		return mav;
	}
	/**
	 * 
	 * @Title: grant
	 * @Description: 执行权限授权
	 * @return Object 返回类型
	 */
	@RequestMapping(value = "/grant")
	@ResponseBody
	@LogCustom(desc="进行了管理员授权保存")
	public Object grant(String userId, String roleIds, String clubIds) {
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行管理员权限授权保存");
		try {
			String userCode = QEncodeUtil.decryptId(userId);//ID解密
			String[] roleCods = QEncodeUtil.decryptKeys(roleIds);//角色ID解密
			String[] clubCods = QEncodeUtil.decryptKeys(clubIds);//角色ID解密
			if(userCode!=null){//用户ID解密成功才进行下一步操作
				int flag = userRoleService.update(Long.valueOf(userCode), roleCods, clubCods);
				if (flag == 0) {
					return renderError("系统无法为您保存该授权方案，请稍后重试！");
				} else {
					return renderSuccess("授权成功！");
				}
			} else {
				return renderError("系统解析表单数据失败，请稍后重试！");
			}
			
		} catch (Exception e) {
			logger.error("用户-角色 授权失败，grant方法出错！", e);
			return renderError("系统无法为您保存该授权方案，系统发生内部错误！");
		}
	}
	/**
	 * 
	 * @Title: deleteIDs
	 * @Description: 批量删除管理员
	 * @return Object 返回类型
	 */
	@RequestMapping(value = "/deleteIDs")
	@ResponseBody
	@LogCustom(desc="删除了多个管理员")
	public Object deleteIDs(String ids){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行管理员批量删除！");
		try {
			int flag = userRoleService.deleteAll(ids.split(","));
			if(flag==0){
				return renderError("系统无法删除选中的管理员！");
			} else if (flag == -1){
				return renderError("删除失败，存在管理员被班级引用，不能删除！");
			}
			return renderSuccess("批量删除成功！");
		} catch (Exception e) {
			logger.error("用户-角色 授权失败，deleteAll方法出错！", e);
			return renderError("系统无法删除选中的管理员，程序内部错误！");
		}
	}
	/**
	 * 
	 * @Title: delete
	 * @Description: 删除单个管理员
	 * @return Object 返回类型
	 */
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	@LogCustom(desc="删除了单个管理员")
	public Object delete(@PathVariable String id){
		logger.info(this.getUserName() + "于" + DateTimeUtils.DateToDateTimeString(new Date()) + "，进行管理员删除！");
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){//解密成功继续操作
				int flag = userRoleService.delete(Long.valueOf(decrypt));
				if(flag==0){
					return renderError("删除失败，系统无法删除该管理员！");
				}
				else if (flag == -1){
					return renderError("删除失败，该管理员担任辅导员，不能删除！");
				}
				return renderSuccess("删除成功！");
			} else {
				logger.info("删除单个  用户-角色 失败 delete方法,ID解密失败！");
				return renderError("表单数据解析异常，请稍后重试！");
			}
		} catch (Exception e) {
			logger.error("删除单个  用户-角色 失败 delete方法出错！",e);
			return renderError("删除失败，系统无法删除该管理员！");
		}
	}

}
