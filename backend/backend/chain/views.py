import datetime
import json
import random

from django.http import HttpResponse

# Create your views here.
from chain.models import Talk, User, UserSession, FriendApply, FriendMapping
from utils.request import QueryRequest
from utils.util import md5


def get_is_email_in_user_session(email):
    """
    检查email是否在user-session表中
    :param email: email
    :return: bool
    """
    test_query = UserSession.objects.filter(user__email=email)
    if test_query.exists():
        return True
        pass
    else:
        return False
    pass


def get_is_token_correctly(email, token):
    """
    检查token是否正确
    :param email: email
    :param token: token
    :return: bool
    """
    test_query = UserSession.objects.filter(user__email=email).filter(token=token)
    if test_query.exists():
        return True
        pass
    else:
        return False
        pass
    pass


def get_is_email_exist(email):
    """
    获取email是否重复
    :param email: 待验证email
    :return: bool
    """
    if User.objects.filter(email=email).exists():
        return True
        pass
    return False
    pass


def get_is_user_session_exist(user_id):
    """
    获取用户是否已经登陆
    :param user_id: 用户主键
    :return: bool
    """
    if UserSession.objects.filter(user__id=user_id).exists():
        return True
        pass
    return False
    pass


def get_auth(email, token):
    """
    验证身份 (token = UserSession token)
    :param email: email
    :param token: token
    :return:
    """
    try:
        main_token = UserSession.objects.get(user__email=email).token
        #
        # psw = User.objects.get(pk=user_id).password
        if token == main_token:
            return True
        return False
        pass
    except Exception as e:
        return False

    pass


def get_request_params(query_dict):
    """
    构造请求对象
    :param query_dict: 请求字典
    :return: 请求对象
    """
    return QueryRequest(query_dict)
    pass


def get_error_response(request_obj):
    """
    得到error结果的json
    :param request_obj: 请求对象
    :return: json str
    """
    response = dict()

    response["code"] = 1
    response["msg"] = "error"

    return json.dumps(response)
    pass


def get_new_message(request_obj):
    """
    检查是否无新信息或好友申请
    :param request_obj: 请求对象
    :return: 相应的dict

    """
    # 检查有无新信息
    result = Talk.objects.filter(send__email=request_obj.email).filter(is_new=True)
    data = dict()
    if result.exists():
        data["message_status"] = "Active"
        message_list = list()
        for message in result:
            content_message = dict()
            content_message["sender_name"] = message.send.user_name
            content_message["sender_email"] = message.send.email
            content_message["content"] = message.content
            content_message["time"] = message.create_time
            message_list.append(content_message)
            pass

        data["message_active"] = message_list
        pass
    else:
        data["message_status"] = "None"

    # 检查有无新的好友申请
    result = FriendApply.objects.filter(receiver__email=request_obj.email)
    if result.exists():
        data["friend_status"] = "Active"
        friend_apply_list = list()
        for friend in result:
            content_friend = dict()
            content_friend["email"] = friend.sender.email
            content_friend["user_name"] = friend.sender.user_name
            friend_apply_list.append(content_friend)
            pass

        data["new_friends"] = friend_apply_list
        pass
    else:
        data["friend_status"] = "None"
        pass

    return data
    pass


def get_response_general_param(request_obj, response):
    """
    为返回的dict类型添加通用属性
    :param request_obj: 请求对象
    :param response: 需要改造的dict
    :return: dict
    """
    response["code"] = 0
    response["msg"] = "success"
    response["email"] = request_obj.email
    return response
    pass


def deal_user_call(request):
    """
    处理某个用户的轮询
    :param request:
    :return: json HttpResponse
    """
    request_obj = get_request_params(request.GET)
    # 验证身份
    if not get_auth(request_obj.email, request_obj.token):
        return HttpResponse(get_error_response(request_obj))

    # 拿到未读情况和好友申请
    response = get_new_message(request_obj)

    # 添加通用参数
    get_response_general_param(request_obj, response)

    return HttpResponse(json.dumps(response))
    pass


def deal_user_register(request):
    """
    处理用户的注册
    :param request: HttpRequest
    :return: HttpResponse json风格
    """
    try:
        request_obj_str = request.body

        request_obj = json.loads(request_obj_str)

        user_name = request_obj["user_name"]
        psw = request_obj["password"]
        email = request_obj["email"]
        head_image = request_obj["head_image"]

        if get_is_email_exist(email):
            error_response = {
                "code": 2,
                "msg": "email is already exist"
            }
            return HttpResponse(json.dumps(error_response))
            pass

        User.objects.create(user_name=user_name, password=psw, email=email, head_image=head_image, balance=0)
    except Exception as e:
        error_response = {
            "code": 1,
            "msg": "server error"
        }
        return HttpResponse(json.dumps(error_response))

    success_response = {
        "code": 0,
        "msg": "success"
    }

    return HttpResponse(json.dumps(success_response))

    pass


def deal_user_sign(request):
    """
    处理用户登陆
    :param request: HttpRequest
    :return: HttpResponse json风格
    """
    request_obj = json.loads(request.body)
    # 检查email是否存在
    try:
        user_id = User.objects.get(email=request_obj["email"]).id
    except:
        error_response = {
            "code": 2,
            "token": request_obj["email"],
            "msg": "user is not register"
        }
        return HttpResponse(json.dumps(error_response))
        pass

    # 检查是否已经登陆
    if get_is_user_session_exist(user_id):

        token = UserSession.objects.get(user__id=user_id).token
        success_response = {
            "code": 0,
            "token": token,
            "msg": "user already sign in"
        }
        return HttpResponse(json.dumps(success_response))
        pass

    # 没登陆进行登陆
    # email，password是否匹配
    test_sign = User.objects.filter(email=request_obj["email"]).filter(password=request_obj["password"])
    if test_sign.exists():
        # 在session表中注册
        token = md5(str(datetime.datetime.now()) + str(random.randint(20, 30)))
        UserSession.objects.create(user=User.objects.get(email=request_obj["email"]), token=token)
        success_response = {
            "code": 0,
            "token": token,
            "msg": "log in success"
        }
        return HttpResponse(json.dumps(success_response))
        pass
    else:
        error_response = {
            "code": 1,
            "msg": "email or password error",
            "token": "error"
        }

        return HttpResponse(json.dumps(error_response))
        pass
    pass


def deal_user_log_out(request):
    """
    退出登陆
    :param request: HttpRequest
    :return: json
    """
    request_obj = json.loads(request.body)
    if get_is_email_in_user_session(request_obj["email"]):
        if get_is_token_correctly(request_obj["email"], request_obj["token"]):
            UserSession.objects.filter(user__email=request_obj["email"], token=request_obj["token"]).delete()
            success_response = {
                "code": 0,
                "msg": "success"
            }
            return HttpResponse(json.dumps(success_response))
            pass
        else:
            error_response = {
                "code": 2,
                "msg": "token is wrong"
            }

            return HttpResponse(json.dumps(error_response))
        pass
    else:
        error_response = {
            "code": 1,
            "msg": "user did not sign in"
        }

        return HttpResponse(json.dumps(error_response))
        pass
    pass


def deal_user_search_user(request):
    """
    搜索用户
    :param request:
    :return:
    """
    request_obj = request.GET
    # 验证身份
    if not get_auth(request_obj["email"], request_obj["token"]):
        error_dict = {
            "code": 1,
            "msg": "permission denied"
        }
        return HttpResponse(json.dumps(error_dict))

    # 获得匹配的用户列表
    user_list = []
    result_dict = User.objects.filter(user_name=request_obj["search_name"])
    for r in result_dict:
        user_list.append({
            "email": r.email,
            "name": r.user_name
        })
        pass

    return HttpResponse(json.dumps({
        "code": 0,
        "msg": "success",
        "result": user_list
    }))

    pass


def deal_user_add_friend(request):
    """
    处理添加好友
    :param request:
    :return:
    """
    request_obj = json.loads(request.body)
    # 验证身份
    if not get_auth(request_obj["email"], request_obj["token"]):
        error_dict = {
            "code": 1,
            "msg": "permission denied"
        }
        return HttpResponse(json.dumps(error_dict))

    test = User.objects.filter(email=request_obj["join_email"])
    if test.exists():
        # 注册在申请表中
        FriendApply.objects.create(sender=User.objects.get(email=request_obj["email"]), receiver=User.objects.get(email=request_obj["join_email"]))

        success_dict = {
            "code": 0,
            "msg": "success"
        }

        return HttpResponse(json.dumps(success_dict))
        pass
    else:
        error_dict = {
            "code": 2,
            "msg": "wrong join email"
        }
        return HttpResponse(json.dumps(error_dict))
    pass


def deal_user_add_friend_result(request):
    """
    处理用户添加好友结果
    :param request: HttpRequest
    :return: bool
    """
    request_obj = request.GET
    # 验证身份
    if not get_auth(request_obj["email"], request_obj["token"]):
        error_dict = {
            "code": 1,
            "msg": "permission denied"
        }
        return HttpResponse(json.dumps(error_dict))

    # 同意
    test = FriendApply.objects.filter(sender__email=request_obj["join_email"], receiver__email=request_obj["email"])
    if test.exists():
        # 删除好友请求
        test.delete()
        # 添加好友
        if request_obj["result"] == "1":
            # 同意
            one_id = User.objects.get(email=request_obj["email"]).pk
            another_id = User.objects.get(email=request_obj["join_email"]).pk
            FriendMapping.objects.create(one_user=one_id, another_user=another_id)
            FriendMapping.objects.create(one_user=another_id, another_user=one_id)

            pass
        success_dict = {
            "code": 0,
            "msg": "success"
        }

        return HttpResponse(json.dumps(success_dict))
        pass
    else:
        error_dict = {
            "code": 2,
            "msg": "wrong join email"
        }
        return HttpResponse(json.dumps(error_dict))
    pass


def deal_user_get_friends(request):
    """
    获得全部好友列表
    :param request: HttpRequest
    :return:
    """
    request_obj = request.GET
    try:
        my_id = User.objects.get(email=request_obj["email"]).pk
        friends = FriendMapping.objects.filter(one_user=my_id)
        if friends.exists():
            result = dict()
            result["code"] = 0
            result["msg"] = "success"
            friends_list = list()

            for friend in friends:
                user_obj = User.objects.get(id=friend.another_user)
                friends_list.append({
                    "email": user_obj.email,
                    "user_name": user_obj.user_name
                })
                pass

            result["friends"] = friends_list

            return HttpResponse(json.dumps(result))
        else:
            success_dict = {
                "code": 0,
                "msg": "success and no friend yet",
                "friends": []
            }
            return HttpResponse(json.dumps(success_dict))

    except Exception as e:
        error_dict = {
            "code": 2,
            "msg": "permission denied"
        }
        return HttpResponse(json.dumps(error_dict))
        pass
    pass
