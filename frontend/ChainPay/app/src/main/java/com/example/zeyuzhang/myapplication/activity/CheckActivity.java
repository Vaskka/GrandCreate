package com.example.zeyuzhang.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

import com.example.zeyuzhang.myapplication.R;
import com.vaskka.api.chain.user.entity.response.BaseResponse;
import com.vaskka.api.chain.user.entity.response.RegisterConfirmCodeResponse;
import com.vaskka.api.chain.user.lib.ApiTool;
import com.vaskka.api.chain.user.lib.User;
import com.vaskka.frontend.utils.UsualUtil;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 验证码确认
 */
public class CheckActivity extends AppCompatActivity {


    private Button goRegister;

    private Button goCancel;

    private EditText checkEmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        initView();
        initAction();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 进行验证
        goRegister = findViewById(R.id.register_confirm_to_register);


        // 取消返回上一activity
        goCancel = findViewById(R.id.register_confirm_to_cancel);

        // 验证码
        checkEmailEditText.findViewById(R.id.register_confirm_verify_code);

    }

    /**
     * 初始化动作
     */
    private void initAction() {
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheckEmail();
            }
        });

        goCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    /**
     * 验证邮箱
     * 负责接收用户的输入并调用sdk执行响应功能
     */
    private void doCheckEmail() {
        String emailCode = checkEmailEditText.getText().toString();

        final LoadingDialog ld = UsualUtil.showLoading(CheckActivity.this, "加载中", "注册成功！", "请重试");

        ApiTool.doConfirmRegister(User.getInstance(), emailCode, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    ld.loadFailed();
                    ld.close();

                    UsualUtil.showWithToast(CheckActivity.this, "网络错误，请重试");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final RegisterConfirmCodeResponse resp = (RegisterConfirmCodeResponse) BaseResponse.load(response.body().string(), RegisterConfirmCodeResponse.class);

                // 验证失败
                if (resp.getCode() != 0) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        ld.loadFailed();
                        ld.close();
                        UsualUtil.showWithToast(CheckActivity.this, resp.getMsg());
                        }
                    });

                }
                else {
                    // 验证成功
                    // 更新用户信息
                    User.getInstance().setUserId(resp.getUser_id());
                    User.getInstance().setSessionToken(resp.getSession_token());
                    User.getInstance().setLoginStatus(0);

                    // 展示成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ld.loadSuccess();
                            ld.close();
                        }
                    });


                    Intent intent = new Intent(CheckActivity.this, FaceActivity.class);
                    startActivity(intent);
                }


            }
        });

    }


}
