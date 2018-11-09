package com.vaskka.frontend.entity;

import java.util.Date;

public class Receive {

    // 交易额
    private String tradeValue;

    // 交易日期
    private Date date;


    // 发起人邮箱
    private String sender_email;

    // 发起人昵称
    private String sender_nick_name;

    // order_id
    private  String orderId;

    public Receive(String tradeValue, Date date, String sender_email, String sender_nick_name, String orderId) {
        this.tradeValue = tradeValue;
        this.date = date;
        this.sender_email = sender_email;
        this.sender_nick_name = sender_nick_name;
        this.orderId = orderId;
    }

    public String getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(String tradeValue) {
        this.tradeValue = tradeValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSender_email() {
        return sender_email;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public String getSender_nick_name() {
        return sender_nick_name;
    }

    public void setSender_nick_name(String sender_nick_name) {
        this.sender_nick_name = sender_nick_name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
