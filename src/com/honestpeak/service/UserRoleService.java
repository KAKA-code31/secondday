package com.honestpeak.service;

import java.util.List;

import com.honestpeak.model.Role;
import com.honestpeak.model.Teacher;
import com.honestpeak.model.UserRole;
import com.honestpeak.utils.Page;

/**
 * @ClassName: UserRoleService
 * @Description: 用户-角色 业务逻辑层 接口
 * @author Jeabev
 * @date 2016年8月17日 下午3:16:59
 *
 */
public interface UserRoleService {

	/**
	 * @Title: save
	 * @Description: 保存用户的权限
	 * @param userId
	 *            管理员id
	 * @param roleIds
	 *            角色ids
	 * @Deprecated
	 *             <p>
	 *             保存用户权限时无法同时修改管理员类型，无法对显示数据访问权限进行控制。<br/>
	 *             该方法不推荐使用,具体参见：
	 *             {@link UserRoleService#save(Integer, Integer[], Integer)}
	 *             <br/>
	 *             <p>
	 * @return
	 */
	int save(Long userId, Long[] roleIds);

	/**
	 * @Title: save
	 * @Description: 保存用户的权限同时更新用户管理员身份标识。<br/>
	 *               需要传入三个非空参数，管理员主键{@code userId}、 管理员角色ids{@code roleIds}、
	 *               管理员类型{@code roleType}。<br/>
	 *               更新用户角色时，同时调用{@code TeacherMapper#up}
	 * @param userId
	 *            管理员id
	 * @param roleIds
	 *            角色ids
	 * @param roleType
	 *            管理员类型
	 * @return
	 */
	int save(Long userId, String[] roleIds, Integer roleType);

	/**
	 * @Title: findUserRoles
	 * @Description: 获取角色列表，并将用户所拥有的角色标记出来
	 * @param userId
	 *            用户编号
	 * @return
	 */
/*	List<Role> findUserRoles(Long userId);*/
	
	/**
	 * @Title: save
	 * @Description: 更新用户的权限
	 * @param userId
	 *            管理员id
	 * @param roleIds
	 *            角色ids
	 * @Deprecated
	 *             <p>
	 *             保存用户权限时无法同时修改管理员类型，无法对显示数据访问权限进行控制。<br/>
	 *             该方法不推荐使用,具体参见：
	 *             {@link UserRoleService#update(Integer, Integer[], Integer)}
	 *             <br/>
	 *             <p>
	 * @return
	 */
	int update(Long userId, Long[] roleIds);
	
	/**
	 * @Title: update
	 * @Description: 更新用户的权限同时更新用户管理员身份标识。<br/>
	 *               需要传入三个非空参数，管理员主键{@code userId}、 管理员角色ids{@code roleIds}、
	 *               管理员类型{@code roleType}。<br/>
	 *               更新用户角色时，同时调用{@code TeacherMapper#up}
	 * @param userId
	 * @param roleIds
	 * @param roleType
	 * @return
	 */
	int update(Long userId, String[] roleIds, Integer roleType);
	
	/**
	 * @Title: deleteAll
	 * @Description: 删除一组用户的全部关联角色
	 * @param userIds
	 * @return
	 */
	int deleteAll(String[] userIds);

	/**
	 * @Title: delete
	 * @Description: 删除单个用户所关联的全部角色
	 * @param userId
	 * @return
	 */
	int delete(Long userId);
	
	/**
	 * @Title: selectByUserId
	 * @Description: 
	 * @param id
	 * @return
	 */
	UserRole selectByUserId(Long id);

	Page<Teacher> findUserRolePage(Teacher user, int currentPage);
	
	/**
	 * @Title: update
	 * @Description: 根据用户信息更新用户的权限信息，用户的管理俱乐部信息
	 * @param userId 用户id
	 * @param roleIds 角色主键信息
	 * @param clubIds 俱乐部主键信息
	 */
	int update(Long userId, String[] roleIds, String[] clubIds);

	void save(UserRole userRole);

	void updateUserRole(UserRole userRole);

	List<UserRole> getByUserId(Long id);

	int countByTeacherId(Long userId);
}
