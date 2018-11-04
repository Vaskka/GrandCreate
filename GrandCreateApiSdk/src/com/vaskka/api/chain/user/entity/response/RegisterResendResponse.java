package com.vaskka.api.chain.user.entity.response;

/**
 * @program: GrandCreateApiSdk
 * @description: RegisterResendResponse 验证码重新发送响应
 * @author: Vaskka
 * @create: 2018/10/31 10:22 PM
 **/

public class RegisterResendResponse extends  BaseResponse {

    public RegisterResendResponse() {
    }

    public RegisterResendResponse(int code, String msg) {
        super(code, msg);
    }
}
