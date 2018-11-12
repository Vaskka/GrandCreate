package com.example.zeyuzhang.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zeyuzhang.myapplication.R;
import com.vaskka.api.chain.user.entity.response.BaseResponse;
import com.vaskka.api.chain.user.entity.response.ChargeResponse;
import com.vaskka.api.chain.user.lib.ApiTool;
import com.vaskka.api.chain.user.lib.User;
import com.vaskka.frontend.utils.UsualUtil;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChargeActivity extends AppCompatActivity {


    private EditText chargeNumber;

    private Button goCharge;

    private Button goCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        initView();
        initAction();
    }

    private void initView() {
        chargeNumber = findViewById(R.id.charge_number);
        goCancel = findViewById(R.id.charge_go_cancel);
        goCharge = findViewById(R.id.charge_go_charge);
    }

    private void initAction() {
        goCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = chargeNumber.getText().toString();
                try {
                    float test = Float.valueOf(number);

                    if (test < 0) {
                        throw new NumberFormatException("金额为负");
                    }

                    int real = (int )test * 100;

                    String realString = String.valueOf(real);

                    final LoadingDialog ld = UsualUtil.showLoading(ChargeActivity.this, "充值中", "", "");
                    ApiTool.doCharge(User.getInstance(), realString, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ld.close();
                                    UsualUtil.showWithToast(ChargeActivity.this, "网络错误，请重试");
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            ChargeResponse resp = (ChargeResponse) BaseResponse.load(response.body().string(), ChargeResponse.class);

                            final String show = UsualUtil.fromSmallBalanceGetNormalBalance(resp.getBalance());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ld.close();
                                    UsualUtil.showWithToast(ChargeActivity.this, "充值成功!\n" + "当前余额：" + show);
                                }
                            });


                        }
                    });
                }
                catch ( NumberFormatException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UsualUtil.showWithToast(ChargeActivity.this, "请输入合法的数字");
                        }
                    });

                }


            }
        });

        goCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
