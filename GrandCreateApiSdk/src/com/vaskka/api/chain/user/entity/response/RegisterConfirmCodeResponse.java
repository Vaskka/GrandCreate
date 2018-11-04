package com.vaskka.api.chain.user.entity.response;

/**
 * @program: GrandCreateApiSdk
 * @description: RegisterConfirmCodeResponse 用户注册验证码验证响应
 * @author: Vaskka
 * @create: 2018/10/31 10:18 PM
 **/

public class RegisterConfirmCodeResponse extends BaseResponse {

    private String user_id;

    public RegisterConfirmCodeResponse(String user_id, String session_token) {
        this.user_id = user_id;
        this.session_token = session_token;
    }

    public RegisterConfirmCodeResponse(int code, String msg, String user_id, String session_token) {
        super(code, msg);
        this.user_id = user_id;
        this.session_token = session_token;
    }

    private String session_token;

    public String getSession_token() {
        return session_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
