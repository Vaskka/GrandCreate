package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: TransactionInquireRequest 转账记录查询
 * @author: Vaskka
 * @create: 2018/11/4 10:03 AM
 **/

public class TransactionInquireRequest extends AlreadyLoginRequest {
    public TransactionInquireRequest(String user_id, String session_token) {
        super(user_id, session_token);
    }

    public TransactionInquireRequest(String email, String user_id, String session_token) {
        super(email, user_id, session_token);
    }
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
