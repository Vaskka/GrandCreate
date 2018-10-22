import json

from django.http import JsonResponse

from chain.models import User, NotRegisterUser, UserSession, UserFriendRequestOrder, UserMapping, Balance, Trade
from utils import util
from utils.util import *
from chain.inner.chain import *


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

        for k, v in response_add_dict.items():
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
        return json.loads(request.body.decode("utf-8"))
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
        for k, v in json_dict.items():
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

        return True
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
                if k in correct_key:
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
    reg = "^[0-9a-zA-Z]+@[0-9a-zA-Z]+\.[0-9a-zA-Z]+$"

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

    code = NotRegisterUser.objects.get(email=json_dict["email"]).verify_code

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


def insert_into_user(json_dict, uid, tid):
    """
    注册在正式用户中, 删除未注册记录
    :param json_dict:
    :param uid: user_id
    :param tid: trader_id
    :return:
    """
    res = NotRegisterUser.objects.get(email=json_dict["email"])
    User.objects.create(user_id=uid,
                        trader_id=tid,
                        nick_name=res.nick_name,
                        password=res.password,
                        email=res.email)

    Balance.objects.create(balance_id=get_balance_id(), user=User.objects.get(user_id=uid), value=0)

    res.delete()
    pass


def check_user_id_email_matched(user_id, email):
    """
    验证user_id email 是否匹配
    :param user_id:
    :param email:
    :return:
    """
    try:
        res_u = User.objects.get(user_id=user_id)
        res_e = res_u.email
        if email == res_e:
            return True

        return False
    except:
        return False
        pass
    pass


def session_check(json_dict):
    """
    session 检查
    :param json_dict:
    :return:
    """
    try:
        st = UserSession.objects.get(user_id=json_dict["user_id"]).session_token
        if st == json_dict["session_token"]:
            return True
        return False
    except:
        return False
    pass


def check_face_token_is_already_exist(json_dict):
    """
    检查face_token是否已经存在
    :param json_dict:
    :return:
    """
    try:
        res_f = User.objects.get(user_id=json_dict["user_id"]).face_token
        if not res_f == "NULL":
            return True
        else:
            return False
    except:
        return False
    pass


def insert_face_token(json_dict):
    """
    插入face_token
    :param json_dict:
    :return:
    """
    User.objects.filter(user_id=json_dict["user_id"]).update(face_token=json_dict["face_token"])

    pass


def register_user_into_fabric(json_dict):
    """
    用户上链
    :param json_dict:
    :return:
    """
    res = User.objects.get(user_id=json_dict["user_id"])

    tid = res.trader_id
    uid = res.user_id
    ftn = res.face_token
    bid = res.balance.balance_id
    val = res.balance.value

    # user上链
    create_trader(tid, uid, ftn)

    # balance上链
    create_balance(balance_id=bid, trader_id=tid, value=int(val))
    pass


def check_user_exist_with_email(json_dict):
    """
    检查用户是否存在
    :param json_dict:
    :return:
    """
    res = User.objects.filter(email=json_dict["email"])

    if res.exists():
        return True
    return False
    pass


def check_email_password(json_dict):
    """
    EMAIL PASSWORD
    :param json_dict:
    :return:
    """
    res = User.objects.get(email=json_dict["email"])
    if res.password == json_dict["password"]:
        return True

    return False
    pass


def insert_into_user_session(email):
    """
    插入session表
    :param email:
    :return: [session, user_id]
    """
    res = UserSession.objects.filter(user__email=email)

    if res.exists():
        uid = res.first().user.user_id
        stn = res.first().session_token
        return [stn, uid, 1]

    # 未登录就插入UserSession
    uid = User.objects.get(email=email).user_id
    stn = md5(str(datetime.datetime.now()))

    UserSession.objects.create(user_id=uid, session_token=stn)

    return [stn, uid, 0]
    pass


def delete_from_user_session(json_dict):
    """
    在UserSession中登出
    :param json_dict:
    :return:
    """
    UserSession.objects.filter(user_id=json_dict["user_id"]).delete()
    pass


def from_email_get_user_id_and_nick_name(json_dict):
    """
    从 email 得到 user_id nick_name
    :param json_dict:
    :return:
    """
    res = User.objects.get(email=json_dict["inquire_email"])

    return [res.user_id, res.nick_name]

    pass


def insert_into_friend_request(json_dict):
    """
    插入好友注册表
    :param json_dict:
    :return: Integer 1-添加的用户不存在 2-已在请求中 0-success
    """

    uid = json_dict["add_user_id"]

    res = User.objects.filter(user_id=uid)
    if not res.exists():
        return 1

    res = UserFriendRequestOrder.objects.filter(sponsor__user_id=json_dict["user_id"]).filter(recipient__user_id=json_dict["add_user_id"])
    if res.exists():
        return 2

    oid = get_order_id()
    sp = User.objects.get(user_id=json_dict["user_id"])
    rec = User.objects.get(user_id=json_dict["add_user_id"])

    UserFriendRequestOrder.objects.create(friend_order_id=oid, sponsor=sp, recipient=rec)
    # UserFriendRequestOrder.objects.create(friend_order_id=oid,
    #                                       sponsor_id=sp,
    #                                       recipient_id=rec)
    return 0
    pass


def from_user_id_get_nick_name(user_id):
    """
    user_id 得到nick_name
    :param user_id:
    :return:
    """
    return User.objects.get(user_id=user_id).nick_name
    pass


def check_friend_order_exist_and_delete(friend_order_id):
    """
    检查好友请求订单是否存在
    :param friend_order_id:
    :return:
    """
    res = UserFriendRequestOrder.objects.filter(friend_order_id=friend_order_id)

    if res.exists():
        return True

    return False
    pass


def delete_friend_order(friend_order_id):
    """
    删除好友请求
    :param friend_order_id:
    :return:
    """
    UserFriendRequestOrder.objects.filter(friend_order_id=friend_order_id).delete()
    pass


def from_friend_order_id_get_user_id_and_nick_name(friend_order_id):
    """
    好友订单use_id nick_name
    :param friend_order_id:
    :return: list
    """

    res = UserFriendRequestOrder.objects.get(friend_order_id=friend_order_id)
    uid = res.sponsor.user_id
    nkn = res.sponsor.nick_name
    return [uid, nkn]
    pass


def add_friend_mapping(one_user_id, another_user_id):
    """
    添加好友映射
    :param one_user_id:
    :param another_user_id:
    :return:
    """

    UserMapping.objects.create(one_user=User.objects.get(user_id=one_user_id),
                               another_user=User.objects.get(user_id=another_user_id))
    UserMapping.objects.create(another_user=User.objects.get(user_id=one_user_id),
                               one_user=User.objects.get(user_id=another_user_id))

    pass


def charge_balance(uid, change_value):
    """
    充值用户的balance
    :param uid:
    :param change_value:
    :return:
    """

    val = int(Balance.objects.get(user=uid).value)

    val += int(change_value)

    Balance.objects.filter(user=uid).update(value=val)

    bid = Balance.objects.get(user=uid).balance_id
    tid = User.objects.get(user_id=uid).trader_id

    # 充值过程信息上链
    change_balance_value_on_fabric(tid, bid, val)

    return val
    pass


def check_user_id_is_exist(uid):
    """
    检查user_id是否存在对应用户
    :param uid:
    :return:
    """
    res = User.objects.filter(user_id=uid)
    if res.exists():
        return True
    return False
    pass


def check_balance_can_suffer_value_and_pay(user_id, value):
    """
    检查余额是否充足,充足直接扣除value
    :param user_id:
    :param value:
    :return:
    """
    val = Balance.objects.get(user_id=user_id).value

    if int(value) > int(val):
        return False

    Balance.objects.filter(user=User.objects.get(user_id=user_id)).update(value=val-int(value))
    return True


def check_face_token(user_id, face_token):
    """
    验证身份信息
    :param user_id:
    :param face_token:
    :return:
    """
    ft = User.objects.get(user_id=user_id).face_token

    if ft == face_token:
        return True

    return False
    pass


def insert_into_trans_order(user_id, receiver_user_id, value, face_token):
    """
    创建交易并将支付方上链
    :param user_id:
    :param receiver_user_id:
    :param value:
    :param face_token:
    :return:
    """
    oid = get_order_id()
    Transaction.objects.create(order_id=oid,
                               sender=User.objects.get(user_id=user_id),
                               receiver=User.objects.get(user_id=receiver_user_id),
                               status=1,
                               transaction_value=int(value))

    # 付款用户上链
    bid = User.objects.get(user_id=user_id).balance.balance_id
    trade_dict = create_trade(face_token, 0, value, oid, bid)

    # Trade插入扣款记录
    trade_id = trade_dict["transactionId"]
    Trade.objects.create(transaction=Transaction.objects.get(order_id=oid),
                         trade_id=trade_id,
                         trade_type=0,
                         face_token=face_token,
                         trade_value=value,
                         trade_time=datetime.datetime.strptime(trade_dict["timestamp"], "%Y-%m-%dT%H:%M:%S.%fZ"),
                         balance=Balance.objects.get(balance_id=bid))
    pass


def get_balance(uid):
    """
    查询余额
    :param uid:
    :return:
    """
    return Balance.objects.get(user=uid).value


def check_trans_order_is_exist(order_id):
    res = Transaction.objects.filter(order_id=order_id)

    if res.exists() and res.first().status == 1:
        return True

    return False


def update_trans_order(user_id, order_id, face_token):
    """
    修改订单表
    :param user_id:
    :param order_id:
    :param face_token:
    :return:
    """
    res = Transaction.objects.get(order_id=order_id)
    value = res.transaction_value
    bid = Balance.objects.get(user=user_id).balance_id

    # 转款上链
    trade_dict = create_trade(face_token, 1, value, order_id, bid)

    # 修改订单表
    Transaction.objects.filter(order_id=order_id).update(status=0)

    # Trade插入转款记录
    trade_id = trade_dict["transactionId"]
    Trade.objects.create(transaction=Transaction.objects.get(order_id=order_id),
                         trade_id=trade_id,
                         trade_type=0,
                         face_token=face_token,
                         trade_value=value,
                         trade_time=datetime.datetime.strptime(trade_dict["timestamp"], "%Y-%m-%dT%H:%M:%S.%fZ"),
                         balance=Balance.objects.get(balance_id=bid))
    return value
    pass


def charge_user(user_id, trade_value):
    """
    转款
    :param user_id:
    :param trade_value:
    :return:
    """
    value = int(Balance.objects.get(user_id=User.objects.get(user_id=user_id)).value)
    Balance.objects.filter(user=User.objects.get(user_id=user_id)).update(value=value+int(trade_value))
    pass


def from_user_id_get_balance_id(user_id):
    """
    利用user_id得到balance_id
    :param user_id:
    :return:
    """
    return Balance.objects.get(user=User.objects.get(user_id=user_id)).balance_id
    pass


def from_fabric_balance_get_balance_id(fabric_balance):
    """
    从fabric格式的balance中提取balance
    :param fabric_balance:
    :return:
    """

    return reg_match_str_with_group("^.*#(.*)$", fabric_balance)
    pass
