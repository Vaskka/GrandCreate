package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: RegisterResendRequest 验证码重新发送请求
 * @author: Vaskka
 * @create: 2018/10/31 10:21 PM
 **/

public class RegisterResendRequest extends BaseRequest {
    public RegisterResendRequest() {
    }

    public RegisterResendRequest(String email) {
        super(email);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
