package com.example.jwtdemo.web.common.error.exception;

/**
 * @author shuai
 */
public class BusinessApiException extends RuntimeException {

    /**
     * 异常信息代码
     */
    private String code;
    /**
     * 异常信息参数
     */
    private Object[] args;
    /**
     * 自定义携带信息
     */
    private Object data;

    public BusinessApiException(String code) {
        super();
        this.code = code;
    }

    public BusinessApiException(String code, Object[] args) {
        super();
        this.code = code;
        this.args = args;
    }

    public BusinessApiException(String code, Object data) {
        super();
        this.code = code;
        this.data = data;
    }

    public BusinessApiException(String code, Object[] args, Object data) {
        super();
        this.code = code;
        this.data = data;
        this.args = args;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getData() {
        return data;
    }
}