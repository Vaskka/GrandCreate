package com.example.zeyuzhang.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zeyuzhang.myapplication.R;
import com.oden.syd_camera.SydCameraActivity;
import com.oden.syd_camera.camera.CameraParaUtil;
import com.vaskka.frontend.utils.ConstUtil;
import com.vaskka.frontend.utils.UsualUtil;

/**
 * 确认收款
 */
public class OkToTradeActivity extends AppCompatActivity {

    private Button goToVerify;

    private TextView emailText;

    private TextView timeText;

    private TextView nickNameText;

    private TextView valueText;

    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_to_trade);

        initView();
        initData();
        initAction();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        goToVerify = findViewById(R.id.ok_to_trade_confirm);
        emailText = findViewById(R.id.ok_to_trade_email_hold);
        timeText = findViewById(R.id.ok_to_trade_time_hold);
        nickNameText = findViewById(R.id.ok_to_trade_sender_hold);
        valueText = findViewById(R.id.ok_to_trade_value_hold);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();

        String email = intent.getStringExtra("email");
        String time = intent.getStringExtra("time");
        String nickName = intent.getStringExtra("nick_name");
        String value = intent.getStringExtra("value");

        orderId = intent.getStringExtra("order_id");

        emailText.setText(email);
        timeText.setText(time);
        nickNameText.setText(nickName);
        valueText.setText(value);
    }


    /**
     * 初始化动作
     */
    private void initAction() {
        goToVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /* 验证人脸 */
            // 拍摄
            Intent intent = new Intent(OkToTradeActivity.this, SydCameraActivity.class);
            intent.putExtra(CameraParaUtil.picQuality, 100); //图片质量0~100
            startActivityForResult(intent, ConstUtil.CAMERA_CODE);
            }
        });
    }

    /**
     * 获拍照结果
     * @param requestCode 请求码
     * @param resultCode 返回码
     * @param data 数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK){
            UsualUtil.showWithToast(this, "拍摄失败，请重试");
            return;
        }

        if (requestCode == ConstUtil.CAMERA_CODE) {
            String picturePath;
            picturePath = data.getStringExtra(CameraParaUtil.picturePath);

            Intent intent = new Intent(OkToTradeActivity.this, ReceiveActivity.class);
            intent.putExtra("order_id", orderId);
            intent.putExtra("path", picturePath);
            startActivity(intent);

        }

    }


}
