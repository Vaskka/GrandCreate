package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: TransactionConfirmRequest 转账确认请求
 * @author: Vaskka
 * @create: 2018/11/4 9:56 AM
 **/

public class TransactionConfirmRequest extends AlreadyLoginRequest {

    private String order_id;

    private String face_token;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public TransactionConfirmRequest(String user_id, String session_token, String order_id, String face_token) {
        super(user_id, session_token);
        this.order_id = order_id;
        this.face_token = face_token;
    }

    public TransactionConfirmRequest(String email, String user_id, String session_token, String order_id, String face_token) {
        super(email, user_id, session_token);
        this.order_id = order_id;
        this.face_token = face_token;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
