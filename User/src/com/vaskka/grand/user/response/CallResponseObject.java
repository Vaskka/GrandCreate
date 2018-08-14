package com.vaskka.grand.user.response;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: User
 * @description: CallResponseObject 轮询Http响应
 * @author: Vaskka
 * @create: 2018/8/14 下午12:20
 **/

public class CallResponseObject extends BaseResponseObject {


    public class CallInnerParam extends BaseInnerParam {
        private String email;
        private String message_status;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMessage_status() {
            return message_status;
        }

        public void setMessage_status(String message_status) {
            this.message_status = message_status;
        }

        public String getFriend_status() {
            return friend_status;
        }

        public void setFriend_status(String friend_status) {
            this.friend_status = friend_status;
        }

        public List<InnerMessage> getMessage_active() {
            return message_active;
        }

        public void setMessage_active(List<InnerMessage> message_active) {
            this.message_active = message_active;
        }

        public List<InnerFriend> getNew_friends() {
            return new_friends;
        }

        public void setNew_friends(List<InnerFriend> new_friends) {
            this.new_friends = new_friends;
        }

        public class InnerMessage {
            private String sender_name;
            private String sender_email;

            public String getSender_name() {
                return sender_name;
            }

            public void setSender_name(String sender_name) {
                this.sender_name = sender_name;
            }

            public String getSender_email() {
                return sender_email;
            }

            public void setSender_email(String sender_email) {
                this.sender_email = sender_email;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            private String content;
            private String time;
        }

        String friend_status;

        public class InnerFriend {
            private String user_name;
            private String email;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }

        List<InnerMessage> message_active = new ArrayList<>();
        List<InnerFriend> new_friends = new ArrayList<>();
    }


    public CallResponseObject(String rawResponse) {
        this.rawResponse = rawResponse;
        this.inner = new Gson().fromJson(rawResponse, CallInnerParam.class);
        if (inner.code == 0) {
            isOK = true;
        }
        result = inner.msg;
    }

    /**
     * 得到新消息列表
     * @return List&lt;CallInnerParam.InnerMessage&gt;
     */
    public List<CallInnerParam.InnerMessage> getNewMessageList() {
        List<CallInnerParam.InnerMessage> result = ((CallInnerParam) inner).getMessage_active();
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }


    /**
     * 得到新的好友申请列表
     * @return List&lt;CallInnerParam.InnerFriend&gt;
     */
    public List<CallInnerParam.InnerFriend> getNewFriendJoinList() {
        List<CallInnerParam.InnerFriend> result = ((CallInnerParam) inner).getNew_friends();
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

}
