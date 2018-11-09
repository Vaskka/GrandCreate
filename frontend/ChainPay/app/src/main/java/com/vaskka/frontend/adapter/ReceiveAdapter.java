package com.vaskka.frontend.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zeyuzhang.myapplication.R;
import com.vaskka.frontend.entity.Receive;

import java.text.DateFormat;
import java.util.List;

public class ReceiveAdapter extends ArrayAdapter {

    private final int resourceId;

    public ReceiveAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);

        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 拿到实例
        Receive receive = (Receive) getItem(position);

        // 实例View对象
        View view = LayoutInflater.from(this.getContext()).inflate(resourceId, null);

        // list item各种控件
        TextView senderNickName = view.findViewById(R.id.item_receive_nick_name);
        TextView date = view.findViewById(R.id.item_receive_date);

        String showNickName = "转账人:" + receive.getSender_nick_name();
        senderNickName.setText(showNickName);
        date.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(receive.getDate()));

        return view;
    }
}
