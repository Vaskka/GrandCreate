package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: RegisterConfirmCodeRequest 用户注册验证码验证
 * @author: Vaskka
 * @create: 2018/10/31 10:17 PM
 **/

public class RegisterConfirmCodeRequest  extends BaseRequest {

    private String verifyCode;

    public RegisterConfirmCodeRequest(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public RegisterConfirmCodeRequest(String email, String verifyCode) {
        super(email);
        this.verifyCode = verifyCode;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
