package com.vaskka.grand.user.request;

import com.vaskka.grand.user.request.BaseRequestObject;

/**
 * @program: TestHttp
 * @description: RegisterRequestObject 注册请求基类
 * @author: Vaskka
 * @create: 2018/8/13 下午12:52
 **/

public class RegisterRequestObject extends BaseRequestObject {
    private String user_name = "";
    private String password = "";
    private String head_image = "";

    public RegisterRequestObject(String email, String user_name, String password ,String head_image) {
        super(email);
        this.user_name = user_name;
        this.head_image = head_image;
        this.password = password;
    }

    @Override
    public String toString() {
        return "{\"email\": \"" + this.email + "\", \"password\": \"" + this.password + "\", \"user_name\": \"" + this.user_name + "\", \"head_image\": \"" + this.head_image + "\"}";
    }
}
