package com.vaskka.api.chain.user.entity.response;

/**
 * @program: GrandCreateApiSdk
 * @description: LogoutResponse 用户登出响应
 * @author: Vaskka
 * @create: 2018/11/4 9:46 AM
 **/

public class LogoutResponse extends BaseResponse {
    public LogoutResponse() {
    }

    public LogoutResponse(int code, String msg) {
        super(code, msg);
    }
}
