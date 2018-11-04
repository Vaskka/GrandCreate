package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: RegisterTryRequest 尝试注册的请求基类
 * @author: Vaskka
 * @create: 2018/10/31 10:13 PM
 **/

public class RegisterTryRequest extends BaseRequest {

    public String password;

    public RegisterTryRequest(String password, String nick_name) {
        this.password = password;
        this.nick_name = nick_name;
    }

    public RegisterTryRequest(String email, String password, String nick_name) {
        super(email);
        this.password = password;
        this.nick_name = nick_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String nick_name;


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
