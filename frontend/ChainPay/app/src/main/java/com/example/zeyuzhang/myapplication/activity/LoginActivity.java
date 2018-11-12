package com.example.zeyuzhang.myapplication.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zeyuzhang.myapplication.R;
import com.vaskka.api.chain.user.entity.response.BaseResponse;
import com.vaskka.api.chain.user.entity.response.LoginResponse;
import com.vaskka.api.chain.user.lib.ApiTool;
import com.vaskka.api.chain.user.lib.User;
import com.vaskka.frontend.utils.UsualUtil;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 登陆
 */
public class LoginActivity extends AppCompatActivity  {

    private Button goLogin;

    private Button goCancel;


    private EditText email;

    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initAction();

    }




    /**
     * 初始化控件
     */
    private void initView() {
        // 尝试注册
        goLogin = findViewById(R.id.login_to_login);


        // 取消返回上一activity
        goCancel = findViewById(R.id.login_to_cancel);

        // 邮箱输入框
        email = findViewById(R.id.login_email);

        // 密码输入框
        password = findViewById(R.id.login_password);

    }

    /**
     * 初始化动作
     */
    private void initAction() {
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            login();
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
     * 执行登陆
     * 读入用户输入信息，进行登陆操作
     */
    private void login() {
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();


        // 构造User
        User.getInstance().setEmail(emailString);
        User.getInstance().setPassword(passwordString);

        // 旋转等待
        final LoadingDialog ld = UsualUtil.showLoading(this, "加载中", "成功！" , "请重试");

        // 调用登陆api
        ApiTool.doLogin(User.getInstance(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ld.loadFailed();
                        ld.close();
                        UsualUtil.showWithToast(LoginActivity.this, "网络错误，请重试");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final LoginResponse resp = (LoginResponse) BaseResponse.load(response.body().string(), LoginResponse.class);

                // User储存信息
                User.getInstance().setLoginStatus(0);
                User.getInstance().setSessionToken(resp.getSession_token());
                User.getInstance().setUserId(resp.getUser_id());

                if (resp.getCode() != 0 && resp.getCode() != 201) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ld.close();
                            UsualUtil.showWithToast(LoginActivity.this, resp.getMsg());
                        }
                    });

                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ld.close();
                        }
                    });


                    Intent intent = new Intent(LoginActivity.this,FunctionActivity.class);
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
