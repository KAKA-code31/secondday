package com.honestpeak.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.honestpeak.constant.AccountConstants;
import com.honestpeak.model.Privilege;
import com.honestpeak.result.Tree;
import com.honestpeak.service.PrivilegeService;
import com.honestpeak.utils.Config;
import com.honestpeak.utils.DateTimeUtils;
import com.honestpeak.utils.Page;
import com.honestpeak.utils.QEncodeUtil;

/**
 * 
 * @author bpc
 *
 */
@Controller
@RequestMapping(value="/back/privilege")
@LogCustom(desc="权限项管理")
public class PrivilegeController extends BaseController{
	@Autowired
	private PrivilegeService privilegeService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @Title: manage
	 * @Description: 权限管理
	 * @return
	 */
	@RequestMapping(value="/manager")
	@LogCustom(desc="获取了权限项信息列表！")
	public ModelAndView manage(Privilege privilege, Integer currentPage){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行权限管理");
		ModelAndView mav = new ModelAndView();
		try {
			Page<Privilege> page = privilegeService.findPrivilegePage(privilege,currentPage==null?1:currentPage);
			//列表项
			mav.addObject("privilegeList", page.getResultList());
			//分页参数
			mav.addObject("currentPage", page.getPageNo());
			mav.addObject("totalPage", page.getTotalPage());
			mav.addObject("totalCount", page.getTotalCount());
			
			mav.addObject("privilege", privilege);
			mav.setViewName("back/privilege/manager");
		} catch (Exception e) {
			mav = new ModelAndView("error/500");
			logger.error("权限管理，manager出错{}",e);
		}
		return mav;
	}
	
	/**
	 * @Title: addUI
	 * @Description: 去权限详情 添加页面
	 * @return
	 */
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	@LogCustom(desc="进入权限项添加页面！")
	public ModelAndView addUI(){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，去行权限添加页面");
		ModelAndView mav = new ModelAndView();
		List<Privilege> parents=null;
		try {
			parents = privilegeService.findAllPrivilegeByType(Config.RESOURCE_MENU);
			mav.addObject("parents",parents);
			mav.setViewName("back/privilege/addUI");
		} catch (Exception e) {
			logger.error("权限项添加，查询所有菜单项出错！", e);
			mav.setViewName("error/500");
		}
		
		return mav;
	}
	
	/**
	 * @Title: add
	 * @Description: 添加处理
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc="进行权限项添加！")
	public Object add(Privilege privilege, String parentIdStr){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行权限添加处理");
		int res = 0;
		try {
			String decrypt = QEncodeUtil.decryptId(parentIdStr);//父菜单ID解密
			if( parentIdStr!=null && decrypt==null ){ //父菜单ID解密失败！
				privilege.setParentId(null);
				privilege.setStatus(AccountConstants.ENABLE);
				res = privilegeService.save(privilege);
			} else {
				privilege.setParentId(Long.valueOf(decrypt));
				privilege.setStatus(AccountConstants.ENABLE);
				res = privilegeService.save(privilege);
			}
		} catch (Exception e) {
			logger.error("权限添加，add 添加处理出错{}",e);
			return renderError("添加失败，系统内部错误！");
		}
		
		if(res==0){
			return renderError("添加失败，无法添加该权限！");
		}
		
		return renderSuccess("添加成功！");
	}
	
	/**
	 * @Title: editUI
	 * @Description: 去权限修改界面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editUI/{id}",method=RequestMethod.GET)
	@LogCustom(desc="进行权限项修改！")
	public ModelAndView editUI(@PathVariable String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，去行权限修改页面");
		
		ModelAndView mav = new ModelAndView();
		List<Privilege> parents=null;
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){
				Privilege privilege = privilegeService.selectById(Long.valueOf(decrypt));
				if(privilege!=null){
					parents = privilegeService.findAllPrivilegeByType(Config.RESOURCE_MENU);
					mav.addObject("parents",parents);
					mav.addObject("privilege",privilege);
					
					mav.setViewName("back/privilege/editUI");
				} else {
					logger.error("权限项修改失败，找不到对应的权限项信息！");
					mav.setViewName("error/500");
				}
			}else{
				logger.error("权限项修改失败，Id解密失败！");
				mav.setViewName("error/400");
			}
			
		} catch (Exception e) {
			logger.error("权限项修改，查询出错！", e);
			mav.setViewName("error/500");
		}
		return mav;
	}
	
	/**
	 * @Title: edit
	 * @Description: 权限编辑处理
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc="修改了一条权限项信息！")
	public Object edit(Privilege privilege, String parentIdStr){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行权限修改");
		int res = 0;
		try {
			String parent = QEncodeUtil.decryptId(parentIdStr);//父菜单ID解密
			String id = QEncodeUtil.decryptId(privilege.getEncryptId());//ID解密
			//先判断菜单ID信息
			if(id==null){
				return renderError("修改失败，表单数据解析失败！");
			} else if( parent==null && id !=null){
				//主键和父节点主键信息设置
				privilege.setId(Long.valueOf(id));
				privilege.setParentId(null);
				res = privilegeService.update(privilege);
			}
			else {
				//主键和父节点主键信息设置
				privilege.setId(Long.valueOf(id));
				privilege.setParentId(Long.valueOf(parent));
				
				res = privilegeService.update(privilege);
			}
		} catch (Exception e) {
			logger.error("权限修改，edit 编辑处理 出错{}",e);
			return renderError("修改失败，系统内部出错！");
		}
		if(res==0){
			return renderError("修改失败，无法对该权限进行修改！");
		}
		
		return renderSuccess("修改成功！");
	}
	/**
	 * 
	 * @Title: delete
	 * @Description: 权限删除处理
	 * @return Object 返回类型
	 */
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	@ResponseBody
	@LogCustom(desc="删除了一条权限项信息！")
	public Object delete(@PathVariable String id){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行权限删除");
		int res = 0;
		try {
			String decrypt = QEncodeUtil.decryptId(id);//ID解密
			if(decrypt!=null){
				res = privilegeService.delete(Long.valueOf(decrypt));
			}
		} catch (DataIntegrityViolationException e) {
			logger.error("权限删除，delete 删除处理 出错，无法删除关联对象！",e);
			return renderError("删除失败，不能删除有子目录的权限项！");
		} catch (Exception e) {
			logger.error("权限删除，delete 删除处理 出错{}",e);
			return renderError("删除失败，系统内部出错！");
		}
		if(res==0){
			return renderError("删除失败，无法对该权限进行删除！");
		}
		
		return renderSuccess("删除成功！");
	}
	/**
	 * 
	 * @Title: deleteIDs
	 * @Description: 删除多个权限信息
	 * @return Object 返回类型
	 */
	@RequestMapping(value="/deleteIDs",method=RequestMethod.POST)
	@ResponseBody
	@LogCustom(desc="删除了多条权限项信息！")
	public Object deleteIDs(String ids){
		logger.info(this.getUserName()+"于"+DateTimeUtils.DateToDateTimeString(new Date())+"，进行权限删除");
		int res = 0;
		try {
			//主键信息解密
			String[] keys = QEncodeUtil.decryptKeys(ids);
			if(keys!=null && keys.length>0){
				res = privilegeService.delete(keys);
			}
		} catch (DataIntegrityViolationException e) {
			logger.error("权限删除，deleteIDs 删除处理 出错，无法删除关联对象！",e);
			return renderError("删除失败，不能删除有子目录的权限项！");
		} catch (Exception e) {
			logger.error("权限删除，delete 删除处理 出错{}",e);
			return renderError("删除失败，系统内部出错！");
		}
		if(res==0){
			return renderError("删除失败，无法对该权限进行删除！");
		}
		
		return renderSuccess("删除成功！");
	}
	/**
	 * 
	 * @Title: findAllTree
	 * @Description: 查找权限树
	 * @return List<Tree> 返回类型
	 */
	@RequestMapping(value="/findAllTree",method=RequestMethod.POST)
	@ResponseBody
	public List<Tree> findAllTree(){
		return privilegeService.findAllTrees();
	}
	
}
