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
import com.vaskka.api.chain.user.entity.response.RegisterTryResponse;
import com.vaskka.api.chain.user.lib.ApiTool;
import com.vaskka.api.chain.user.lib.User;
import com.vaskka.frontend.utils.UsualUtil;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 尝试注册
 */
public class RegisterActivity extends AppCompatActivity {

    private Button goRegister;

    private Button goCancel;

    private EditText email;

    private EditText nickname;

    private EditText password;

    private EditText rePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initAction();

    }


    /**
     * 初始化控件
     */
    private void initView() {
        // 尝试注册
         goRegister = findViewById(R.id.register_try_to_register);


        // 取消返回上一activity
        goCancel = findViewById(R.id.register_try_to_cancel);

        // 输入框
        email = findViewById(R.id.register_try_email);
        nickname = findViewById(R.id.register_try_nick_name);
        password = findViewById(R.id.register_try_password);
        rePassword = findViewById(R.id.register_try_re_password);

    }

    /**
     * 初始化动作
     */
    private void initAction() {
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
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
     * 执行尝试注册API并根据返回信息进行判断逻辑
     */
    private void doRegister() {
        String emailString = email.getText().toString();

        String nicknameString = nickname.getText().toString();

        String passwordString = password.getText().toString();

        String rePasswordString = rePassword.getText().toString();

        // 保证确认密码正确
        if (!passwordString.equals(rePasswordString)) {
            UsualUtil.showWithToast(RegisterActivity.this, "两次输入密码不一致,请重试");

            password.setText("");
            rePassword.setText("");
            return;
        }

        // 构造User
        User.getInstance().setEmail(emailString);
        User.getInstance().setNickName(nicknameString);
        User.getInstance().setPassword(passwordString);

        final LoadingDialog ld = UsualUtil.showLoading(this, "加载中", "成功！" , "请重试");
        ApiTool.doTryRegister(User.getInstance(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                // 展示错误信息
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ld.close();
                        UsualUtil.showWithToast(RegisterActivity.this, "调用失败");
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final RegisterTryResponse res = (RegisterTryResponse) BaseResponse.load(response.body().string(), RegisterTryResponse.class);

                // 失败
                if (res.getCode() != 0) {

                    // 展示错误信息
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ld.close();
                            UsualUtil.showWithToast(RegisterActivity.this, res.getMsg());
                        }
                    });

                }
                else {
                    // 成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ld.close();
                        }
                    });


                    Intent intent = new Intent(RegisterActivity.this, CheckActivity.class);
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
