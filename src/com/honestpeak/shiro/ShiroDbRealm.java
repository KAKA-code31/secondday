package com.honestpeak.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.honestpeak.constant.AccountConstants;
import com.honestpeak.model.Role;
import com.honestpeak.model.User;
import com.honestpeak.service.RoleService;
import com.honestpeak.service.StudentService;
import com.honestpeak.service.TeacherService;

/**
 * @ClassName: ShiroDbRealm
 * @Description: shiro权限认证
 * @author Jeabev
 * @date 2016年7月30日 上午9:20:07
 */
public class ShiroDbRealm extends AuthorizingRealm {

	private static Logger LOGGER = LoggerFactory.getLogger(ShiroDbRealm.class);

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private StudentService studentService;

	/**
	 * Shiro登录认证(原理：用户提交 用户名和密码 --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ----
	 * shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		LOGGER.info("Shiro开始登录认证");
		LoginToken token = (LoginToken) authcToken;
		
		User user = null;
		if (token.getLoginType() == AccountConstants.LOGIN_FRONT) {
			// 获取学生、普通用户的账号信息。做前台登陆
             user = studentService.loginUserByLoginName(token.getUsername());
             if(user!=null){user.setAdminType(0);}
		} else if (token.getLoginType() == AccountConstants.LOGIN_ADMIN) {
			// 获取身份是教师的 登陆账号信息，因为这里是后台登陆，所以只获取教师登陆信息
			user = teacherService.loginUserByUserName(token.getUsername());			
		} else {
			//类型不正确、不在登陆类型之中
			throw new UnknownAccountException("登陆类型无法识别，登陆被禁止！");
		}

		// 账号不存在
		if (user == null) {
			// 抛出 账号不存在异常 action处理
			throw new UnknownAccountException("账号不存在");
		}
		List<Long> roleList = null;
		int enableCount = 0;
		Integer userType = user.getAdminType();
		if(userType == 0){			//若登录的用户是学生
			roleList = roleService.findStuRoleIdListByUserId(user.getUserId());
			for(Long roleId : roleList) {
				Role role = roleService.selectById(roleId);
				if(role == null) continue;
				LOGGER.debug(role.toString());
				enableCount++;
			}
		}else if(userType == 1){			//若登录的用户是教师
			roleList = roleService.findTeacherRoleIdListByUserId(user.getUserId());
			for(Long roleId : roleList) {
				Role role = roleService.selectById(roleId);
				if(role == null) continue;
				LOGGER.debug(role.toString());
				enableCount++;
			}
		}
		LOGGER.info("Shiro开始登录认证 enableCount=" + enableCount + ", adminType=" + user.getAdminType());
		// 账号未启用 (only check teacher,not check club manager role)
		if (enableCount == 0) { //抛出 账号未启用异常
			throw new DisabledAccountException("账号或角色未启用");
		}
		ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUserId(), user.getName(), user.getAdminType(), roleList);
		// 认证缓存信息
		return new SimpleAuthenticationInfo(shiroUser, user.getPassword().toCharArray(), getName());

	}

	/**
	 * Shiro权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		List<Long> roleList = shiroUser.roleList;

		Set<String> urlSet = new HashSet<String>();
		for (Long roleId : roleList) {
			Role role = roleService.selectById(roleId);
			List<Map<Long, String>> rolePrivilegeList = roleService.findRolePrivilegeListByRoleId(roleId);
			if (rolePrivilegeList != null && rolePrivilegeList.size() > 0) {
				for (Map<Long, String> map : rolePrivilegeList) {
					if (map != null && StringUtils.isNoneBlank(map.get("url"))) {
						urlSet.add(map.get("url"));
					}
				}
			}
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(urlSet);
		return info;
	}

}
