package com.honestpeak.vo;

import com.honestpeak.utils.QEncodeUtil;

/**
 * @ClassName: InsVo
 * @Description: id,name,status 三个基本字段的简单整合
 * @author Jeabev
 * @date 2017年4月5日 下午2:46:38
 */
public class InsVo {
	/** 主键 */
	private String id;
	/** 名称 */
	private String name;
	/** 状态 */
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @Title: encryptId
	 * @Description: 获取加密主键
	 * @throws Exception
	 */
	public String encryptId() throws Exception {
		String result = null;
		if(this.id!=null){
			result = QEncodeUtil.encryptId(id);
		}
		return result;
	}

	@Override
	public String toString() {
		return new StringBuffer("InsVo [id=")
				.append(id).append(", name=").append(name)
				.append(", status=").append(status).append("]").toString();
	}
}
