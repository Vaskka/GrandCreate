package com.example.zeyuzhang.myapplication.activity;

import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

import com.example.zeyuzhang.myapplication.R;


/**
 * 欢迎页
 */
public class MainActivity extends AppCompatActivity {


    private Button goLogin;

    private Button goRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initAction();
    }


    private void initView() {
        goLogin = findViewById(R.id.welcome_login);
        goRegister = findViewById(R.id.welcome_register);
    }

    private void initAction() {
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
