package com.honestpeak.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.honestpeak.constant.AccountConstants;

/**
 * @ClassName: LoginToken
 * @Description: 由于 {@link UsernamePasswordToken}
 *               ，不足以实现我们前、后端分开登陆的应用场景，我们自己构造一个Token.
 *               实现用户登陆时，设置不同的UserType。实现从不同的表中获取User对象。
 *               获取user信息。
 * @author Jeabev
 * @email jeabev@qq.com
 * @date 2017年3月22日 下午4:28:35
 * @version 1.0
 */
public class LoginToken extends UsernamePasswordToken {

	private static final long serialVersionUID = -2629714940293884422L;
	/**
	 * 登陆类型，参考 {@link AccountConstants#LOGIN_ADMIN} 管理员登陆 、 
	 * {@link AccountConstants#LOGIN_FRONT} 普通用户登陆
	 */
	private Integer loginType;
	
	//---get and set method---
	public Integer getLoginType() {
		return loginType;
	}
	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}
	
	//---
	public LoginToken(){
		
	}
	/**
	 * <p>Title: 构造器 </p>
	 * <p>Description: LoginToken 全参构造器，构造全部的参数信息 </p>
	 * @param username 用户登陆用户名信息
	 * @param password 用户登陆密码，要与数据库密码一致。前台加密的密码要解密，并采用后台密码加密算法加密。前后台不可用同一套加密算法。
	 * @param rememberMe cookie中是否记录该用户
	 * @param loginType 登陆类型指定
	 */
	public LoginToken(final String username, final char[] password,
            final boolean rememberMe, final Integer loginType){
		super.setUsername(username);
		super.setPassword(password);
		super.setRememberMe(rememberMe);
		this.setLoginType(loginType);
	}
	/**
	 * <p>Title: 全参构造器 </p>
	 * <p>Description: LoginToken 全参构造器，构造全部的参数信息 </p>
	 * @param username 用户登陆用户名信息
	 * @param password 用户登陆密码，要与数据库密码一致。前台加密的密码要解密，并采用后台密码加密算法加密。前后台不可用同一套加密算法。
	 * @param rememberMe cookie中是否记录该用户
	 * @param host 支持指定远程登陆服务器地址或者ip
	 * @param loginType 登陆类型指定
	 */
	public LoginToken(final String username, final char[] password,
			final boolean rememberMe, final String host, final Integer loginType){
		super.setUsername(username);
		super.setPassword(password);
		super.setRememberMe(rememberMe);
		super.setHost(host);
		this.setLoginType(loginType);
	}
	
	
}
