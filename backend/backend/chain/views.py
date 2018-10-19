import datetime
import json
import random

from django.http import HttpResponse, JsonResponse

# Create your views here.
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
    pass


def user_insert_face_token(request):
    """
    用户录入人脸信息
    :param request:
    :return:
    """
    pass


def user_login(request):
    """
    用户登陆
    :param request:
    :return:
    """

    pass


def user_logout(request):
    """
    用户登出
    :param request:
    :return:
    """
    pass


def user_find_from_email_user_id(request):
    """
    利用邮箱查询user_id
    :param request:
    :return:
    """

    pass


def user_try_add_friend(request):
    """
    用户尝试添加好友
    :param request:
    :return:
    """

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