package com.honestpeak.constant;

import java.io.File;

/**
 * @ClassName: PathConstants
 * @Description: 系统监控路径常量 数据字典
 * @author Jeabev
 * @date 2016年8月13日 上午10:49:28
 */
public class PathConstants {
	/**
	 * 不需要进行权限验证的 NameSpace 信息
	 */
	public static final String[] N_NAMESPACE = {"editPasswordUI","checkoldPwd","editPassword"};

	/**
	 * 需要进行权限验证的请求路径
	 */
	public static String[] PERMITURL = {"manager","add","detail","edit","editIds", "editIDs","forceDelete","delete","deleteIDs","grant","check","release","upload","resetPassword","resetPasswords"};
	
	/**
	 * 需要记录进入log日志的请求路径
	 */
	public static String[] SYSLOGURL ={"login", "logout","add","edit","editIds","editIDs","forceDelete", "delete", "grant","manager","check","release","upload","resetPassword","resetPasswords"};
	
	/**
	 * 需要记录参数的log日志的请求方法
	 */
	public static String[] LOGPARAM ={"delete", "addUI", "editUI","checkUI","releaseUI"};
	/**
	 * 需要后台权限验证的方法
	 */
	public static String[] PERMIBACK ={"back"};
	
	/**
	 * 文件暂存文件夹
	 */
	public static String TEMP_PATH = File.separator+"temp"+File.separator;
	/**
	 * 考勤上传文件暂存文件夹
	 */
	public static String SIGN_PATH = File.separator+"sign"+File.separator;
	
}
