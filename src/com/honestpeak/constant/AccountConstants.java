package com.honestpeak.constant;

/**
 * @ClassName: AccountConstants
 * @Description: 用户常量信息 数据字典
 * @author Jeabev
 * @date 2016年8月1日 下午3:41:22
 */
public class AccountConstants {
	
	//账户状态
	/** 启用   账户状态正常   1 */
	public static final int ENABLE = 1;
	/** 停用   账户被限制登录   0 */
	public static final int DISABLE = 0;
	
	//登陆类型
	/** 登陆类型 登陆   1 */
	public static final int LOGIN = 1;
	/** 登陆类型 登出   0 */
	public static final int LOGOUT = 0;
	
	//登陆状态
	/** 登陆成功    1 */
	public static final int LOGSUCC = 1;
	/** 登陆失败    0 */
	public static final int LOGFAIL = 0;
	
	
	//登陆失败状态
	/** 登陆错误状态  默认   0 */
	public static final int DEFAULT = 0;
	/** 登陆错误状态  账户不存在  1 */
	public static final int NOTEXIST = 1;
	/** 登陆错误状态  账户未启用  2 */
	public static final int NOTENABLE = 2;
	/** 登陆错误状态  用户名错误  3 */
	public static final int NAMEERROR = 3;
	/** 登陆错误状态  密码错误  4 */
	public static final int PWDERROR = 4;
	/** 登陆错误状态  其他错误  5 */
	public static final int OTHER = 5;
	
	/**
	 * 登陆类型，前台登陆，登陆对象  学生、非系统管理员、不参与后台管理的用户
	 */
	public static final int LOGIN_FRONT = 0;
	/**
	 * 登陆类型，后台登陆，登陆对象 管理员、教师、参与后台管理的用户
	 */
	public static final int LOGIN_ADMIN = 1;
	/**
	 * 审核状态  未审核
	 */
	public static final int IS_CHECK_NO = 0;
	
	/**
	 * 审核状态  审核通过
	 */
	public static final int IS_CHECK_TRUE = 1;
	
	/**
	 * 审核状态  审核未通过
	 */
	public static final int IS_CHECK_FALSE = 2;
	
	
	/**
	 * 显示状态 不显示
	 */
	public static final int VISIBLE_FALSE = 0; 
	
	/**
	 * 显示状态 显示
	 */
	public static final int VISIBLE_TRUE = 1; 
	
}
