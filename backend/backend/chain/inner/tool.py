import json

from django.http import JsonResponse

from chain.models import User, NotRegisterUser
from utils import util
from utils.util import *


class Response:

    def __init__(self):

        # 通用返回模版
        self.common_response_dict = {
            "code": 0,
            "msg": "success"
        }

        pass

    def success_response(self, response_add_dict):
        """
        成功返回
        :param response_add_dict:
        :return: JsonResponse
        """

        for k, v in response_add_dict.items():
            self.common_response_dict[k] = v
            pass

        return JsonResponse(self.common_response_dict)

    def error_response(self, code, msg, response_add_dict):
        """
        出错返回
        :param code: 返回码
        :param msg: 返回消息
        :param response_add_dict: 附加dict
        :return: JsonResponse
        """

        self.common_response_dict["code"] = code
        self.common_response_dict["msg"] = msg

        for k, v in response_add_dict:
            self.common_response_dict[k] = v
            pass

        return JsonResponse(self.common_response_dict)
    pass


class Checker:
    """
    各种错误的检查
    """
    # 成功
    common_success = 0

    # 参数格式错误
    common_param_format_error = 401

    # 参数数量错误
    common_param_number_error = 402

    # 参数类型错误(json 字符串格式不正确)
    common_param_kind_error = 403

    # 服务器错误
    common_server_error = 500

    # 需要检查的参数格式字段
    common_param_format_summary = ("user_id", "add_user_id", "email", "order_id", "receiver_user_id", "inquire_email", "friend_order_id", "trade_value")

    @classmethod
    def trans_request_json_dict(cls, request):
        """
        提取HttpRequest 中的json
        :param request:
        :return: dict
        """
        return json.loads(str(request.body))
        pass

    @classmethod
    def common_check_param(cls, request, correct_key):
        """
        通用参数检查
        :return: Integer, str, json_dict
        """
        try:
            json_dict = cls.trans_request_json_dict(request)
        except json.decoder.JSONDecodeError as e:
            return 403, "json format error", {}

        if not cls.common_param_format(json_dict):
            return cls.common_param_format_error, "params format error", json_dict
        if not cls.common_param_number(json_dict, correct_key):
            return cls.common_param_number_error, "param number error", json_dict

        return 0, "success", json_dict
        pass

    @classmethod
    def common_param_format(cls, json_dict):
        """
        参数格式检查
        :return: boolean
        """
        for k, v in json_dict:
            if k in cls.common_param_format_summary:
                check_k = ".*" + k + ".*"

                if reg_is_match_str(check_k, "user_id"):
                    return user_id_check(v)
                if reg_is_match_str(check_k, "order_id"):
                    return order_id_check(v)
                if reg_is_match_str(check_k, "email"):
                    return email_check(v)
                if reg_is_match_str(check_k, "trade_value"):
                    return trade_value_check(v)

        pass

    @classmethod
    def common_param_number(cls, json_dict, correct_key):
        """
        参数个数检查
        :return: BOOLEAN
        """
        if len(correct_key) == len(json_dict):
            i = 0
            for k, v in json_dict.items():
                if k == correct_key[i]:
                    i += 1
                else:
                    return False
                pass

            return True
        else:
            return False
        pass
    pass


def email_check(email):
    """
    检查是否是合法的email
    :param email: email
    :return: boolean
    """
    reg = "^[0-9a-zA-Z]@[0-9a-zA-Z]\.[0-9a-zA-Z]$"

    return reg_is_match_str(reg, email)
    pass


def user_id_check(user_id):
    """
    检查user_id是否合法
    :param user_id:
    :return:
    """
    reg = "^user[0-9a-zA-Z]{28}$"

    return reg_is_match_str(reg, user_id)
    pass


def order_id_check(order_id):
    """
    检查order_id是否合法
    :param order_id:
    :return:
    """
    reg = "^order[0-9a-zA-Z]{27}$"

    return reg_is_match_str(reg, order_id)
    pass


def trade_value_check(trade_value):
    """
    检查trade_value 是否合法
    :param trade_value:
    :return:
    """

    reg = "^[0-9]+$"

    return reg_is_match_str(reg, trade_value)
    pass


"""
业务逻辑检查
"""


def check_try_register_over_commit(json_dict):
    """
    尝试注册检查重复提交
    :return: Integer, str
    """

    res = User.objects.filter(email=json_dict["email"])
    if res.exists():
        return True

    res = NotRegisterUser.objects.filter(email=json_dict["email"])
    if res.exists():
        return True

    return False
    pass


def check_email_send(json_dict, verify_code):
    """
    尝试发送验证码邮件
    :param json_dict:
    :param verify_code:
    :return:
    """
    send_a_email(json_dict["email"], "您的验证码为:" + str(verify_code))
    pass


def insert_into_not_register_user(json_dict, verify_code):
    """
    插入未注册用户表
    :param json_dict:
    :param verify_code:
    :return None
    """

    NotRegisterUser.objects.create(user_id=get_user_id(),
                                   nick_name=json_dict["nick_name"],
                                   password=json_dict["password"],
                                   email=json_dict["email"],
                                   verify_code=verify_code)
    pass


def check_not_register_user_exist(json_dict):
    """
    检查未注册用户是否存在
    :return:
    """
    res = NotRegisterUser.objects.filter(email=json_dict["email"])

    if res.exists():
        return True

    return False
    pass


def check_verify_code(json_dict):
    """
    检查验证码
    :param json_dict:
    :return:
    """

    code = NotRegisterUser.objects.get(email=json_dict["email"])

    if code == json_dict["verify_code"]:
        return True
        pass

    return False


def update_into_not_register_user(json_dict, code):
    """
    更新未注册用户表
    :param json_dict:
    :param code:
    :return:
    """
    NotRegisterUser.objects.filter(email=json_dict["email"]).update(verify_code=code)
    pass
