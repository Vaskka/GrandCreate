package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: ChargeRequest 用户充值请求
 * @author: Vaskka
 * @create: 2018/11/4 9:47 AM
 **/

public class ChargeRequest extends AlreadyLoginRequest {
    private String trade_value;

    public String getTrade_value() {
        return trade_value;
    }

    public void setTrade_value(String trade_value) {
        this.trade_value = trade_value;
    }

    public ChargeRequest(String user_id, String session_token, String trade_value) {
        super(user_id, session_token);
        this.trade_value = trade_value;
    }

    public ChargeRequest(String email, String user_id, String session_token, String trade_value) {
        super(email, user_id, session_token);
        this.trade_value = trade_value;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
