package com.vaskka.api.chain.user.lib;

import com.vaskka.api.chain.user.entity.request.*;
import com.vaskka.api.chain.user.lib.face.FaceRegisterCallback;
import com.vaskka.api.chain.user.lib.face.FaceVerifyCallback;
import com.vaskka.api.chain.user.util.FaceUtil;
import com.vaskka.api.chain.user.util.HttpUtil;

import okhttp3.Callback;



/**
 * @program: GrandCreateApiSdk
 * @description: ApiTool 操作api的工具
 * @author: Vaskka
 * @create: 2018/10/31 10:03 PM
 **/

public class ApiTool {

    public static String BASE_URL = "http://localhost:8000";


    /**
     * 尝试注册
     * @param user 用户对象
     * @param callback 异步网络请求回调
     */
    public static void doTryRegister(User user, Callback callback) {
        RegisterTryRequest request = new RegisterTryRequest(user.getEmail(), user.getPassword(), user.getNickName());


        HttpUtil.post(BASE_URL + "/grand/chain/register/try/", request.toString(), callback);
    }


    /**
     * 验证验证码
     * @param user 用户对象
     * @param verifyCode 验证码
     * @param callback 异步网络请求回调
     */
    public static void doConfirmRegister(User user, String verifyCode, Callback callback) {
        RegisterConfirmCodeRequest request = new RegisterConfirmCodeRequest(user.getEmail(), verifyCode);

        String body = request.toString();
        HttpUtil.post(BASE_URL + "/grand/chain/register/verify/", body, callback);
    }


    /**
     * 重新发送验证码
     * @param user 用户对象
     * @param callback 异步网络请求回调
     */
    public static void doResendCode(User user, Callback callback) {
        RegisterResendRequest request = new RegisterResendRequest(user.getEmail());


        HttpUtil.post(BASE_URL + "/grand/chain/register/send/", request.toString(), callback);
    }


    /**
     * 注册人脸信息（到百度云 ）
     * @param user 用户对象
     * @param imgBase64 拍摄的人脸base64
     * @param faceRegisterCallback 人脸信息回调
     */
    public static void doRegisterFace(User user, String imgBase64, FaceRegisterCallback faceRegisterCallback) {
        faceRegisterCallback.beforeRegister(user, imgBase64);

        String ft = FaceUtil.addUserFace(imgBase64, user.getUserId());

        faceRegisterCallback.afterRegister(ft);

    }

    /**
     * 插入人脸信息
     * @param user 用户对象
     * @param faceToken face_token
     * @param callback 网络请求回调
     */
    public static void doInsertFace(User user, String faceToken, Callback callback) {
        RegisterInsertFaceTokenRequest request = new RegisterInsertFaceTokenRequest(user.getEmail(),
                user.getUserId(),
                user.getSessionToken(),
                faceToken);


        HttpUtil.post(BASE_URL + "/grand/chain/register/face/insert/", request.toString(), callback);
    }


    /**
     * 登陆
     * @param user 用户对象
     * @param callback 网络请求回调
     */
    public static void doLogin(User user, Callback callback) {
        LoginRequest request = new LoginRequest(user.getEmail(), user.getPassword());

        HttpUtil.post(BASE_URL + "/grand/chain/login/", request.toString(), callback);

    }

    /**
     * 登出
     * @param user 用户对象
     * @param callback 网络请求回调
     */
    public static void doLogout(User user, Callback callback) {
        LogoutRequest request = new LogoutRequest(user.getEmail(), user.getUserId(), user.getSessionToken());
        String json = request.toString();
        HttpUtil.post(BASE_URL + "/grand/chain/logout/", json, callback);
    }


    /**
     * 充值
     * @param user 用户对象
     * @param tradeValue 交易额
     * @param callback 网络请求回调
     */
    public static void doCharge(User user, String tradeValue, Callback callback) {
        ChargeRequest request = new ChargeRequest(user.getEmail(), user.getUserId(), user.getSessionToken(), tradeValue);

        HttpUtil.post(BASE_URL + "/grand/chain/user/charge/", request.toString(), callback);
    }


    /**
     * 验证人脸信息 (with 百度云)
     * @param faceToken 认证标准
     * @param imageBase64 用户拍摄的人脸图像base64
     * @param faceVerifyCallback 执行后回调
     */
    public static void doVerifyFace(String faceToken, String imageBase64, FaceVerifyCallback faceVerifyCallback) {
        faceVerifyCallback.beforeVerify(faceToken, imageBase64);

        String result =  FaceUtil.verifyFace(faceToken, imageBase64);

        faceVerifyCallback.afterVerify(result);
    }


    /**
     * 得到用户的face_token
     * @param user 用户对象
     * @param callback 执行后回调
     */
    public static void doGetUserFaceToken(User user, Callback callback) {
        GetFaceTokenRequest req = new GetFaceTokenRequest(user.getEmail(), user.getUserId(), user.getSessionToken());

        HttpUtil.post(BASE_URL + "/grand/chain/user/get/facetoken/", req.toString(), callback);
    }


    /**
     * 发起转账
     * @param user 用户对象
     * @param tradeValue 交易额
     * @param faceToken 经过验证后的人脸信息
     * @param receiverUserEmail 接受者email
     * @param callback 网络请求回调
     */
    public static void doTransactionTrade(User user, String tradeValue, String faceToken, String receiverUserEmail, Callback callback) {

        TransactionTradeRequest request = new TransactionTradeRequest(user.getEmail(), user.getUserId(), user.getSessionToken(), receiverUserEmail, tradeValue, faceToken);

        HttpUtil.post(BASE_URL + "/grand/chain/trade/transfer/", request.toString(), callback);


    }

    /**
     * 转账接受
     * @param user 用户对象
     * @param orderId orderId
     * @param faceToken 验证过的人脸
     * @param callback 网络请求回调
     */
    public static void doTransactionConfirm(User user, String orderId, String faceToken, Callback callback) {

        TransactionConfirmRequest request = new TransactionConfirmRequest(user.getEmail(), user.getUserId(), user.getSessionToken(), orderId, faceToken);

        HttpUtil.post(BASE_URL + "/grand/chain/trade/receive/", request.toString(), callback);


    }


    /**
     * 交易记录查询
     * @param user 用户对象
     * @param callback 网络请求回调
     */
    public static void doTransactionInquire(User user, Callback callback) {
        TransactionInquireRequest request = new TransactionInquireRequest(user.getEmail(), user.getUserId(), user.getSessionToken());

        HttpUtil.post(BASE_URL + "/grand/chain/trade/inquire/", request.toString(), callback);
    }


    /**
     * 轮询未读转账信息
     * @param user 用户对象
     * @param callback 网络请求回调
     */
    public static void doTransactionGetUnread(User user, Callback callback) {
        GetUnreadRequest request = new GetUnreadRequest(user.getEmail(), user.getUserId(), user.getSessionToken());

        HttpUtil.post(BASE_URL + "/grand/chain/trade/unread/", request.toString(), callback);
    }
}
