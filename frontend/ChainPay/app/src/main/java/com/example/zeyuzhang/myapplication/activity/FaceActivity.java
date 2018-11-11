package com.example.zeyuzhang.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.example.zeyuzhang.myapplication.R;
import com.oden.syd_camera.SydCameraActivity;
import com.oden.syd_camera.camera.CameraParaUtil;
import com.vaskka.api.chain.user.entity.response.BaseResponse;
import com.vaskka.api.chain.user.entity.response.RegisterInsertFaceTokenResponse;
import com.vaskka.api.chain.user.lib.ApiTool;
import com.vaskka.api.chain.user.lib.User;
import com.vaskka.api.chain.user.lib.face.FaceRegisterCallback;
import com.vaskka.api.chain.user.util.FaceUtil;
import com.vaskka.frontend.utils.ConstUtil;
import com.vaskka.frontend.utils.UsualUtil;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 插入人脸信息
 */
public class FaceActivity extends AppCompatActivity {

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);

        /* 开启摄像头 */
        /* 插入人脸数据 */
        initData();
        doInsert();
    }

    /**
     * 初始化视图
     */
    private void initData() {
        path = getIntent().getStringExtra("path");

    }

    /**
     * 执行人脸信息的插入
     */
    private void doInsert() {
        // 转动条
        final LoadingDialog ld = UsualUtil.showLoading(FaceActivity.this, "加载中", "成功！" , "请重试");

        // 百度云上注册人脸
        ApiTool.doRegisterFace(User.getInstance(), FaceUtil.imageToBase64ByLocal(path), new FaceRegisterCallback() {
            @Override
            public void beforeRegister(User user, String b64) {

            }

            @Override
            public void afterRegister(String resultFT) {
                if (resultFT == null) {

                    // 注册失败
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 错误提示重试
                                    ld.close();
                                    UsualUtil.showWithToast(FaceActivity.this, "注册失败，请重试");
                                    finish();
                                }
                            });

                        }
                    });

                }
                else {
                    // 注册成功
                    ApiTool.doInsertFace(User.getInstance(), resultFT, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ld.close();
                                    UsualUtil.showWithToast(FaceActivity.this, "网络错误，请重试");
                                    finish();
                                }
                            });

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            RegisterInsertFaceTokenResponse resp = (RegisterInsertFaceTokenResponse) BaseResponse.load(response.body().string(), RegisterInsertFaceTokenResponse.class);
                            if (resp.getCode() != 0 ){
                                // 服务器错误
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ld.close();
                                        UsualUtil.showWithToast(FaceActivity.this, "服务器错误，请重试");
                                        finish();
                                    }
                                });
                            }
                            else  {
                                // 调用成功
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ld.close();
                                        UsualUtil.showWithToast(FaceActivity.this, "注册成功");

                                        // 跳转主界面
                                        Intent intent = new Intent(FaceActivity.this, FunctionActivity.class);
                                        startActivity(intent);
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