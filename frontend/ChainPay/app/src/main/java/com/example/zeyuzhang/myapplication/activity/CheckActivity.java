package com.example.zeyuzhang.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
        checkEmailEditText = findViewById(R.id.register_confirm_verify_code);

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
                            ld.close();
                        }
                    });


                    Intent intent = new Intent(CheckActivity.this, ReadyToInsertFace.class);
                    startActivity(intent);
                }


            }
        });

    }

    @Override
    public boolean dispatchTouchEvent (MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            }
            else {
                return true;
            }
        }
        return false;
    }



}
