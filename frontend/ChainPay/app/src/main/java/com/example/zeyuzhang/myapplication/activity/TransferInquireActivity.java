package com.example.zeyuzhang.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.zeyuzhang.myapplication.R;
import com.vaskka.api.chain.user.entity.response.BaseResponse;
import com.vaskka.api.chain.user.entity.response.TransactionInquireResponse;
import com.vaskka.api.chain.user.lib.ApiTool;
import com.vaskka.api.chain.user.lib.User;
import com.vaskka.frontend.adapter.InquireAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TransferInquireActivity extends AppCompatActivity {

    private List<TransactionInquireResponse.RecordItem> list = new ArrayList<>();

    private InquireAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_inquire);

        initView();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        ListView listView = findViewById(R.id.transfer_inquire_list_view);
        adapter = new InquireAdapter(this, R.layout.item_inquire, list);
        listView.setAdapter(adapter);

        // 加载数据
        ApiTool.doTransactionInquire(User.getInstance(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                TransactionInquireResponse resp = (TransactionInquireResponse) BaseResponse.load(response.body().string(), TransactionInquireResponse.class);

                if (resp.getCode() == 0) {
                    list.clear();
                    list.addAll(resp.getRecord());

                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
