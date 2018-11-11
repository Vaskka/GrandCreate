package com.example.zeyuzhang.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.example.zeyuzhang.myapplication.R;
import com.vaskka.api.chain.user.entity.response.BaseResponse;
import com.vaskka.api.chain.user.entity.response.GetFaceTokenResponse;
import com.vaskka.api.chain.user.entity.response.TransactionConfirmResponse;
import com.vaskka.api.chain.user.entity.response.TransactionTradeResponse;
import com.vaskka.api.chain.user.lib.ApiTool;
import com.vaskka.api.chain.user.lib.User;
import com.vaskka.api.chain.user.lib.face.FaceVerifyCallback;
import com.vaskka.api.chain.user.util.FaceUtil;
import com.vaskka.frontend.utils.UsualUtil;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 收款时人脸验证
 */
public class ReceiveActivity extends AppCompatActivity {


    private String orderId;

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);


        //***************************
        //人脸识别验证
        //
        //
        //***************************
        initData();
        doCheck();
    }


    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();

        orderId = intent.getStringExtra("order_id");
        path = intent.getStringExtra("path");
    }

    /**
     * 读取上一Activity读入的图片信息并执行人脸识别API并调用确认收款API
     */
    private void doCheck() {
        // 转动条
        final LoadingDialog ld = UsualUtil.showLoading(ReceiveActivity.this, "加载中", "成功！" , "请重试");

        // 拿到用户人脸信息
        ApiTool.doGetUserFaceToken(User.getInstance(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    ld.close();
                    UsualUtil.showWithToast(ReceiveActivity.this, "网络错误，请重试");
                    finish();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                GetFaceTokenResponse resp = (GetFaceTokenResponse) BaseResponse.load(response.body().string(), GetFaceTokenResponse.class);

                if (resp.getCode() != 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        ld.close();
                        UsualUtil.showWithToast(ReceiveActivity.this, "服务器出错，请重试");
                        }
                    });
                }
                else {
                    // 拿到了标准face_token
                    final String faceToken = resp.getFace_token();
                    String imageBase64 = FaceUtil.imageToBase64ByLocal(path);

                    // 百度云验证人脸
                    ApiTool.doVerifyFace(faceToken, imageBase64, new FaceVerifyCallback() {
                        @Override
                        public void beforeVerify(String ft, String ib64) {

                        }

                        @Override
                        public void afterVerify(String s) {
                            // 百度云api调用后
                            // 验证结果
                            if (s == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                    ld.close();
                                    UsualUtil.showInfoInDialog(ReceiveActivity.this, "验证失败", "验证失败请重新拍摄，请重试", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 失败直接结束activity
                                            finish();
                                        }
                                    });
                                    }
                                });
                            }
                            else {
                                // 进行收款
                                ApiTool.doTransactionConfirm(User.getInstance(), orderId, faceToken, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                            ld.close();
                                            UsualUtil.showWithToast(ReceiveActivity.this, "网络错误，请重试");
                                            finish();

                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        final TransactionConfirmResponse resp = (TransactionConfirmResponse) BaseResponse.load(response.body().string(), TransactionConfirmResponse.class);

                                        if (resp.getCode() != 0) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ld.close();
                                                    UsualUtil.showWithToast(ReceiveActivity.this, resp.getMsg());
                                                    finish();
                                                }
                                            });
                                        }
                                        else {
                                            // 执行成功
                                            final String balanceString = "收款成功!\n账户余额：" + UsualUtil.fromSmallBalanceGetNormalBalance(resp.getBalance()) + "\n";
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                ld.close();

                                                UsualUtil.showInfoInDialog(ReceiveActivity.this, "转账成功", balanceString, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    }
                                                });
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }
}
