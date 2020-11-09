package com.honestpeak.service;

import java.util.List;

import com.honestpeak.model.Privilege;
import com.honestpeak.result.Tree;
import com.honestpeak.utils.Page;

/**
 * @ClassName: PrivilegeService
 * @Description: 具体权限业务逻辑层接口
 * @author Jeabev
 * @date 2016年8月6日 下午3:23:45
 */
public interface PrivilegeService {
	
	/**
	 * @Title: selectById
	 * @Description: 按主键查找权限项
	 * @param id 主键
	 * @return
	 */
	Privilege selectById(Long id);
	
	/**
	 * @param privilege 
	 * @Title: findPrivilegePage
	 * @Description: 查询所有权限项，带分页
	 * @param currentPage
	 * @return
	 */
	Page<Privilege> findPrivilegePage(Privilege privilege, int currentPage);
	
	/**
	 * @Title: save
	 * @Description:权限项保存
	 * @param privilege
	 * @return
	 */
	int save(Privilege privilege);
	
	/**
	 * @Title: update
	 * @Description: 权限项更新
	 * @param privilege
	 * @return
	 */
	int update(Privilege privilege);
	
	/**
	 * @Title: delete
	 * @Description: 删除单个权限项
	 * @param id
	 * @return
	 */
	int delete(Long id);
	
	/**
	 * @Title: delete
	 * @Description: 批量删除
	 * @param ids
	 * @return
	 */
	int delete(String[] ids);
	
	/**
	 * @Title: findAllPrivilegeByType
	 * @Description: 按权限项类型查找所有权限列表集合
	 * @param privilegeType
	 * @return
	 */
	List<Privilege> findAllPrivilegeByType(Integer privilegeType);
	
	/**
	 * @Title: findAllTree
	 * @Description: 生成二级菜单树
	 * @return
	 */
	List<Tree> findAllTree();
	
	/**
	 * @Title: findAllTrees
	 * @Description: 生成三级级菜单树
	 * @return
	 */
	List<Tree> findAllTrees();
}
