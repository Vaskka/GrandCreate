package com.vaskka.api.chain.user.entity.response;

/**
 * @program: GrandCreateApiSdk
 * @description: RegisterTryResponse 尝试注册相应
 * @author: Vaskka
 * @create: 2018/10/31 10:16 PM
 **/

public class RegisterTryResponse extends BaseResponse {
    public RegisterTryResponse() {
    }

    public RegisterTryResponse(int code, String msg) {
        super(code, msg);
    }
}
