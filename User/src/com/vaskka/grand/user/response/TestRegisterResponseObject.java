package com.vaskka.grand.user.response;

import com.google.gson.Gson;

/**
 * @program: User
 * @description: TestRegisterResponseObject 验证注册用户
 * @author: Vaskka
 * @create: 2018/8/30 上午11:39
 **/

public class TestRegisterResponseObject extends BaseResponseObject {

    private class TestRegisterInnerParam extends BaseInnerParam {
        private String token_code;

        public String getToken_code() {
            return token_code;
        }

        public void setToken_code(String token_code) {
            this.token_code = token_code;
        }
    }

    public TestRegisterResponseObject(String rawResponse) {
        super(rawResponse);
        inner = (TestRegisterInnerParam) new Gson().fromJson(rawResponse, TestRegisterInnerParam.class);

        if (inner.code == 0 ) {

            this.isOK = false;
        }

        this.result = inner.msg;
    }

    /**
     * 获得token
     * @return String token_code
     */
    public String getTokenCode() {
        return ((TestRegisterInnerParam) inner).getToken_code();
    }

}
