# 用户操作api

## 实现功能
整合项目后端的接口实现了全部用户操作的封装，实现了统一的数据格式
### 目前api包含的功能有：
+  用户注册
+  用户登陆
+  退出登陆
+  用户查找用户 (with user name)
+  用户尝试添加好友
+  用户对好友申请的验证 (通过or不通过)
+  好友之间互相发送消息 (后续可扩展成转账)
+  轮询 (包含收到的新消息和收到的好友申请)
###下一版本待实现
+  查看用户的好友列表
+  删除好友
+  解决头像的问题

## 使用示例

### 初始化用户对象

<pre>
// 第一个参数是用户的邮箱，第二个是密码，第三个是用户名。
// 同时email token userName 也提供了Getter和Setter
User u1 = new User("sdk1@qq.com", "2333", "test_sdk_1");
User u2 = new User("sdk2@qq.com", "2333", "test_sdk_2");
</pre>

### 用户操作
> 所有类似 userObject.do****(); 的方法返回值都是BaseResponseObject对象或继承BaseResponseObject的对象

#### BaseResponseObject及其子类
##### 公用接口

###### isOK() : boolean
返回请求是否成功

###### toString() : String
返回请求的提示信息, default:"This response is not ready"(说明请求尚未发生)

###### getRawResponse() : String
得到请求的原始返回字符串 (json)

##### 其他接口
> 一些BaseResponseObject 的子类用List<E> 对象返承载请求返回的信息。例如CallResponseObject, SearchUserResponseObject 详细内容见下方示例。

#### 用户注册

<pre>
    u1.doRegister(); 
    u2.doRegister();
</pre>

#### 用户登陆
<pre>
    u1.doLogin();
    u2.doLogin();
</pre>

##### 备注
登陆之后可以调用
User的getToken()方法获得作为身份验证的token

#### 用户搜索用户
<pre>
// 参数为要搜索用户的用户名
u1.doSearchUser("test_sdk_1");
</pre>
##### 备注
调用返回的SearchUserResponseObejct对象的getSearchResultList()方法可以获得找到的List<SearchUserResponseObject.SearchUserInnerParam.InnerUser> 对象

#### 尝试添加好友
<pre>
// 参数为要搜索用户的email
u1.doAddFriend(u2.getEmail())
</pre>

#### 发送消息
<pre>
// 第一个参数是接受者的email 第二个参数时发送的消息
u2.doSend(u1.getEmail(), "Hello Hello u1!")；
</pre>

#### 轮询
<pre>
CallResponseObject responseObject = u2.doCall();
// 获得新消息列表 (第一次获取后之后就不是新消息了)
List<CallResponseObject.CallInnerParam.InnerMessage> newMessages = responseObject.getNewMessageList();

// 获取好友申请列表 (只要不向后端发送同意或不同意就一直能获取到)
List<CallResponseObject.CallInnerParam.InnerFriend> newFriends = responseObject.getNewFriendJoinList();

</pre>

##### 备注
CallResponseObject.CallInnerParam.InnerMessage 对象和 CallResponseObject.CallInnerParam.InnerFriend 对象都有丰富的Getter和Setter接口在此不进行演示
