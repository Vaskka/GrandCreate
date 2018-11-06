package com.vaskka.api.chain.user.entity.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: GrandCreateApiSdk
 * @description: GetUnreadResponse 轮询响应
 * @author: Vaskka
 * @create: 2018/11/6 11:18 AM
 **/

public class GetUnreadResponse extends BaseResponse {

    public class UnreadItem {
        private String create_time;

        private String sender_email;

        private String sender_nick_name;

        private String order_id;

        private String trade_value;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getSender_email() {
            return sender_email;
        }

        public void setSender_email(String sender_email) {
            this.sender_email = sender_email;
        }

        public String getSender_nick_name() {
            return sender_nick_name;
        }

        public void setSender_nick_name(String sender_nick_name) {
            this.sender_nick_name = sender_nick_name;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getTrade_value() {
            return trade_value;
        }

        public void setTrade_value(String trade_value) {
            this.trade_value = trade_value;
        }

        public UnreadItem(String create_time, String sender_email, String sender_nick_name, String order_id, String trade_value) {
            this.create_time = create_time;
            this.sender_email = sender_email;
            this.sender_nick_name = sender_nick_name;
            this.order_id = order_id;
            this.trade_value = trade_value;
        }
    }

    private List<UnreadItem> unread = new ArrayList<>();

    public List<UnreadItem> getUnread() {
        return unread;
    }

    public void setUnread(List<UnreadItem> unread) {
        this.unread = unread;
    }

    public GetUnreadResponse(List<UnreadItem> unread) {
        this.unread = unread;
    }

    public GetUnreadResponse(int code, String msg, List<UnreadItem> unread) {
        super(code, msg);
        this.unread = unread;
    }
}
