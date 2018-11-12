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
import com.vaskka.api.chain.user.entity.response.TransactionInquireResponse;
import com.vaskka.frontend.entity.Receive;
import com.vaskka.frontend.utils.UsualUtil;

import java.text.DateFormat;
import java.util.List;

public class InquireAdapter extends ArrayAdapter {

    private final int resourceId;

    public InquireAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        resourceId = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // 拿到实例
        TransactionInquireResponse.RecordItem item = (TransactionInquireResponse.RecordItem) getItem(position);

        // 实例View对象
        View view = LayoutInflater.from(this.getContext()).inflate(resourceId, null);

        // list item各种控件
        TextView time = view.findViewById(R.id.item_inquire_time);
        TextView kind = view.findViewById(R.id.item_inquire_kind);
        TextView value = view.findViewById(R.id.item_inquire_value);

        time.setText(item.getTime_stamp());
        if (item.getType() == 0) {
            kind.setText("转账");
        }
        else {
            kind.setText("收款");
        }
        String v = UsualUtil.fromSmallBalanceGetNormalBalance(item.getTrade_value());
        value.setText(v);

        return view;
    }
}
