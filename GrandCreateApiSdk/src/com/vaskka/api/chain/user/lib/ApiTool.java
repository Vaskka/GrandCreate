package com.vaskka.api.chain.user.lib;

import com.vaskka.api.chain.user.entity.exception.ChainApiFaceRegisterException;
import com.vaskka.api.chain.user.entity.request.*;
import com.vaskka.api.chain.user.entity.response.BaseResponse;
import com.vaskka.api.chain.user.entity.response.GetFaceTokenResponse;
import com.vaskka.api.chain.user.util.FaceUtil;
import com.vaskka.api.chain.user.util.HttpUtil;
import com.vaskka.api.chain.user.util.ZipUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

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

        String body = request.toString();
        HttpUtil.post(BASE_URL + "/grand/chain/register/verify/", body, callback);
    }

    public static void doResendCode(User user, Callback callback) {
        RegisterResendRequest request = new RegisterResendRequest(user.getEmail());


        HttpUtil.post(BASE_URL + "/grand/chain/register/send/", request.toString(), callback);
    }

    public static void doInsertFace(User user, String imgBase64, Callback callback) throws ChainApiFaceRegisterException {
        String ft = FaceUtil.addUserFace(imgBase64, user.getUserId());

        if (ft != null) {
            doInnerInsertFace(user, ft, callback);
        }
        else {
            throw new ChainApiFaceRegisterException("call face api register error\n", -1);
        }
    }

    private  static void doInnerInsertFace(User user, String faceToken, Callback callback) {
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
        LogoutRequest request = new LogoutRequest(user.getEmail(), user.getUserId(), user.getSessionToken());
        String json = request.toString();
        HttpUtil.post(BASE_URL + "/grand/chain/logout/", json, callback);
    }

    public static void doCharge(User user, String tradeValue, Callback callback) {
        ChargeRequest request = new ChargeRequest(user.getEmail(), user.getUserId(), user.getSessionToken(), tradeValue);

        HttpUtil.post(BASE_URL + "/grand/chain/user/charge/", request.toString(), callback);
    }

    public static void doTransactionTrade(User user, String tradeValue, String imgBase64, String receiverUserEmail, Callback callback) {

        GetFaceTokenRequest req = new GetFaceTokenRequest(user.getEmail(), user.getUserId(), user.getSessionToken());

        HttpUtil.post(BASE_URL + "/grand/chain/user/get/facetoken/", req.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                GetFaceTokenResponse r = (GetFaceTokenResponse) BaseResponse.load(response.body().string(), GetFaceTokenResponse.class);
                String result_ft = FaceUtil.verifyFace(r.getFace_token(), imgBase64);
                if (result_ft != null && r.getCode() == 0) {

                    TransactionTradeRequest request = new TransactionTradeRequest(user.getEmail(), user.getUserId(), user.getSessionToken(), receiverUserEmail, tradeValue, result_ft);

                    HttpUtil.post(BASE_URL + "/grand/chain/trade/transfer/", request.toString(), callback);
                }
                else {
                    throw new IOException("network error");
                }
            }
        });


    }

    public static void doTransactionConfirm(User user, String orderId, String imgBase64, Callback callback) {

        GetFaceTokenRequest req = new GetFaceTokenRequest(user.getEmail(), user.getUserId(), user.getSessionToken());

        HttpUtil.post(BASE_URL + "/grand/chain/user/get/facetoken/", req.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                GetFaceTokenResponse r = (GetFaceTokenResponse) BaseResponse.load(response.body().string(), GetFaceTokenResponse.class);

                String result_ft = FaceUtil.verifyFace(r.getFace_token(), imgBase64);
                if (result_ft != null && r.getCode() == 0) {
                    TransactionConfirmRequest request = new TransactionConfirmRequest(user.getEmail(), user.getUserId(), user.getSessionToken(), orderId, result_ft);

                    HttpUtil.post(BASE_URL + "/grand/chain/trade/receive/", request.toString(), callback);
                }
                else {
                    throw new IOException("network error");
                }
            }
        });


    }

    public static void doTransactionInquire(User user, Callback callback) {
        TransactionInquireRequest request = new TransactionInquireRequest(user.getEmail(), user.getUserId(), user.getSessionToken());

        HttpUtil.post(BASE_URL + "/grand/chain/trade/inquire/", request.toString(), callback);
    }

    public static void doTransactionGetUnread(User user, Callback callback) {
        GetUnreadRequest request = new GetUnreadRequest(user.getEmail(), user.getUserId(), user.getSessionToken());

        HttpUtil.post(BASE_URL + "/grand/chain/trade/unread/", request.toString(), callback);
    }
}
