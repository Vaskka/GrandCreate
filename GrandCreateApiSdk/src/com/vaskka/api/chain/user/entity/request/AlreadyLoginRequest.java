package com.vaskka.api.chain.user.entity.request;

/**
 * @program: GrandCreateApiSdk
 * @description: AlreadyLoginRequest 已登陆用户的基类
 * @author: Vaskka
 * @create: 2018/10/31 10:24 PM
 **/

public class AlreadyLoginRequest extends BaseRequest {

    private String user_id;

    private String session_token;

    public AlreadyLoginRequest(String user_id, String session_token) {
        this.user_id = user_id;
        this.session_token = session_token;
    }

    public AlreadyLoginRequest(String email, String user_id, String session_token) {
        super(email);
        this.user_id = user_id;
        this.session_token = session_token;
    }

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

    @Override
    public String toString() {
        return super.toString();
    }
}
