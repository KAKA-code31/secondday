package com.honestpeak.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.honestpeak.constant.Constants;  
  
/**
 * @ClassName: QEncodeUtil
 * @Description: 编码工具类 
 * 1.将byte[]转为各种进制的字符串 
 * 2.base 64 encode 
 * 3.base 64 decode 
 * 4.获取byte[]的md5值 
 * 5.获取字符串md5值 
 * 6.结合base64实现md5加密 
 * 7.AES加密 
 * 8.AES加密为base 64 code 
 * 9.AES解密 
 * 10.将base 64 code AES解密 
 * 11.支持加密基本类型，基本类型包装类，String。
 * @author Jeabev
 * @date 2017年3月25日 上午10:06:51
 */
public class QEncodeUtil {
	private final static int KEY_SIZE = 128;
      
    /** 
     * base 64 encode 
     * @param bytes 待编码的byte[] 
     * @return 编码后的base 64 code 
     */  
    private static String base64Encode(byte[] bytes){
    	Base64 sd = new Base64(true);//因为这里的ID需要传到前台，采用URL_SAFE模式进行加密
    	return new String(sd.encode(bytes));
    }  
      
    /** 
     * base 64 decode 
     * @param base64Code 待解码的base 64 code 
     * @return 解码后的byte[] 
     * @throws Exception 
     */  
    private static byte[] base64Decode(String base64Code) throws Exception{  
    	return StringUtils.isBlank(base64Code) ? null : new Base64().decode(base64Code.getBytes());
    }  
      
    /** 
     * 获取byte[]的md5值 
     * @param bytes byte[] 
     * @return md5 
     * @throws Exception 
     */  
    private static byte[] md5(byte[] bytes) throws Exception {  
        MessageDigest md = MessageDigest.getInstance("MD5");  
        md.update(bytes);  
          
        return md.digest();  
    }  
      
    /** 
     * 获取字符串md5值 
     * @param msg  
     * @return md5 
     * @throws Exception 
     */  
    private static byte[] md5(String msg) throws Exception {  
        return StringUtils.isBlank(msg) ? null : md5(msg.getBytes());  
    }  
      
    /** 
     * 结合base64实现md5加密 
     * @param msg 待加密字符串 
     * @return 获取md5后转为base64 
     * @throws Exception 
     */  
    public static String md5Encrypt(String msg) throws Exception{  
        return StringUtils.isBlank(msg) ? null : base64Encode(md5(msg));  
    }  
      
    /** 
     * AES加密 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的byte[] 
     * @throws Exception 
     */  
    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" ); 
        secureRandom.setSeed(encryptKey.getBytes()); 
        kgen.init(KEY_SIZE, secureRandom);  
        SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");  
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);  
          
        return cipher.doFinal(byteContent);  
        
        /*
         * KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" ); 
        secureRandom.setSeed(password.getBytes()); 
		kgen.init(length, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		byte[] byteContent = content.getBytes("utf-8");
		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(byteContent);
		return result; // 加密
		
         */
    }  
      
    /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
        return base64Encode(aesEncryptToBytes(content, encryptKey));  
    }  
      
    /** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */  
    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" ); 
        secureRandom.setSeed(decryptKey.getBytes()); 
        kgen.init(KEY_SIZE, secureRandom);  
        SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, key);  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
          
        return new String(decryptBytes);  
        /**
         * KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" ); 
      	secureRandom.setSeed(password.getBytes()); 
		kgen.init(length, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(content);
		return result; // 加密
         */
    }  
      
    /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {  
        return StringUtils.isBlank(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);  
    }
    
    /**
	 * @throws Exception 
     * @Title: encryptId
	 * @Description: 加密
	 */
	public static String encryptId(Object id) throws Exception {
		if(id != null) {
			String key = QEncodeUtil.md5Encrypt(DateTimeUtils.DateToDateTimeString(new Date(), "yyyyMMdd"));
			return QEncodeUtil.aesEncrypt(id.toString(), key).trim();
		}
		//执行失败，返回null
		return null;
	}
	
	/**
	 * @Title: decryptId
	 * @Description: 解密
	 * @return
	 */
	public static String decryptId(String encryptId){
		String decrypt = null;
		if(encryptId != null){
			String[] keys = null;
			try {
				keys = initKeys();
			} catch (Exception e) {
				System.err.println("加密解密：keys-系统生成秘钥失败！");
			}
			if(keys!=null && keys.length>0){
				for (String key : keys) {
					try {
						decrypt = QEncodeUtil.aesDecrypt(encryptId, key);
					} catch (Exception e) {
						System.err.println("加密解密：ID字段-系统解密失败！");
					}
					if(decrypt!=null){
						break;
					}
				}
			}
		}
		//解密失败返回null
		return decrypt;
	}
	
	/**
	 * @Title: initKeys
	 * @Description: 生成解密的 key 数组，解决凌晨系统更换 key 时，用户无法正常使用系统
	 * @return
	 * @throws Exception 
	 */
	private static String[] initKeys() throws Exception {
		String[] sjs = null;
		//获取当前时间
		Long sj = Long.valueOf(DateTimeUtils.DateToDateTimeString(new Date(), "HHmmss"));
		if(sj < Constants.KEY_SYSTEM_MISSTIME){
			sjs = new String[2];
			//将昨天的日期作为key
			sjs[0] = QEncodeUtil.md5Encrypt(DateTimeUtils.DateToDateTimeString(DateTimeUtils.getPreDay(new Date()), "yyyyMMdd"));
			//将今天的日期作为key
			sjs[1] = QEncodeUtil.md5Encrypt(DateTimeUtils.DateToDateTimeString(new Date(), "yyyyMMdd"));
		} else {
			sjs = new String[1];
			//将今天的日期作为key
			sjs[0] = QEncodeUtil.md5Encrypt(DateTimeUtils.DateToDateTimeString(new Date(), "yyyyMMdd"));
		}
		return sjs;
	}
	
	/**
     * @Title: decryptKeys
     * @Description: 主键信息解密。次方法用于一串字符串（如：1,2,3,4），解密到字符串数组中。<br>
     * 应用场景，前台复选框，传入一组ID数据，后台调用此方法直接将有效的ID信息解密出来，并放到字符数组中
     * @param ids
     * @return
     */
    public static String[] decryptKeys(String ids) {
    	if(ids==null){
    		return null;
    	}
    	String[] keys = ids.split(",");
    	List<String> decrypts = new ArrayList<>();
    	for (String id : keys) {
    		String code = decryptId(id);
    		if(code!=null){
    			decrypts.add(code);
    		}
		}
    	String[] cods = new String[decrypts.size()];
		return (decrypts.size()==0?null:decrypts.toArray(cods));
	}
    
    /**
	 * @Title: encrypt
	 * @Description: 提供一组 List<?> ids。将其加密成List<String>返回。注意，该方法只支持加密基本类型，基本类型包装类，String。
	 * @param ids 原始id列表
	 * @throws Exception
	 */
    public static List<String> encrypt(List<?> ids) throws Exception {
		List<String> encrypts = null;
		if(ids!=null && ids.size()>0){
			encrypts = new ArrayList<>();
			for (Object encrypt : ids) {//循环加密
				encrypts.add(QEncodeUtil.encryptId(encrypt));
			}
		}
		return encrypts;
	}
    
}
