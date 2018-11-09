package com.example.zeyuzhang.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zeyuzhang.myapplication.R;
import com.oden.syd_camera.SydCameraActivity;
import com.oden.syd_camera.camera.CameraParaUtil;
import com.vaskka.frontend.utils.ConstUtil;
import com.vaskka.frontend.utils.UsualUtil;

/**
 * 提示用户即将插入人脸信息
 */
public class ReadyToInsertFace extends AppCompatActivity {


    private Button goCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_to_insert_face);

        initView();
        initAction();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        goCamera = findViewById(R.id.register_insert_go_camera);
    }

    /**
     * 初始化数据
     */
    private void initAction() {
        goCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开始拍摄
                Intent intent = new Intent(ReadyToInsertFace.this, SydCameraActivity.class);
                intent.putExtra(CameraParaUtil.picQuality, 100); //图片质量0~100
                startActivityForResult(intent, ConstUtil.CAMERA_CODE);
            }
        });
    }


    /**
     * 结束拍摄
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
            // 获取图片路径
            String picturePath;
            picturePath = data.getStringExtra(CameraParaUtil.picturePath);

            Intent intent = new Intent(ReadyToInsertFace.this, FaceActivity.class);
            intent.putExtra("path", picturePath);
            startActivity(intent);

        }
    }
}
