package com.vaskka.api.chain.user.entity.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: GrandCreateApiSdk
 * @description: TransactionInquireResponse 转账查询响应
 * @author: Vaskka
 * @create: 2018/11/4 10:04 AM
 **/

public class TransactionInquireResponse extends BaseResponse {

    public class RecordItem {
        private String time_stamp;

        private String trade_value;

        private int type;

        public String getTime_stamp() {
            return time_stamp;
        }

        public void setTime_stamp(String time_stamp) {
            this.time_stamp = time_stamp;
        }

        public String getTrade_value() {
            return trade_value;
        }

        public void setTrade_value(String trade_value) {
            this.trade_value = trade_value;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public RecordItem(String time_stamp, String trade_value, int type) {
            this.time_stamp = time_stamp;
            this.trade_value = trade_value;
            this.type = type;
        }
    }

    List<RecordItem> record = new ArrayList<>();

    public List<RecordItem> getRecord() {
        return record;
    }

    public void setRecord(List<RecordItem> record) {
        this.record = record;
    }

    public TransactionInquireResponse(List<RecordItem> record) {
        this.record = record;
    }

    public TransactionInquireResponse(int code, String msg, List<RecordItem> record) {
        super(code, msg);
        this.record = record;
    }
}
