package com.vaskka.api.chain.user.entity.exception;

/**
 * @program: GrandCreateApiSdk
 * @description: ChainApiBaseException 异常基类
 * @author: Vaskka
 * @create: 2018/11/4 10:08 AM
 **/

public class ChainApiBaseException extends Exception {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ChainApiBaseException(String message, int code) {
        super(message);
        this.code = code;
    }
}
