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

public class MineActivity extends AppCompatActivity {


    private TextView nickNameEditText;

    private TextView balanceEditText;

    private Button goInquire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
    }


    private void initView() {
        nickNameEditText = findViewById(R.id.mine_nick_name);
        balanceEditText = findViewById(R.id.mine_balance);
        goInquire = findViewById(R.id.mine_go_inquire);
    }


    private void initAction() {
        goInquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineActivity.this, TransferInquireActivity.class);
                startActivity(intent);
            }
        });
    }
}
