package com.example.zeyuzhang.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zeyuzhang.myapplication.R;
import com.vaskka.api.chain.user.entity.response.BaseResponse;
import com.vaskka.api.chain.user.entity.response.GetFaceTokenResponse;
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
 * 发起交易时验证人脸信息
 */
public class FaceTransferActivity extends AppCompatActivity  {

    private Button ok;

    private Button cancel;

    private ImageView imageView;


    private String email;

    private String value;

    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_transfer);

        initView();
        initData();
        initAction();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();

        email = intent.getStringExtra("email");
        value = intent.getStringExtra("value");
        path  = intent.getStringExtra("path");

        imageView.setImageBitmap(BitmapFactory.decodeFile(path));

    }


    /**
     * 初始化视图
     */
    private void initView() {
        ok = findViewById(R.id.trade_transaction_face_to_ok);
        cancel = findViewById(R.id.trade_transaction_face_to_cancel);

        imageView = findViewById(R.id.trade_transaction_face_image_view);
    }


    /**
     * 初始化动作
     */
    private void initAction() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            final LoadingDialog ldFace = UsualUtil.showLoading(FaceTransferActivity.this, "加载中", "成功！" , "请重试");

            // 拿到用户人脸信息
            ApiTool.doGetUserFaceToken(User.getInstance(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ldFace.close();
                            UsualUtil.showWithToast(FaceTransferActivity.this, "网络错误，请重试");
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
                                ldFace.close();
                                UsualUtil.showWithToast(FaceTransferActivity.this, "服务器出错，请重试");
                            }
                        });
                    }
                    else {
                        // 拿到了标准face_token
                        String faceToken = resp.getFace_token();
                        String imageBase64 = FaceUtil.imageToBase64ByLocal(path);

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
                                            ldFace.close();
                                            UsualUtil.showInfoInDialog(FaceTransferActivity.this, "验证失败", "验证失败请重新拍摄，请重试", new DialogInterface.OnClickListener() {
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
                                    // 进行转账
                                    ApiTool.doTransactionTrade(User.getInstance(), value, s, email, new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {

                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            final TransactionTradeResponse resp = (TransactionTradeResponse) BaseResponse.load(response.body().string(), TransactionTradeResponse.class);

                                            if (resp.getCode() != 0) {

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                    ldFace.close();
                                                    UsualUtil.showWithToast(FaceTransferActivity.this, resp.getMsg());
                                                    finish();
                                                    }
                                                });

                                            }
                                            else {

                                                // 转帐成功
                                                final String balanceString = "转账成功\n余额：" + UsualUtil.fromSmallBalanceGetNormalBalance(resp.getBalance()) + "\n等待收款用户确认收款...";
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ldFace.close();

                                                        UsualUtil.showInfoInDialog(FaceTransferActivity.this, "转账成功", balanceString, new DialogInterface.OnClickListener() {
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
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
