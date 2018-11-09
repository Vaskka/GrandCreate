package com.example.zeyuzhang.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zeyuzhang.myapplication.R;
import com.oden.syd_camera.SydCameraActivity;
import com.oden.syd_camera.camera.CameraParaUtil;
import com.vaskka.frontend.utils.UsualUtil;

import  com.vaskka.frontend.utils.ConstUtil;


/**
 * 进行转账
 */
public class TransferActivity extends AppCompatActivity  {

    private Button doTrade;

    private Button doCancel;

    private EditText emailEditText;

    private EditText valueEditText;

    private static String email;
    private static String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        initView();
        initAction();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        doTrade = findViewById(R.id.do_trade_go);
        doCancel = findViewById(R.id.do_trade_cancel);
        emailEditText = findViewById(R.id.do_trade_receiver_email);
        valueEditText = findViewById(R.id.do_trade_value);
    }

    /**
     * 初始化动作
     */
    private void initAction() {
        doTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 发起转账 */
                goTrade();

            }
        });

        doCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 发起转账
     */
    private void goTrade() {
        email = emailEditText.getText().toString();
        value = valueEditText.getText().toString();

        // 验证人脸信息

        // 拍摄
        Intent intent = new Intent(TransferActivity.this, SydCameraActivity.class);
        intent.putExtra(CameraParaUtil.picQuality, 100); //图片质量0~100
        startActivityForResult(intent, ConstUtil.CAMERA_CODE);

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

            Intent intent = new Intent(TransferActivity.this, FaceTransferActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("value", value);
            intent.putExtra("path", picturePath);
            startActivity(intent);

        }

    }
}
