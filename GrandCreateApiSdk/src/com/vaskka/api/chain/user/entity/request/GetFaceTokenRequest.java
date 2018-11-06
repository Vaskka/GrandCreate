package com.vaskka.api.chain.user.entity.request;

/**
 * @program: GrandCreateApiSdk
 * @description: GetFaceTokenRequest 得到用户的facetoken
 * @author: Vaskka
 * @create: 2018/11/5 1:09 AM
 **/

public class GetFaceTokenRequest extends AlreadyLoginRequest {
    public GetFaceTokenRequest(String user_id, String session_token) {
        super(user_id, session_token);
    }

    public GetFaceTokenRequest(String email, String user_id, String session_token) {
        super(email, user_id, session_token);
    }
}
