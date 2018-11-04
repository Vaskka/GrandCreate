package com.vaskka.api.chain.user.entity.response;

/**
 * @program: GrandCreateApiSdk
 * @description: TransactionConfirmResponse 收款确认响应
 * @author: Vaskka
 * @create: 2018/11/4 10:01 AM
 **/

public class TransactionConfirmResponse extends BaseResponse {

    private String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public TransactionConfirmResponse(String balance) {
        this.balance = balance;
    }

    public TransactionConfirmResponse(int code, String msg, String balance) {
        super(code, msg);
        this.balance = balance;
    }
}

