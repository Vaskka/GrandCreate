package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: LogoutRequest 用户登出
 * @author: Vaskka
 * @create: 2018/11/4 9:45 AM
 **/

public class LogoutRequest extends AlreadyLoginRequest {
    public LogoutRequest(String user_id, String session_token) {
        super(user_id, session_token);
    }

    public LogoutRequest(String email, String user_id, String session_token) {
        super(email, user_id, session_token);
    }
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
