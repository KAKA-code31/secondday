package com.honestpeak.result;

import java.io.Serializable;

/**
 * @ClassName: Result
 * @Description: 操作结果集
 * @author Jeabev
 * @date 2016年8月1日 下午2:41:00
 */
public class Result implements Serializable {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;

    private static final long serialVersionUID = 5576237395711742681L;

    private boolean success = false;

    private String msg = "";

    private Object obj = null;
    
    /**
     * <p>Title: 无参构造器</p>
     * <p>Description: 默认无参数构造方法</p>
     */
    public Result() {
	}
    
    /**
     * <p>Description: 快速生成 执行结果标志，及执行信息 Result</p>
     * @param success 是否成功
     * @param msg 执行消息
     */
	public Result(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}
	
	/**
	 * <p>Title: 全参构造器</p>
	 * <p>Description: 全参数构造方法，用来快速构建类</p>
	 * @param success 是否成功
	 * @param msg 执行消息
	 * @param obj 返回数据
	 */
	public Result(boolean success, String msg, Object obj) {
		this.success = success;
		this.msg = msg;
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "Result [success=" + success + ", msg=" + msg + ", obj=" + obj + "]";
	}

	public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}
