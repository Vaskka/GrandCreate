package com.vaskka.grand.user;

import com.vaskka.grand.user.request.*;
import com.vaskka.grand.user.response.BaseResponseObject;
import com.vaskka.grand.user.response.CallResponseObject;
import com.vaskka.grand.user.response.LoginResponseObject;
import com.vaskka.grand.user.response.SearchUserResponseObject;
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
    private static String BASE_URL = "http://localhost:8000";


    /**
     * 用户进行注册的方法
     * @return BaseResponseObject
     * @throws NullPointerException 有任意参数为null抛出
     */
    public BaseResponseObject doRegister() throws NullPointerException{
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

        String rawResponse = HttpUtil.post(BASE_URL + "/register/", registerObject);

        return new BaseResponseObject(rawResponse);

    }


    /**
     * 用户进行登陆
     * @return LoginResponse
     * @throws NullPointerException 有任意参数为null抛出
     **/
    public LoginResponseObject doLogin() throws NullPointerException {
        if (email == null) {
            throw new NullPointerException("email is null");
        }
        if (password == null) {
            throw new NullPointerException("password is null");
        }

        LoginRequestObject registerObject = new LoginRequestObject(email, password);

        String rawResponse = HttpUtil.post(BASE_URL + "/sign/", registerObject);

        LoginResponseObject result = new LoginResponseObject(rawResponse);

        if (result.isOK()) {
            this.token = result.getToken();
        }
        return result;
    }


    /**
     * 用户登出的操作
     * @return BaseResponseObject
     * @throws NullPointerException 有任意参数为null抛出
     */
    public BaseResponseObject doLogout() throws NullPointerException {
        if (email == null) {
            throw new NullPointerException("email is null");
        }
        if (token == null) {
            throw new NullPointerException("token is null");
        }

        LogoutRequestObject registerObject = new LogoutRequestObject(email, token);

        String rawResponse = HttpUtil.post(BASE_URL + "/logout/", registerObject);

        return new BaseResponseObject(rawResponse);

    }


    /**
     * 用户尝试添加好友的操作
     * @return BaseResponseObject
     * @throws NullPointerException 有任意参数为null抛出
     */
    public BaseResponseObject doAddFriend(String friendEmail) throws NullPointerException {
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

        String rawResponse = HttpUtil.post(BASE_URL + "/add_friend/", registerObject);

        return new BaseResponseObject(rawResponse);

    }


    /**
     * 搜索好友的操作
     * @param searchUserName 要是搜索的名字
     * @return SearchUserResponseObject
     * @throws NullPointerException 有任意参数为null抛出
     */
    public SearchUserResponseObject doSearchUser(String searchUserName) throws NullPointerException {
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

        String rawResponse = HttpUtil.get(BASE_URL + "/search_user/", params);

        return new SearchUserResponseObject(rawResponse);

    }


    /**
     * 是否确认添加这个好友
     * @param thisFriendEmail 这个好友的email
     * @param isOkToBeYourFriend 是否同意
     * @return BaseResponseObject
     * @throws NullPointerException 有任意参数为null抛出
     */
    public BaseResponseObject doAddFriendResult(String thisFriendEmail, boolean isOkToBeYourFriend) throws NullPointerException {
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

        String rawResponse = HttpUtil.get(BASE_URL + "/add_result/", params);

        return new BaseResponseObject(rawResponse);
    }


    /**
     * 向某个用户发消息
     * @param toEmail 接受人的email
     * @param content 消息内容
     * @return BaseResponseObject
     * @throws NullPointerException 有任意参数为null抛出
     */
    public BaseResponseObject doSend(String toEmail, String content) throws NullPointerException {
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

        String rawResponse = HttpUtil.post(BASE_URL + "/send/", obj);

        return new BaseResponseObject(rawResponse);
    }


    /**
     * 处理用户的轮询操作
     * @return CallResponseObject
     * @throws NullPointerException 有任意参数为null抛出
     */
    public CallResponseObject doCall() throws NullPointerException {
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

        String rawResponse = HttpUtil.get(BASE_URL + "/call/", params);

        return new CallResponseObject(rawResponse);

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
