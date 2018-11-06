package com.vaskka.api.chain.user;

import com.google.gson.Gson;
import com.vaskka.api.chain.user.entity.Face;
import com.vaskka.api.chain.user.entity.exception.ChainApiFaceRegisterException;
import com.vaskka.api.chain.user.entity.request.LogoutRequest;
import com.vaskka.api.chain.user.entity.request.TransactionConfirmRequest;
import com.vaskka.api.chain.user.entity.request.TransactionTradeRequest;
import com.vaskka.api.chain.user.entity.response.*;
import com.vaskka.api.chain.user.lib.ApiTool;
import com.vaskka.api.chain.user.lib.User;
import com.vaskka.api.chain.user.util.ConstUtil;
import com.vaskka.api.chain.user.util.FaceUtil;
import com.vaskka.api.chain.user.util.ZipUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONObject;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;

import static com.vaskka.api.chain.user.util.FaceUtil.addUserFace;
import static com.vaskka.api.chain.user.util.FaceUtil.verifyFace;

public class Main {

    public static void Log(Object o) {
        if (ConstUtil.DEBUG) {
            System.out.println(o);
        }
    }


    private static void test() {
        // 测试尝试开户

//        User.getInstance().setEmail("1139851358@qq.com");
//        User.getInstance().setNickName("Viskka");
//        User.getInstance().setPassword("thisispsw");
//        User.getInstance().setSessionToken("a5bb8fe2276cefbbd559fbefba680d57");
//        User.getInstance().setUserId("user7a3b6a103425b4ddee428ac63541");

        User.getInstance().setEmail("15145051056@163.com");
        User.getInstance().setNickName("Vaskka");
        User.getInstance().setPassword("thisispsw");
        User.getInstance().setSessionToken("41018c173245428b5b2824f252d8ed2f");
        User.getInstance().setUserId("userd13a9ac6b05eb814842fa23ca9dc");


//        ApiTool.doTryRegister(User.getInstance(), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log("尝试注册失败");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Gson gson = new Gson();
//                Log("尝试注册");
//                Log("messgae:");
//                RegisterTryResponse resp = gson.fromJson(response.body().string(), RegisterTryResponse.class);
//
//                Log(resp.getCode());
//                Log(resp.getMsg());
//            }
//        });

        // 测试验证码重新发送
//        ApiTool.doResendCode(User.getInstance(), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log("error");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                RegisterResendResponse resp = (RegisterResendResponse) BaseResponse.load(response.body().string(), RegisterResendResponse.class);
//
//                Log(resp.getCode());
//                Log(resp.getMsg());
//
//            }
//        });

        // 测试验证码验证
//        ApiTool.doConfirmRegister(User.getInstance(), "7736", new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log("error");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                RegisterConfirmCodeResponse resp = (RegisterConfirmCodeResponse) BaseResponse.load(response.body().string(), RegisterConfirmCodeResponse.class);
//
//                Log(resp.getCode());
//                Log(resp.getMsg());
//            }
//        });

        // 测试人脸数据录入
//        try {
//            ApiTool.doInsertFace(User.getInstance(), FaceUtil.imageToBase64ByLocal("/Users/vaskka/Desktop/another2.jpeg"), new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    RegisterInsertFaceTokenResponse r = (RegisterInsertFaceTokenResponse) BaseResponse.load(response.body().string(), RegisterInsertFaceTokenResponse.class);
//                    if (r.getCode() == 0) {
//                        Log("insert ok");
//                    }
//                    else {
//                        Log(response.body().string());
//                    }
//                }
//            });
//        } catch (ChainApiFaceRegisterException e) {
//            e.printStackTrace();
//        }
//
    // 测试登出
//        ApiTool.doLogout(User.getInstance(), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                LogoutResponse resp = (LogoutResponse) BaseResponse.load(response.body().string(), LogoutResponse.class);
//
//                if (resp.getCode() == 0) {
//                    Log("ok");
//                }
//                else {
//                    Log(response.body().string());
//                }
//            }
//        });
    // 测试登陆
//        ApiTool.doLogin(User.getInstance(), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                LoginResponse r = (LoginResponse) BaseResponse.load(response.body().string(), LoginResponse.class);
//
//                if (r.getCode() == 0 || r.getCode() == 201) {
//                    Log(r.getSession_token());
//                }
//            }
//        });

        // 测试充值
//        ApiTool.doCharge(User.getInstance(), "20000", new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                ChargeResponse r = (ChargeResponse) BaseResponse.load(response.body().string(), ChargeResponse.class);
//
//                if (r.getCode() == 0) {
//                    Log("ok");
//                }
//            }
//        });


        // 测试发起转账
//        ApiTool.doTransactionTrade(User.getInstance(), "5000", FaceUtil.imageToBase64ByLocal("/Users/vaskka/Desktop/test1.jpeg"), "15145051056@163.com", new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                //TransactionTradeResponse r = (TransactionTradeResponse) BaseResponse.load(response.body().string(), TransactionTradeResponse.class);
//                Log(response.body().string());
//            }
//        });
        // 测试收款
//        ApiTool.doTransactionConfirm(User.getInstance(), "order98bc974381e0a7a96baa5afbb78", FaceUtil.imageToBase64ByLocal("/Users/vaskka/Desktop/another.jpeg"), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                TransactionConfirmResponse r = (TransactionConfirmResponse) BaseResponse.load(response.body().string(), TransactionConfirmResponse.class);
//                Log(r.getBalance());
//            }
//        });
        // 测试查询
        ApiTool.doTransactionInquire(User.getInstance(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                TransactionInquireResponse r = (TransactionInquireResponse) TransactionInquireResponse.load(response.body().string(), TransactionInquireResponse.class);
                if (r.getCode() == 0) {
                    for (TransactionInquireResponse.RecordItem item : r.getRecord()) {
                        Log(item.getTime_stamp());
                        Log(item.getTrade_value());
                        Log(item.getType());
                        Log("==============");
                    }
                }
            }
        });
    }


    public static void main(String[] args) {
	    // write your code here
        if (ConstUtil.DEBUG) {
            test();
            //verifyFace("23f4eb1935899a6e53a745c467bafb77", FaceUtil.imageToBase64ByLocal("/Users/vaskka/Desktop/test2.jpeg"));
        }
//        String corr = FaceUtil.imageToBase64ByLocal("/Users/vaskka/Desktop/test2.jpeg");
//
//        String zi = ZipUtil.base64("/Users/vaskka/Desktop/test2.jpeg");
//        String uz = ZipUtil.decode(zi);
// 99.98509216
//        Log(corr.length());
//        Log(zi.length());
//        if (uz.equals(corr.replaceAll("\r|\n", ""))) {
//            Log("ok");
//        }

//        Face f = Face.getInstance();
//        f.initService();
//
//        JSONObject o = f.faceVerify("613d04cb298cb688628ebf97e5aa9334", "FACE_TOKEN", "43cbefc2b514a20b0a6d8ba12cb28955", "FACE_TOKEN");
//        Log(o.toString());
    }
}
