package com.vaskka.grand.user.response;

import com.google.gson.Gson;

/**
 * @program: User
 * @description: LoginResponseObject 用户登录Http响应
 * @author: Vaskka
 * @create: 2018/8/14 下午12:35
 **/

public class LoginResponseObject extends BaseResponseObject {


    public class LoginInnerParam extends BaseInnerParam {
        public String getToken() {
            return this.token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        private String token;

    }

    public LoginResponseObject(String rawResponse) {
        this.rawResponse = rawResponse;
        inner = new Gson().fromJson(rawResponse, LoginInnerParam.class);

        if (inner.code == 0) {
            isOK = true;

        }

        result = inner.msg;

    }

    /**
     * 登陆后拿到token
     * @return String token
     */
    public String getToken() {
        return ((LoginInnerParam) this.inner).getToken();
    }
}
