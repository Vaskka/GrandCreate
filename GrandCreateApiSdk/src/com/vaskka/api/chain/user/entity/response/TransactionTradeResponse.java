package com.vaskka.api.chain.user.entity.response;

/**
 * @program: GrandCreateApiSdk
 * @description: TransactionTradeResponse 转账响应
 * @author: Vaskka
 * @create: 2018/11/4 9:51 AM
 **/

public class TransactionTradeResponse extends BaseResponse {
    private String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public TransactionTradeResponse(String balance) {
        this.balance = balance;
    }

    public TransactionTradeResponse(int code, String msg, String balance) {
        super(code, msg);
        this.balance = balance;
    }
}
