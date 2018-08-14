package com.vaskka.grand.user.response;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: User
 * @description: SearchUserResponseObject 搜索用户的响应类
 * @author: Vaskka
 * @create: 2018/8/14 下午12:49
 **/

public class SearchUserResponseObject extends BaseResponseObject {
    public class SearchUserInnerParam extends BaseInnerParam {

        public class InnerUser {
            private String email;

            private  String name;

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        List<InnerUser> result = new ArrayList<>();

    }


    public SearchUserResponseObject(String rawResponse) {
        this.rawResponse = rawResponse;

        inner = new Gson().fromJson(rawResponse, SearchUserInnerParam.class);

        if (inner.code == 0 ) {

            this.isOK = false;
        }

        this.result = inner.msg;
    }

    /**
     * 得到搜索到的用户结果列表
     * @return List&lt;SearchUserInnerParam.InnerUser&gt;
     */
    public List<SearchUserInnerParam.InnerUser> getSearchResultList() {
        return ((SearchUserInnerParam) inner).result;

    }


}
