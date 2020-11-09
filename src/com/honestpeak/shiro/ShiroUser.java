/**
 *
 */
package com.honestpeak.shiro;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ShiroUser
 * @Description: 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 * @author Jeabev
 * @date 2016年7月30日 下午4:23:17
 */
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = -1373760761780840081L;
    public Long id;
    public String loginName;
    public String name;
    public Integer adminType;//用户类型，教师1，俱乐部班干，学生0
    public List<Long> roleList;

    public ShiroUser(Long id, String loginName, String name, Integer adminType, List<Long> roleList) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.roleList = roleList;
        this.adminType = adminType;
    }

    public String getName() {
        return name;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return name;
    }

	public Integer getAdminType() {
		return adminType;
	}
}