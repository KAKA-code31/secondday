package com.honestpeak.result;

import com.honestpeak.utils.QEncodeUtil;

/**
 * @ClassName: IdEncrypt
 * @Description: ID 加密解密总父类
 * @author Jeabev
 * @date 2017年3月28日 上午10:10:58
 */
public class IdEncrypt {
	protected Long id;//真实ID
	private String encryptId;//加密ID
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEncryptId() {
		return encryptId==null?encrypt():encryptId;
	}
	public void setEncryptId(String encryptId) {
		this.encryptId = encryptId;
	}
	
	/**
	 * @Title: encrypt
	 * @Description: 加密
	 */
	public String encrypt(){
		if(this.id != null) {
			try {
				this.encryptId = QEncodeUtil.encryptId(this.id);
				return this.encryptId;
			} catch (Exception e) {
				System.err.println("加密解密：ID字段-系统加密失败！");
			}
		}
		//执行失败，返回null
		return null;
	}
	
	/**
	 * @Title: decrypt
	 * @Description: 解密
	 * @return
	 */
	public String decrypt(){
		return QEncodeUtil.decryptId(encryptId);
	}
}
