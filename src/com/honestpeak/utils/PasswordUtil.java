package com.honestpeak.utils;

/**
 * @ClassName: PasswordUtil
 * @Description: 密码工具类
 * @author Jeabev
 * @date 2016年8月4日 上午10:47:14
 */
public class PasswordUtil {
	public static String DEFAULT = "111333";
	/**
	 * @Title: createDefaultPassword
	 * @Description: 创建初始密码 默认“111333”
	 * @return
	 */
	public static String createDefaultPassword(){
		return Encrypt.SHA256(DEFAULT);
	}
	
	/**
	 * @Title: encryption
	 * @Description: 密码加密
	 * @param pass
	 * @return
	 */
	public static String encryption(String pass){
		return Encrypt.SHA256(pass);
	}
	
	/**
	 * @Title: decrypt
	 * @Description: 密码解密
	 * @param pass
	 * @return
	 */
	public static String decrypt(String pass){
		return Encrypt.SHA256(pass);
	}
	
}
