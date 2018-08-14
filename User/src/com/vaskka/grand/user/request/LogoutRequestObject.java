package com.vaskka.grand.user.request;

import com.vaskka.grand.user.request.BaseRequestObject;

/**
 * @program: TestHttp
 * @description: LogoutRequestObject 退出登陆
 * @author: Vaskka
 * @create: 2018/8/13 下午12:58
 **/

public class LogoutRequestObject extends BaseRequestObject {
    protected String token = "";

    public LogoutRequestObject(String email, String token) {
        super(email);
        this.token = token;
    }

    @Override
    public String toString() {
        return "{\"email\": \"" + this.email + "\", \"token\": \"" + this.token + "\"}";
    }
}
