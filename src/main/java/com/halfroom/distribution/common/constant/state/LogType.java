package com.halfroom.distribution.common.constant.state;

/**
 * 日志类型
 */
public enum LogType {

    /**
     * 登录日志
     */
    LOGIN("登录日志"),
    /**
     * 登录失败日志
     */
    LOGIN_FAIL("登录失败日志"),
    /**
     * 退出日志
     */
    EXIT("退出日志"),
    /**
     * 异常日志
     */
    EXCEPTION("异常日志"),
    /**
     * 业务日志
     */
    BUSSINESS("业务日志");

    String message;

    LogType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
