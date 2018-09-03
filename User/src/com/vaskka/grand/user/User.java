package com.vaskka.grand.user;

import com.vaskka.grand.user.request.*;
import com.vaskka.grand.user.response.*;
import com.vaskka.grand.user.util.HttpCallBack;
import com.vaskka.grand.user.util.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: User
 * @description: User 用户操作相关功能
 * @author: Vaskka
 * @create: 2018/8/13 下午3:23
 **/

public class User {


    /**
     * 域名
     */
    private static String BASE_URL = "http://101.201.234.129";

    /**
     * 用户进行尝试注册的方法
     * @throws NullPointerException email 为null抛出
     */
    public void doTestRegister(HttpCallBack mCallBack) throws NullPointerException {
        if (email == null) {
            throw new NullPointerException("email is null");
        }

        BaseRequestObject testRegisterRequestObject = new BaseRequestObject(email);

        HttpUtil.post(BASE_URL + "/register/", testRegisterRequestObject.toString(), mCallBack);


    }


    /**
     * 用户进行注册的方法
     * @throws NullPointerException 有任意参数为null抛出
     */
    public void doRegister(HttpCallBack mCallBack) throws NullPointerException{
        if (email == null) {
            throw new NullPointerException("email is null");
        }
        if (userName == null) {
            throw new NullPointerException("user name is null");
        }
        if (password == null) {
            throw new NullPointerException("password is null");
        }

        RegisterRequestObject registerObject = new RegisterRequestObject(email, userName, password, "");

        HttpUtil.post(BASE_URL + "/register/ok/", registerObject.toString(), mCallBack);

    }


    /**
     * 用户进行登陆
     * @throws NullPointerException 有任意参数为null抛出
     **/
    public void doLogin(HttpCallBack mCallBack) throws NullPointerException {
        if (email == null) {
            throw new NullPointerException("email is null");
        }
        if (password == null) {
            throw new NullPointerException("password is null");
        }

        LoginRequestObject registerObject = new LoginRequestObject(email, password);

        HttpUtil.post(BASE_URL + "/sign/", registerObject.toString(), mCallBack);

    }


    /**
     * 用户登出的操作

     * @throws NullPointerException 有任意参数为null抛出
     */
    public void doLogout(HttpCallBack mCallBack) throws NullPointerException {
        if (email == null) {
            throw new NullPointerException("email is null");
        }
        if (token == null) {
            throw new NullPointerException("token is null");
        }

        LogoutRequestObject registerObject = new LogoutRequestObject(email, token);

        HttpUtil.post(BASE_URL + "/logout/", registerObject.toString(), mCallBack);


    }


    /**
     * 用户尝试添加好友的操作
     * @throws NullPointerException 有任意参数为null抛出
     */
    public void doAddFriend(String friendEmail, HttpCallBack mCallBack) throws NullPointerException {
        if (email == null) {
            throw new NullPointerException("email is null");
        }
        if (friendEmail == null) {
            throw new NullPointerException("friendEmail is null");
        }
        if (token == null) {
            throw new NullPointerException("token is null");
        }

        AddFriendRequestObject registerObject = new AddFriendRequestObject(email, token, friendEmail);

        HttpUtil.post(BASE_URL + "/add_friend/", registerObject.toString(), mCallBack);

    }


    /**
     * 搜索好友的操作
     * @param searchUserName 要是搜索的名字
     * @throws NullPointerException 有任意参数为null抛出
     */
    public void doSearchUser(String searchUserName, HttpCallBack mCallBack) throws NullPointerException {
        if (email == null) {
            throw new NullPointerException("email is null");
        }
        if (searchUserName == null) {
            throw new NullPointerException("search user name is null");
        }
        if (token == null) {
            throw new NullPointerException("token is null");
        }

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("token", token);
        params.put("search_name", searchUserName);

        HttpUtil.get(BASE_URL + "/search_user/", params, mCallBack);


    }


    /**
     * 是否确认添加这个好友
     * @param thisFriendEmail 这个好友的email
     * @param isOkToBeYourFriend 是否同意
     * @throws NullPointerException 有任意参数为null抛出
     */
    public void doAddFriendResult(String thisFriendEmail, boolean isOkToBeYourFriend, HttpCallBack mCallBack) throws NullPointerException {
        if (email == null) {
            throw new NullPointerException("email is null");
        }
        if (thisFriendEmail == null) {
            throw new NullPointerException("thisFriendEmail is null");
        }
        if (token == null) {
            throw new NullPointerException("token is null");
        }

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("token", token);
        params.put("join_email", thisFriendEmail);
        if (isOkToBeYourFriend) {
            params.put("result", "1");
        }
        else {
            params.put("result", "0");
        }

        HttpUtil.get(BASE_URL + "/add_result/", params, mCallBack);

    }


    /**
     * 向某个用户发消息
     * @param toEmail 接受人的email
     * @param content 消息内容
     * @throws NullPointerException 有任意参数为null抛出
     */
    public void doSend(String toEmail, String content, HttpCallBack mCallBack) throws NullPointerException {
        if (email == null) {
            throw new NullPointerException("email is null");
        }
        if (toEmail == null) {
            throw new NullPointerException("receiver email is null");
        }
        if (token == null) {
            throw new NullPointerException("token is null");
        }
        if (content == null) {
            throw new NullPointerException("message content is null");
        }

        SendMessageRequestObject obj = new SendMessageRequestObject(email, token, content, toEmail);

        HttpUtil.post(BASE_URL + "/send/", obj.toString(), mCallBack);

    }


    /**
     * 处理用户的轮询操作
     * @throws NullPointerException 有任意参数为null抛出
     */
    public void doCall(HttpCallBack mCallBack) throws NullPointerException {
        if (email == null) {
            throw new NullPointerException("email is null");
        }
        if (token == null) {
            throw new NullPointerException("token is null");
        }

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("token", token);
        params.put("request_time", sf.format(new Date()));

        HttpUtil.get(BASE_URL + "/call/", params, mCallBack);



    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String email;
    private String password;
    private String userName;

    public String getToken() {
        return token;
    }

    private String token;

    public User(String email, String password, String userName, String token) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.token = token;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
}
