package com.vaskka.api.chain.user.entity.exception;

/**
 * @program: GrandCreateApiSdk
 * @description: ChainApiFaceRegisterException 注册人脸出错
 * @author: Vaskka
 * @create: 2018/11/5 3:42 PM
 **/

public class ChainApiFaceRegisterException extends ChainApiBaseException {

    public ChainApiFaceRegisterException(String message, int code) {
        super(message, code);
    }
}
