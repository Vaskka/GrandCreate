package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: BaseRequest api请求基类
 * @author: Vaskka
 * @create: 2018/10/31 10:10 PM
 **/

public class BaseRequest {
    private String email;

    public BaseRequest() {}

    public BaseRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
