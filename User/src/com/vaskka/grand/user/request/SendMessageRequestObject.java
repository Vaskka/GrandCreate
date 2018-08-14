package com.vaskka.grand.user.request;

/**
 * @program: TestHttp
 * @description: SendMessageRequestObject 发送消息请求类
 * @author: Vaskka
 * @create: 2018/8/13 下午1:02
 **/

public class SendMessageRequestObject extends LogoutRequestObject {

    private String message = "";

    private String toEmail = "";

    public SendMessageRequestObject(String email, String token, String message, String toEmail) {
        super(email, token);
        this.email = email;
        this.toEmail = toEmail;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\"email\": \"" + this.email + "\", \"token\": \"" + this.token + "\", \"message\": \"" + this.message + "\", \"to_email\": \"" + this.toEmail + "\"}";

    }
}
