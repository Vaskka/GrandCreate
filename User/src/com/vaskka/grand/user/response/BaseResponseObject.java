package com.vaskka.grand.user.response;

import com.google.gson.Gson;

/**
 * @program: User
 * @description: BaseResponseObject 基础HttpResponse类 包含json处理能力
 * @author: Vaskka
 * @create: 2018/8/13 下午4:18
 **/

public class BaseResponseObject {
    protected String rawResponse;

    protected class BaseInnerParam {
        protected int code;
        protected String msg;
    }

    public boolean isOK() {
        return isOK;
    }

    protected boolean isOK = false;

    protected String result;

    public BaseInnerParam getInner() {
        return inner;
    }

    protected BaseInnerParam inner;

    public String getRawResponse() {
        return rawResponse;
    }

    public BaseResponseObject(String rawResponse) {
        this.rawResponse = rawResponse;
        inner = new Gson().fromJson(rawResponse, BaseInnerParam.class);
        if (inner.code == 0) {
            isOK = true;
        }
        result = inner.msg;
    }

    public BaseResponseObject() {

    }



    @Override
    public String toString() {
        if (result != null) {
            return result;
        }
        return "This response is not ready";
    }
}
