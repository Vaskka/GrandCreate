package com.vaskka.api.chain.user.util;

import okhttp3.*;

import java.util.concurrent.TimeUnit;

/**
 * @program: GrandCreateApiSdk
 * @description: HttpUtil http请求工具类
 * @author: Vaskka
 * @create: 2018/11/4 10:25 AM
 **/

public class HttpUtil {

    /**
     * okhttp client
     */
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * json 标志
     */
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("text/json; charset=utf-8");


    /**
     * post 异步
     * @param url url
     * @param json json字符串
     * @param callback 回调
     */
    public static void post(String url, String json, Callback callback) {
        Request request = new Request.Builder()
            .url(url).post(RequestBody.create(MEDIA_TYPE_JSON, json)).build();

        Call call = client.newCall(request);

        call.enqueue(callback);
    }
}
