package com.honestpeak.controller;

import java.util.Date;
import java.util.List;

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
import com.honestpeak.model.Role;
import com.honestpeak.service.RoleService;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;

/**
 * @ClassName: RoleController
 * @Description: 角色管理业务逻辑
 * @author Jeabev
 * @date 2016年8月5日 下午4:29:53
 */
@Controller
@RequestMapping("back/role")
@LogCustom(desc="角色管理")
public class RoleController extends BaseController {
	@Autowired
	private RoleService roleService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 /**
	  * @Title: manager
	  * @Description:角色管理页
	  * @param role 查询条件
	  * @param currentPage 当前页
	  */
    @RequestMapping(value = "/manager")
    @LogCustom(desc="获取了角色列表")
    public ModelAndView manager(Role role , Integer currentPage) {
    	logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行角色管理");
    	ModelAndView mav = new ModelAndView();
    	try {
    		Page<Role> page = roleService.findRolePage(role,currentPage==null?1:currentPage);
			//列表项
			mav.addObject("roleList", page.getResultList());
			//分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());
			
    		mav.setViewName("/back/role/roleList");
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("角色管理，manager出错{}",e);
		}
        return mav;
    }
    
    /**
     * @Title: addUI
     * @Description: 角色添加页面
     * @return
     */
    @RequestMapping(value = "/addUI", method = RequestMethod.GET)
    @LogCustom(desc="进入了角色添加页面")
    public ModelAndView addUI(){
    	logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进入角色添加页面");
    	ModelAndView mav = new ModelAndView();
    	try {
        	mav.setViewName("/back/role/roleAddUI");
        	return mav;
		} catch (Exception e) {
			mav = new ModelAndView("/error/500");
			logger.error("角色添加页面，addUI出错{}",e);
			return mav;
		}
    }
    
    /**
     * @Title: add
     * @Description: 角色添加处理
     * @param role
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @LogCustom(desc="进行了角色添加")
    public Object add(Role role){
    	logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行角色添加处理！");
    	try {
    		int res = roleService.save(role);
    		if(res != 1){
    			return renderError("抱歉，系统无法保存该信息！");
    		}
    		return renderSuccess("角色信息保存成功！");
		} catch (Exception e) {
			logger.error("角色信息添加失败，add出错{}",e);
			return renderError("抱歉，系统出现内部错误，无法保存该信息！");
		}
    }
    
    /**
     * @Title: editUI
     * @Description: 去角色修改界面
     * @param id
     * @return
     */
	@RequestMapping(value="/editUI/{id}",method=RequestMethod.GET)
	@LogCustom(desc="进入了角色修改页面")
	public ModelAndView editUI(@PathVariable String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，去行角色修改页面");
		ModelAndView mav = new ModelAndView();
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt==null){
				logger.error("角色修改失败，系统无法解析表单信息！");
				mav.setViewName("error/400");
			} else {
				Role role = roleService.selectById(Long.parseLong(decrypt));
				mav.addObject("role",role);
				if(role!=null){
					mav.setViewName("/back/role/roleEditUI");
				} else {
					logger.error("角色修改失败，找不到对应的角色信息！");
					mav.setViewName("error/500");
				}
			}
		} catch (Exception e) {
			logger.error("角色修改，editUI出错{}", e);
			mav.setViewName("error/500");
		}
		return mav;
	}
	
	/**
	 * @Title: edit
	 * @Description: 角色编辑处理
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc="进行了角色修改")
	public Object edit(Role role){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行权限修改");
		int res = 0;
		try {
			String encrypt = role.decrypt();
			if(encrypt!=null){
				role.setId(Long.valueOf(encrypt));
				res = roleService.update(role);
			}else{
				logger.error("角色修改，edit失败，Id解析失败！");
				return renderError("修改失败，表单信息解析不成功！");
			}
		} catch (Exception e) {
			logger.error("角色修改，edit出错{}",e);
			return renderError("修改失败，系统内部出错！");
		}
		if(res==0){
			return renderError("修改失败，无法对该角色进行修改！");
		}
		return renderSuccess("修改成功！");
	}
    
    /**
     * @Title: grantUI
     * @Description: 去角色授权页
     * @param id 角色主键
     */
    @RequestMapping(value="/grantUI/{id}",method=RequestMethod.GET)
    @LogCustom(desc="进入了角色授权页面")
    public ModelAndView grantUI(@PathVariable String id){
    	logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行角色授权");
    	ModelAndView mav = new ModelAndView();
    	try {
    		String decrypt = QEncodeUtil.decryptId(id);//ID解密
    		if(decrypt==null){
    			logger.error("角色授权页面，grantUI异常，ID解析解密失败！");
    			mav.setViewName("error/400");
    		} else {
    			String pids = roleService.findPrivilegeIdsByRoleId(Long.valueOf(decrypt));
    			mav.addObject("privilegeIds",pids);
    			mav.addObject("id",id);
    			mav.setViewName("/back/role/grantUI");
    		}
        	return mav;
		} catch (Exception e) {
			logger.error("角色授权页面，grantUI出错{}",e);
			mav.setViewName("error/500");
			return mav;
		}
    	
    }
    /**
     * 授权页面页面根据角色查询资源
     * @param id
     * @return
     */
    @RequestMapping(value="/findPrivilegeIdListByRoleId",method=RequestMethod.POST)
    @ResponseBody
    public Object findPrivilegeByRoleId(String id) {
    	String decrypt = QEncodeUtil.decryptId(id);//ID解密
    	if(decrypt!=null){
    		List<Long> privileges = roleService.findPrivilegeIdListByRoleId(Long.valueOf(decrypt));
            try {
				return renderSuccess(QEncodeUtil.encrypt(privileges));
			} catch (Exception e) {
				logger.error("授权页面页面根据角色查询资源,失败！",e);
			}
    	}
        return null;
    }
    /**
     * 授权
     * @param id
     * @param resourceIds
     * @return
     */
    @RequestMapping(value="/grant",method=RequestMethod.POST)
    @ResponseBody
    @LogCustom(desc="进行了角色授权")
    public Object grant(String id, String privilegeIds) {
    	try {
    		String decrypt = QEncodeUtil.decryptId(id);//ID解密
    		if(decrypt!=null){
    			roleService.updateRolePrivilege(Long.valueOf(decrypt), QEncodeUtil.decryptKeys(privilegeIds));
    		}else{
    			logger.error("授权失败，grant方法异常，ID解析失败！");
    			return renderError("授权失败，数据解析失败！");
    		}
		} catch (Exception e) {
			logger.error("授权失败，grant方法出错！",e);
			return renderError("授权失败，无法完成授权");
		}
        return renderSuccess("授权成功！");
    }
    
    /**
     * @Title: rolePrivileges
     * @Description: 获取角色拥有的权限id列表
     * @param roleId
     * @return
     */
    @RequestMapping(value="/rolePrivileges",method=RequestMethod.POST)
    @ResponseBody
    public List<Long> rolePrivileges(String roleId){
    	String decrypt = QEncodeUtil.decryptId(roleId);//ID解密
    	if(decrypt==null){
    		return null;
    	}
    	return roleService.findPrivilegeIdListByRoleId(Long.valueOf(decrypt));
    }
	
    /**
     * @Title: deleteIDs
     * @Description: 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value="/deleteIDs",method=RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc="进行了角色批量删除")
	public Object deleteIDs(String ids){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行角色批量删除，删除项"+ids);
		int res = 0;
		try {
			String[] keys = QEncodeUtil.decryptKeys(ids);//id解密
			res = roleService.delete(keys);
		} catch (DataIntegrityViolationException e) {
			logger.error("角色删除，deleteIDs 删除处理 出错，无法删除关联对象！",e);
			return renderError("删除失败，存在角色已被系统引用！");
		} catch (Exception e) {
			logger.error("角色删除，deleteIDs 删除处理 出错{}",e);
			return renderError("删除失败，系统内部出错！");
		}
		if(res==0){
			return renderError("删除失败，无法对该角色进行删除！");
		}
		return renderSuccess("删除成功！");
	}
    
	/**
     * @Title: delete
     * @Description: 单个删除
     * @param id
     * @return
     */
    @RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="进行了单个角色删除")
	public Object delete(@PathVariable String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行角色删除，删除项："+id);
		int res = 0;
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){
				res = roleService.delete(Long.valueOf(decrypt));
			}else{
				logger.error("角色删除，delete 删除处理 出错，无法解析主键！");
				return renderError("删除失败，数据解析不正确！");
			}
		} catch (DataIntegrityViolationException e) {
			logger.error("角色删除，delete 删除处理 出错，无法删除关联对象！",e);
			return renderError("删除失败，不能删除被系统引用的角色！");
		} catch (Exception e) {
			logger.error("角色删除，delete 删除处理 出错{}",e);
			return renderError("删除失败，系统内部出错！");
		}
		if(res==0){
			return renderError("删除失败，无法对该角色进行删除！");
		}
		
		return renderSuccess("删除成功！");
	}
    
}
