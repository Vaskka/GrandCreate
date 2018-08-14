package com.vaskka.grand.user.request;


/**
 * @program: TestHttp
 * @description: LoginRequestObject 登陆请求
 * @author: Vaskka
 * @create: 2018/8/13 下午12:57
 **/

public class LoginRequestObject extends BaseRequestObject {
    private String password = "";

    public LoginRequestObject(String email, String psw) {
        super(email);
        this.password
                 = psw;
    }

    @Override
    public String toString() {
        return "{\"email\": \"" + this.email + "\", \"password\": \"" + this.password + "\"}";
    }
}
