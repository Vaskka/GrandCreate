package com.vaskka.api.chain.user.entity.response;

/**
 * @program: GrandCreateApiSdk
 * @description: ChargeResponse 充值响应
 * @author: Vaskka
 * @create: 2018/11/4 9:48 AM
 **/

public class ChargeResponse extends BaseResponse {
    private String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public ChargeResponse(String balance) {
        this.balance = balance;
    }

    public ChargeResponse(int code, String msg, String balance) {
        super(code, msg);
        this.balance = balance;
    }
}
