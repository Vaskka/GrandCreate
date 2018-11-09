package com.example.zeyuzhang.myapplication.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.zeyuzhang.myapplication.R;
import com.vaskka.api.chain.user.entity.response.GetUnreadResponse;
import com.vaskka.frontend.adapter.ReceiveAdapter;
import com.vaskka.frontend.entity.Receive;
import com.vaskka.frontend.service.PollingService;
import com.vaskka.frontend.utils.UsualUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 展示待接受的收款信息
 */
public class FunctionActivity extends AppCompatActivity {

    private Button goTrade;

    private ListView listView;

    private List<Receive> receives = new ArrayList<>();

    private ReceiveAdapter receiveAdapter;

    private PollingService.DownLoadBinder pollingBinder;


    // 滑动监听
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        initView();
        initData();
        initAction();

        Intent bindIntent = new Intent(this, PollingService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }


    /**
     * 初始化控件view
     */
    private void initView() {
        goTrade = findViewById(R.id.main_show_go_trade);

        listView = findViewById(R.id.main_show_receive_list_view);

    }


    /**
     * 初始化list view 数据
     */
    private void initData() {
        try {
            Receive r1 = new Receive("tv1", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2018-1-1 22:33:20"), "123@email.com", "叫啊叫啊叫", "oid");
            Receive r2 = new Receive("tv2", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2018-1-1 22:33:20"), "123@email.com", "叫啊wqd叫啊叫", "oid");
            Receive r3 = new Receive("tv3", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2017-1-1 22:33:20"), "123@email.com", "r叫啊叫啊叫", "oid12");
            Receive r4 = new Receive("tv4", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2018-1-1 22:33:20"), "123@email.com", "叫啊叫啊ad叫", "oid34");
            Receive r5 = new Receive("tv5", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2019-1-1 22:33:20"), "123@email.com", "叫啊叫啊叫", "oid55");
            Receive r6 = new Receive("tv6", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2020-1-1 22:33:20"), "133@email.com", "叫啊叫asd啊叫", "oid6");

            receives.add(r1);
            receives.add(r2);
            receives.add(r3);
            receives.add(r4);
            receives.add(r5);
            receives.add(r6);

            receiveAdapter = new ReceiveAdapter(FunctionActivity.this, R.layout.item_receive, receives);
            listView.setAdapter(receiveAdapter);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化动作
     */
    private void initAction() {
        goTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发起转账
                Intent intent = new Intent(FunctionActivity.this,TransferActivity.class);
                startActivity(intent);
            }
        });

        // list view item 点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Receive receive = receives.get(position);
                SimpleDateFormat sf = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss", Locale.CHINA);

                Intent intent = new Intent(FunctionActivity.this, OkToTradeActivity.class);
                intent.putExtra("email", receive.getSender_email());
                intent.putExtra("nick_name", receive.getSender_nick_name());
                intent.putExtra("time", sf.format(receive.getDate()));
                intent.putExtra("value", receive.getTradeValue());
                intent.putExtra("order_id", receive.getOrderId());

                startActivity(intent);
            }
        });
    }


    /**
     * service 链接
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            pollingBinder = (PollingService.DownLoadBinder) service;

            PollingService s = pollingBinder.getService();

            s.setCallback(new PollingService.PollingCallback() {
                @Override
                public void getList(List<GetUnreadResponse.UnreadItem> list) {
                    // 接受list

                    receives.clear();

                    for (GetUnreadResponse.UnreadItem item : list) {
                        Receive receive = new Receive(item.getTrade_value(), UsualUtil.fromStringToDate(item.getCreate_time()), item.getSender_email(), item.getSender_nick_name(), item.getOrder_id());
                        receives.add(receive);
                    }

                    receiveAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();


            if(x2 - x1 > 50) {
                // 向右滑
                Intent intent = new Intent(this, MineActivity.class);
            }
        }
        return super.onTouchEvent(event);

    }
}
