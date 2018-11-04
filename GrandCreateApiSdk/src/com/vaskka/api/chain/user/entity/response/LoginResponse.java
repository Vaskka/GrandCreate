package com.vaskka.api.chain.user.entity.response;

/**
 * @program: GrandCreateApiSdk
 * @description: LoginResponse 登陆响应
 * @author: Vaskka
 * @create: 2018/11/4 9:44 AM
 **/

public class LoginResponse extends BaseResponse {

    private String user_id;

    private String session_token;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSession_token() {
        return session_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }

    public LoginResponse(String user_id, String session_token) {
        this.user_id = user_id;
        this.session_token = session_token;
    }

    public LoginResponse(int code, String msg, String user_id, String session_token) {
        super(code, msg);
        this.user_id = user_id;
        this.session_token = session_token;
    }
}
