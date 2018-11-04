package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: TransactionTradeRequest 转账请求
 * @author: Vaskka
 * @create: 2018/11/4 9:49 AM
 **/

public class TransactionTradeRequest extends AlreadyLoginRequest {

    private String receiver_user_email;

    private String trade_value;

    private  String face_token;

    public String getReceiver_user_email() {
        return receiver_user_email;
    }

    public void setReceiver_user_email(String receiver_user_id) {
        this.receiver_user_email = receiver_user_id;
    }

    public String getTrade_value() {
        return trade_value;
    }

    public void setTrade_value(String trade_value) {
        this.trade_value = trade_value;
    }

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public TransactionTradeRequest(String user_id, String session_token, String receiver_user_email, String trade_value, String face_token) {
        super(user_id, session_token);
        this.receiver_user_email = receiver_user_email;
        this.trade_value = trade_value;
        this.face_token = face_token;
    }

    public TransactionTradeRequest(String email, String user_id, String session_token, String receiver_user_email, String trade_value, String face_token) {
        super(email, user_id, session_token);
        this.receiver_user_email = receiver_user_email;
        this.trade_value = trade_value;
        this.face_token = face_token;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
