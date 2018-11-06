package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: GetUnreadRequest 轮询请求
 * @author: Vaskka
 * @create: 2018/11/6 11:18 AM
 **/

public class GetUnreadRequest extends AlreadyLoginRequest {

    public GetUnreadRequest(String user_id, String session_token) {
        super(user_id, session_token);
    }

    public GetUnreadRequest(String email, String user_id, String session_token) {
        super(email, user_id, session_token);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
