package com.vaskka.api.chain.user.lib;

/**
 * @program: GrandCreateApiSdk
 * @description: User 主要操作api的User类
 * @author: Vaskka
 * @create: 2018/10/31 9:56 PM
 **/

public class User {

    private String userId;

    private String email;

    private String nickName;

    private String password;

    /**
     * 0-已登陆 -1-未注册 1-已登陆
     */
    private int loginStatus;

    private String sessionToken;

    private static User mainUser;


//    public User(String userId, String email, String nickName, String password, int loginStatus) {
//        this.userId = userId;
//        this.email = email;
//        this.nickName = nickName;
//        this.password = password;
//        this.loginStatus = loginStatus;
//    }

    private User() {

    }

    public static User getInstance() {
        if (mainUser == null) {
            mainUser = new User();
        }

        return mainUser;
    }

    /* getter and setter */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
