package com.vaskka.api.chain.user.util;

/**
 * @program: GrandCreateApiSdk
 * @description: ConstUtil 常量
 * @author: Vaskka
 * @create: 2018/11/4 8:10 PM
 **/

public class ConstUtil {


    /**
     * 测试环境
     */
    @Deprecated
    public static String TEST_HOST = "http://localhost:8000";

    /**
     * 线上环境
     */
    @Deprecated
    public static String ONLINE_HOST = "http://www.vaskka.com";

    /**
     * 是否是debug
     */
    public static boolean DEBUG = true;

    /**
     * 人脸识别标示度
     */
    public static int STD_FACE_SCORE = 85;

}
