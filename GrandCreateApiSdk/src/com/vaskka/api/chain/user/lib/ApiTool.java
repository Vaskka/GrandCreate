package com.vaskka.api.chain.user.lib;

import com.vaskka.api.chain.user.entity.request.*;
import com.vaskka.api.chain.user.util.HttpUtil;
import okhttp3.Callback;

/**
 * @program: GrandCreateApiSdk
 * @description: ApiTool 操作api的工具
 * @author: Vaskka
 * @create: 2018/10/31 10:03 PM
 **/

public class ApiTool {

    private static final String BASE_URL = "http://localhost:8000";


    public static void doTryRegister(User user, Callback callback) {
        RegisterTryRequest request = new RegisterTryRequest(user.getEmail(), user.getPassword(), user.getNickName());


        HttpUtil.post(BASE_URL + "/grand/chain/register/try/", request.toString(), callback);
    }

    public static void doConfirmRegister(User user, String verifyCode, Callback callback) {
        RegisterConfirmCodeRequest request = new RegisterConfirmCodeRequest(user.getEmail(), verifyCode);


        HttpUtil.post(BASE_URL + "/grand/chain/register/verify/", request.toString(), callback);
    }

    public static void doResendCode(User user, Callback callback) {
        RegisterResendRequest request = new RegisterResendRequest(user.getEmail());


        HttpUtil.post(BASE_URL + "/grand/chain/register/send/", request.toString(), callback);
    }

    public static void doInsertFace(User user, String faceToken, Callback callback) {
        RegisterInsertFaceTokenRequest request = new RegisterInsertFaceTokenRequest(user.getEmail(),
                user.getUserId(),
                user.getSessionToken(),
                faceToken);


        HttpUtil.post(BASE_URL + "/grand/chain/register/face/insert/", request.toString(), callback);
    }

    public static void doLogin(User user, Callback callback) {
        LoginRequest request = new LoginRequest(user.getEmail(), user.getPassword());

        HttpUtil.post(BASE_URL + "/grand/chain/login/", request.toString(), callback);

    }

    public static void doLogout(User user, Callback callback) {
        LogoutRequest request = new LogoutRequest(user.getUserId(), user.getSessionToken());

        HttpUtil.post(BASE_URL + "/grand/chain/logout/", request.toString(), callback);
    }

    public static void doCharge(User user, String tradeValue, Callback callback) {
        ChargeRequest request = new ChargeRequest(user.getEmail(), user.getUserId(), user.getSessionToken(), tradeValue);

        HttpUtil.post(BASE_URL + "/grand/chain/user/charge/", request.toString(), callback);
    }

    public static void doTransactionTrade(User user, String tradeValue, String faceToken, String receiverUserEmail, Callback callback) {
        TransactionTradeRequest request = new TransactionTradeRequest(user.getEmail(), user.getUserId(), user.getSessionToken(), receiverUserEmail, tradeValue, faceToken);

        HttpUtil.post(BASE_URL + "/grand/chain/trade/transfer/", request.toString(), callback);
    }

    public static void doTransactionConfirm(User user, String orderId, String faceToken, Callback callback) {
        TransactionConfirmRequest request = new TransactionConfirmRequest(user.getEmail(), user.getUserId(), user.getSessionToken(), orderId, faceToken);

        HttpUtil.post(BASE_URL + "/grand/chain/trade/receive/", request.toString(), callback);
    }

    public static void doTransactionInquire(User user, Callback callback) {
        TransactionInquireRequest request = new TransactionInquireRequest(user.getEmail(), user.getUserId(), user.getSessionToken());

        HttpUtil.post(BASE_URL + "/grand/chain/trade/inquire/", request.toString(), callback);
    }
}
