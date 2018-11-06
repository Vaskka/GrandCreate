package com.vaskka.api.chain.user.entity.response;

/**
 * @program: GrandCreateApiSdk
 * @description: GetFaceTokenResponse 获取facetoken的响应
 * @author: Vaskka
 * @create: 2018/11/5 1:10 AM
 **/

public class GetFaceTokenResponse extends  BaseResponse {

    private String face_token;

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public GetFaceTokenResponse(String face_token) {
        this.face_token = face_token;
    }

    public GetFaceTokenResponse(int code, String msg, String face_token) {
        super(code, msg);
        this.face_token = face_token;
    }
}
