package com.vaskka.grand.user.request;

/**
 * @program: TestHttp
 * @description: BaseRequestObject 请求访问的基类
 * @author: Vaskka
 * @create: 2018/8/13 下午12:47
 **/

public class BaseRequestObject {

    protected String email = "";


    public BaseRequestObject(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{\"email\": " + email + "}";
    }

}
