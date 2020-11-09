package com.honestpeak.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honestpeak.constant.PageConstants;
import com.honestpeak.mapper.PrivilegeMapper;
import com.honestpeak.model.Privilege;
import com.honestpeak.result.Tree;
import com.honestpeak.service.PrivilegeService;
import com.honestpeak.utils.Config;
import com.honestpeak.utils.Page;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
	@Resource
	private PrivilegeMapper privilegeMapper;
	@Override
	public Page<Privilege> findPrivilegePage(Privilege privilege, int currentPage) {
		Page<Privilege> page = new Page<>(currentPage, PageConstants.PAGESIZE);
		List<Privilege> privileges = privilegeMapper.findPrivilegePage(privilege, page);
		page.setResultList(privileges);
		return page;
	}
	
	@Override
	public int save(Privilege privilege) {
		return privilegeMapper.insert(privilege);
	}

	@Override
	public int update(Privilege privilege) {
		return privilegeMapper.updateByPrimaryKeySelective(privilege);
	}

	@Override
	public int delete(Long id) {
		return privilegeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int delete(String[] ids) {
		return privilegeMapper.deleteAll(ids);
	}

	@Override
	public List<Privilege> findAllPrivilegeByType(Integer privilegeType) {
		return privilegeMapper.findAllPrivilegeByType(privilegeType);
	}

	@Override
	public List<Tree> findAllTree() {
		List<Tree> trees = null;
		List<Privilege> pps = privilegeMapper.findParentPrivilege();
		if(pps!=null){
			trees = new ArrayList<>();
			for (Privilege p : pps) {
				Tree t = new Tree();
				t.setId(p.getId());
				t.setName(p.getName());
				t.setIconCls(p.getIcon());
				t.setAttributes(p.getUrl());
				t.setpId(0l);
				List<Tree> childs = initResourceChild(Config.RESOURCE_MENU, p.getId(),false);
				if(childs!=null && childs.size()>0){
					t.setChildren(childs);
				}else{
					t.setOpen(false);
				}
				trees.add(t);
			}
		}
		return trees;
	}
	
	/**
	 * @Title: initResourceChild
	 * @Description: 初始化权限项子节点
	 * @param privilegeType 权限项类型
	 * @param privilegeId 父节点编号
	 * @param findThree 是否查找子节点按钮
	 * @return
	 */
	private List<Tree> initResourceChild(int privilegeType,long privilegeId,boolean findThree){
		List<Tree> trees = null;
		List<Privilege> childs = privilegeMapper.findAllPrivilegeByTypeAndPid(privilegeType,privilegeId);
		if(childs!=null && childs.size()>0){
			trees = new ArrayList<>();
			for(Privilege child : childs){
				Tree tree = new Tree();
				tree.setId(child.getId());
				tree.setName(child.getName());
				tree.setIconCls(child.getIcon());
				tree.setAttributes(child.getUrl());
				tree.setpId(privilegeId);
				if(findThree){
					tree.setChildren(this.initResourceChild(Config.RESOURCE_BUTTON, child.getId(), false));
				}
				trees.add(tree);
			}
		}
		return trees;
	}

	@Override
	public List<Tree> findAllTrees() {
		List<Tree> trees = null;
		List<Privilege> pps = privilegeMapper.findParentPrivilege();
		if(pps!=null){
			trees = new ArrayList<>();
			for (Privilege p : pps) {
				Tree t = new Tree();
				t.setId(p.getId());
				t.setName(p.getName());
				t.setIconCls(p.getIcon());
				t.setAttributes(p.getUrl());
				t.setpId(0l);
				List<Tree> childs = initResourceChild(Config.RESOURCE_MENU, p.getId(),true);
				if(childs!=null && childs.size()>0){
					t.setChildren(childs);
				}else{
					t.setOpen(false);
				}
				trees.add(t);
			}
		}
		return trees;
	}

	@Override
	public Privilege selectById(Long id) {
		return privilegeMapper.selectByPrimaryKey(id);
	}
	
}
