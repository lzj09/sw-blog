package com.swnote.common.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * 请求处理结果封装类
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-23]
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 6349673870945237786L;

    /**
     * 请求处理成功
     */
    public final static int CODE_SUCCESS = 20;
    
    /**
     * 没有操作权限
     */
    public final static int CODE_LIMIT = 30;
    
    /**
     * 请求出错
     */
    public final static int CODE_EXCEPTION = 40;

    /**
     * 状态码
     */
    private int code;
    
    /**
     * 消息
     */
    private String msg;
    
    /**
     * 结果值
     */
    private Object content;
    
    public Result() {
    }
    
    public Result(int code) {
        String msg = "";
        if (CODE_SUCCESS == code) {
            msg = "请求处理成功";
        } else if (CODE_LIMIT == code) {
            msg = "没有操作权限";
        } else if (CODE_EXCEPTION == code) {
            msg = "请求出错";
        }
        this.code = code;
        this.msg = msg;
    }
    
    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public Result(int code, String msg, Object content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }
}