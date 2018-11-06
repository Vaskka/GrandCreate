package com.vaskka.api.chain.user.entity.response;

import com.google.gson.Gson;
import com.sun.xml.internal.rngom.parse.host.Base;

/**
 * @program: GrandCreateApiSdk
 * @description: BaseResponse 响应基类
 * @author: Vaskka
 * @create: 2018/10/31 10:11 PM
 **/

public class BaseResponse {
    private int code;

    public BaseResponse() {};

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    public static Object load(String json, Class template) {
        Gson gson = new Gson();

        return gson.fromJson(json, template);
    }


}
