package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: LoginRequest 已经注册用户登陆
 * @author: Vaskka
 * @create: 2018/11/4 9:42 AM
 **/

public class LoginRequest extends BaseRequest {
    private String password;

    public LoginRequest(String password) {
        this.password = password;
    }

    public LoginRequest(String email, String password) {
        super(email);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
