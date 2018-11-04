package com.vaskka.api.chain.user.entity.request;

import com.google.gson.Gson;

/**
 * @program: GrandCreateApiSdk
 * @description: RegisterInsertFaceTokenRequest 插入人脸信息请求
 * @author: Vaskka
 * @create: 2018/10/31 10:23 PM
 **/

public class RegisterInsertFaceTokenRequest extends AlreadyLoginRequest {

    private  String face_token;

    public String getFace_token() {
        return face_token;
    }

    public RegisterInsertFaceTokenRequest(String user_id, String session_token, String face_token) {
        super(user_id, session_token);
        this.face_token = face_token;
    }

    public RegisterInsertFaceTokenRequest(String email, String user_id, String session_token, String face_token) {
        super(email, user_id, session_token);
        this.face_token = face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
