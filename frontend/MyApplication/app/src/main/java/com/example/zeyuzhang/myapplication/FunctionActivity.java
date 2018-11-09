package com.example.zeyuzhang.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class FunctionActivity extends AppCompatActivity  {


    private String[] data = {"Apple","Banana","Orange","Watermelon","Pear","Grape",
            "Pineapple","Strawberry","Cherry","Mange",
            "Apple","Banana","Orange","Watermelon","Pear","Grape",
            "Pineapple","Strawberry","Cherry","Mange"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                FunctionActivity.this, android.R.layout.simple_list_item_1,data);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //跳转至收款界面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FunctionActivity.this,ReceiveActivity.class);
                startActivity(intent);
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FunctionActivity.this,TransferActivity.class);
                startActivity(intent);
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FunctionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

}
