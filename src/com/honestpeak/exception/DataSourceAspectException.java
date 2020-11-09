package com.honestpeak.exception;

/**
 * @ClassName: DataSourceAspectException
 * @Description: 数据库切换异常
 * @author Jeabev
 * @date 2016年8月1日 下午3:37:16
 */
public class DataSourceAspectException extends RuntimeException {

    private static final long serialVersionUID = 1401593546385403721L;

    public DataSourceAspectException() {
        super();
    }

    public DataSourceAspectException(String message) {
        super(message);
    }

    public DataSourceAspectException(Throwable cause) {
        super(cause);
    }

    public DataSourceAspectException(String message, Throwable cause) {
        super(message, cause);
    }
}
