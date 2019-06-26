package com.halfroom.distribution.common.constant.tips;

import com.halfroom.distribution.common.exception.BizExceptionEnum;

/**
 * 返回给前台的错误提示
 */
public class ErrorTip extends AbstractTip {

    public ErrorTip(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ErrorTip(BizExceptionEnum bizExceptionEnum) {
        this.code = bizExceptionEnum.getCode();
        this.message = bizExceptionEnum.getMessage();
    }
}
