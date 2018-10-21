import datetime
import json
import random

from django.http import HttpResponse, JsonResponse

# Create your views here.
from chain.inner.chain import *
from utils import util
from utils.request import QueryRequest
from utils.util import md5
from chain.inner.tool import *


def user_try_register(request):
    """
    用户尝试注册
    :param request:
    :return:
    """
    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "password", "nick_name"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1], {})

        # 业务检查
        if check_try_register_over_commit(param_check[2]):
            res.error_response(501, "over commit", {})

        # 发送验证码
        code = str(random_int())
        check_email_send(param_check[2], code)

        # 插入未注册用户表
        insert_into_not_register_user(param_check[2], code)

        return res.success_response({})
    except Exception as e:
        return res.error_response(500, str(e), {})
    pass


def user_confirm_verify(request):
    """
    用户验证码验证
    :param request:
    :return:
    """
    res = Response()

    uid = get_user_id()

    session_token = md5(get_order_id())

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "verify_code"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1], {"user_id": "NULL", "session_token": "NULL"})

        # 业务检查
        # 检查是否存在在未注册的用户表
        if check_not_register_user_exist(param_check[2]):
            # 检查验证码
            if check_verify_code(param_check[2]):
                # 正式注册
                tid = get_trade_id()
                # 注册在正式用户中, 删除未注册记录
                insert_into_user(param_check[2], uid, tid)

                return res.success_response({"user_id": uid, "session_token": session_token})
            else:
                return res.error_response(502, "error verify code", {"user_id": "NULL", "session_token": "NULL"})
        else:
            return res.error_response(501, "user not exist", {"user_id": "NULL", "session_token": "NULL"})
    except Exception as e:
        return res.error_response(500, str(e), {})
    pass


def user_resend_code(request):
    """
    用户验证码重新发送
    :param request:
    :return:
    """

    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1], {})

        # 业务检查
        # 发送验证码
        if check_not_register_user_exist(param_check[2]):

            code = str(random_int())
            check_email_send(param_check[2], code)

            # 更新未注册用户表
            update_into_not_register_user(param_check[2], code)

            return res.success_response({})
        else:
            return res.error_response(501, "user does not exist", {})
    except Exception as e:
        return res.error_response(500, str(e), {})
    pass


def user_insert_face_token(request):
    """
    用户录入人脸信息
    :param request:
    :return:
    """

    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "user_id", "session_token", "face_token"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1], {})

        # 业务检查
        # 检查session
        if not session_check(param_check[2]):
            return res.error_response(504, "identity error", {})

        # 检查user_id email是否匹配
        if check_user_id_email_matched(param_check[2]["user_id"], param_check[2]["email"]):
            # 检查face_token 是否已经存在
            if check_face_token_is_already_exist(param_check[2]):
                return res.error_response(502, "face_token already exist", {})

            # 插入face_token
            insert_face_token(param_check[2])

            # 用户上链
            register_user_into_fabric(param_check[2])

            return res.success_response({})
            pass
        else:
            return res.error_response(503, "user_id, email does not matched or user does not exist", {})
    except Exception as e:
        return res.error_response(500, str(e), {})
    pass


def user_login(request):
    """
    用户登陆
    :param request:
    :return:
    """
    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "password"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1], {"user_id": "NULL", "session_token": "NULL"})

        # 业务检查
        # 检查用户是否存在
        if not check_user_exist_with_email(param_check[2]):
            return res.error_response(501, "user does not exist", {"user_id": "NULL", "session_token": "NULL"})

        # 检查密码
        if not check_email_password(param_check[2]):
            return res.error_response(502, "password error", {"user_id": "NULL", "session_token": "NULL"})

        result = insert_into_user_session(param_check[2])

        return res.success_response({"user_id": result[1], "session_token": result[0]})
    except Exception as e:
        return res.error_response(500, str(e), {"user_id": "NULL", "session_token": "NULL"})
    pass

    pass


def user_logout(request):
    """
    用户登出
    :param request:
    :return:
    """
    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "user_id", "session_token"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1], {})

        # 业务检查
        # 身份检查
        if not session_check(param_check[2]):
            return res.error_response(504, "identity error", {})
        # 检查用户是否存在
        if not check_user_exist_with_email(param_check[2]):
            return res.error_response(501, "user does not exist", {})

        # email user_id 是否匹配
        if not check_user_id_email_matched(email=param_check[2]["email"], user_id=param_check[2]["user_id"]):
            return res.error_response(503, "email or user_id error", {})
        # 删除session
        delete_from_user_session(param_check[2])
        return res.success_response({})
    except Exception as e:
        return res.error_response(500, str(e), {})
    pass


def user_find_from_email_user_id(request):
    """
    利用邮箱查询user_id
    :param request:
    :return:
    """
    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "user_id", "session_token", "inquire_email"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1], {"inquire_email": "NULL", "result": "NULL"})

        # 业务检查
        # 身份检查
        if not session_check(param_check[2]):
            return res.error_response(504, "identity error", {"inquire_email": "NULL", "result": "NULL"})
        # 检查用户是否存在
        if not check_user_exist_with_email(param_check[2]):
            return res.error_response(501, "user does not exist", {"inquire_email": "NULL", "result": "NULL"})

        # email user_id 是否匹配
        if not check_user_id_email_matched(email=param_check[2]["email"], user_id=param_check[2]["user_id"]):
            return res.error_response(503, "email or user_id error", {"inquire_email": "NULL", "result": "NULL"})

        res = from_email_get_user_id_and_nick_name(param_check[2])

        return res.success_response({"inquire_email": res[0], "result": res[1]})
    except Exception as e:
        return res.error_response(500, str(e), {"inquire_email": "NULL", "result": "NULL"})
    pass


def user_try_add_friend(request):
    """
    用户尝试添加好友
    :param request:
    :return:
    """
    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "user_id", "session_token", "add_user_id"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1], {"add_user_id": "NULL", "add_user_nick_name": "NULL"})

        # 业务检查
        # 身份检查
        if not session_check(param_check[2]):
            return res.error_response(504, "identity error", {"add_user_id": "NULL", "add_user_nick_name": "NULL"})
        # 检查用户是否存在
        if not check_user_exist_with_email(param_check[2]):
            return res.error_response(501, "user does not exist", {"add_user_id": "NULL", "add_user_nick_name": "NULL"})

        # email user_id 是否匹配
        if not check_user_id_email_matched(email=param_check[2]["email"], user_id=param_check[2]["user_id"]):
            return res.error_response(503, "email or user_id error", {"add_user_id": "NULL", "add_user_nick_name": "NULL"})

        res = insert_into_friend_request(param_check[2])
        if res == 1:
            return res.error_response(501, "user does not exist", {"add_user_id": "NULL", "add_user_nick_name": "NULL"})
        if res == 2:
            return res.error_response(502, "request has already sent", {"add_user_id": "NULL", "add_user_nick_name": "NULL"})

        return res.success_response({"add_user_id": param_check[2]["add_user_id"], "add_user_nick_name": from_user_id_get_nick_name(param_check[2]["user_id"])})
    except Exception as e:
        return res.error_response(500, str(e), {"inquire_email": "NULL", "result": "NULL"})
    pass


def user_confirm_add_friend(request):
    """
    用户同意添加好友
    :param request:
    :return:
    """


    pass


def transaction_try_trade(request):
    """
    向用户尝试发起转账
    :param request:
    :return:
    """
    pass


def transaction_confirm_trade(request):
    """
    用户同意交易申请
    :param request:
    :return:
    """

    pass


def transaction_inquire_trade(request):
    """
    用户查询全部交易
    :param request:
    :return:
    """
    pass