package com.vaskka.grand.user;

import com.vaskka.grand.user.response.CallResponseObject;
import com.vaskka.grand.user.response.SearchUserResponseObject;

public class Main {

    public static void L(Object o) {

        System.out.println(o);
    }


    public static void main(String[] args) {
	// write your code here
//        User u1 = new User("sdk1@qq.com", "2333", "test_sdk_1");
//        User u2 = new User("sdk2@qq.com", "2333", "test_sdk_2");

        // test sdk
        // 注册 OK
//        L(u1.doRegister().isOK());
//        L(u2.doRegister().isOK());
        // 登陆 OK
//        u1.doLogin();
//        u2.doLogin();
//        L(u1.getToken());
//        L(u2.getToken());
        // 搜索用户
//        SearchUserResponseObject res = u1.doSearchUser("test_sdk_");
//        for (SearchUserResponseObject.SearchUserInnerParam.InnerUser user : res.getSearchResultList()) {
//            L("email:" + user.getEmail());
//            L("user name:" + user.getName());
//        }
        // 尝试添加好友
//        L(u1.doAddFriend(u2.getEmail()).isOK());

        // 轮询 OK
//        CallResponseObject res = u2.doCall();
//            // 展示新消息
//        for (CallResponseObject.CallInnerParam.InnerMessage msg : res.getNewMessageList()) {
//            L("sender_name:" + msg.getSender_name());
//            L("sender_email:" + msg.getSender_email());
//            L("content:" + msg.getContent());
//            L("time:" + msg.getTime());
//        }
//            // 展示新的好友申请
//        for (CallResponseObject.CallInnerParam.InnerFriend friend : res.getNewFriendJoinList()) {
//            L("user name:" + friend.getUser_name());
//            L("email:" + friend.getEmail());
//        }
        // 通过好友添加
//        L(u2.doAddFriendResult(u1.getEmail(), true).isOK());
        // 发送消息
//        L(u2.doSend(u1.getEmail(), "Hello Hello u1!"));
//        L(u1.doSend(u2.getEmail(), "Hello Hello u2"));
        // 轮询
//        CallResponseObject res = u2.doCall();
//            // 展示新消息
//        for (CallResponseObject.CallInnerParam.InnerMessage msg : res.getNewMessageList()) {
//            L("sender_name:" + msg.getSender_name());
//            L("sender_email:" + msg.getSender_email());
//            L("content:" + msg.getContent());
//            L("time:" + msg.getTime());
//        }
//            // 展示新的好友申请
//        for (CallResponseObject.CallInnerParam.InnerFriend friend : res.getNewFriendJoinList()) {
//            L("user name:" + friend.getUser_name());
//            L("email:" + friend.getEmail());
//        }
//        L("###############################");
//        res = u1.doCall();
                // 展示新消息
//        for (CallResponseObject.CallInnerParam.InnerMessage msg : res.getNewMessageList()) {
//            L("sender_name:" + msg.getSender_name());
//            L("sender_email:" + msg.getSender_email());
//            L("content:" + msg.getContent());
//            L("time:" + msg.getTime());
//        }
//              // 展示新的好友申请
//        for (CallResponseObject.CallInnerParam.InnerFriend friend : res.getNewFriendJoinList()) {
//            L("user name:" + friend.getUser_name());
//            L("email:" + friend.getEmail());
//        }
    }
}
