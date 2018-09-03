package com.vaskka.grand.user.util;

public interface HttpCallBack {
    public void onFailure(int code);

    public void onResponse(String json);
}
