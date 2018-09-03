package com.vaskka.grand.user.util;

import okhttp3.*;

import java.io.*;

import java.util.Map;

/**
 * @program: User
 * @description: HttpUtil 操作http的请求
 * @author: Vaskka
 * @create: 2018/8/13 下午3:36
 **/

public class HttpUtil {

    private static OkHttpClient client;


    private static OkHttpClient getOkHttpClient() {
        if (client == null) {
            client = new OkHttpClient();
        }
        return client;
    }

    private static  String getFullUrl(String url, Map<String, String> params) {
        int count = 0;

        for (Map.Entry<String, String> param : params.entrySet()) {
            if (count == 0) {
                url += "?";
            }
            else {
                url += "&";
            }

            url += (param.getKey() + "=" + param.getValue());
            count++;
        }

        return url;
    }


    public static void get(String url, Map<String, String> params, HttpCallBack mCallBack) {

        url = getFullUrl(url, params);
        System.out.println(url);
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mCallBack.onFailure(-1);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    mCallBack.onResponse(response.body().string());
                    // String str = response.body().string();
                }

            }
        });

    }


    public static void post(String url, String json, HttpCallBack mCallBack) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder().post(body).url(url).build();
        getOkHttpClient().newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                mCallBack.onFailure(-1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    mCallBack.onResponse(response.body().string());
                }
            }
        });

    }

}
