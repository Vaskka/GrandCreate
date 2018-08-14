package com.vaskka.grand.user.request;

/**
 * @program: TestHttp
 * @description: AddFriendRequestObject 添加好友请求
 * @author: Vaskka
 * @create: 2018/8/13 下午1:00
 **/

public class AddFriendRequestObject extends LogoutRequestObject {

    private String joinEmail = "";

    public AddFriendRequestObject(String email, String token, String joinEmail) {
        super(email, token);

        this.joinEmail = joinEmail;
    }

    @Override
    public String toString() {
        return "{\"email\": \"" + this.email + "\", \"token\": \"" + this.token + "\", \"join_email\": \"" + this.joinEmail + "\"}";
    }
}
