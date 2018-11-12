package com.example.zeyuzhang.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zeyuzhang.myapplication.R;
import com.vaskka.api.chain.user.lib.ApiTool;
import com.vaskka.api.chain.user.lib.User;

public class MineActivity extends AppCompatActivity {


    private TextView nickNameEditText;


    private Button goInquire;

    private Button goCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        initView();
        initAction();
        initData();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        nickNameEditText = findViewById(R.id.mine_nick_name);
        goInquire = findViewById(R.id.mine_go_inquire);
        goCharge = findViewById(R.id.mine_go_charge);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        nickNameEditText.setText(User.getInstance().getEmail());
    }

    /**
     * 初始化动作
     */
    private void initAction() {
        goInquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineActivity.this, TransferInquireActivity.class);
                startActivity(intent);
            }
        });

        goCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineActivity.this, ChargeActivity.class);
                startActivity(intent);
            }
        });
    }
}
