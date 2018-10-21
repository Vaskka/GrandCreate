import datetime
import hashlib
import random
import re

from backend import settings
from django.core.mail import send_mail


def md5(s):
    """
    md5
    :param s: str
    :return: str
    """
    b = s.encode("utf-8")
    m = hashlib.md5()
    m.update(b)
    return m.hexdigest()


def send_a_email(receiver, content, subject=settings.EMAIL_SUBJECT):
    """
    发送邮件
    :param receiver: 接受者
    :param content: 内容
    :param subject: 主题
    :return: None
    """
    send_mail(subject, content, settings.EMAIL_HOST_USER, [receiver], fail_silently=False)
    # msg = EmailMultiAlternatives(subject, content, settings.EMAIL_HOST, [].append(receiver))
    # msg.content_subtype = 'html'
    # msg.send()
    pass


def reg_is_match_str(reg, s):
    """
    正则是否匹配字符串
    :param reg: regex
    :param s: str
    :return: boolean
    """

    result = re.match(reg, s)

    if result:
        return True
    return False
    pass


def reg_match_str_with_group(reg, s, group=1):
    """
    匹配正则中的组
    :param reg: 正则
    :param s: 待匹配字符串
    :param group: 组数
    :return: str
    """
    result = re.match(reg, s)

    if result:
        return result.group(group)

    return None
    pass


def random_int():
    """
    取4位随机数
    :return:
    """
    return random.randint(1000, 9999)
    pass


def current_time():
    """
    当前时间
    :return:
    """
    return str(datetime.datetime.now())
    pass


def get_user_id():
    """
    获取user_id
    :return:
    """
    return "user" + md5(current_time())[0:28]
    pass


def get_order_id():
    """
    获取order_id
    :return:
    """
    return "order" + md5(current_time())[0:27]
    pass


def get_trade_id():
    """
    获取trade_id
    :return:
    """
    return "trader" + md5(current_time())[0:26]
    pass


def get_balance_id():
    """
    得到balance_id
    :return:
    """

    return "balance" + md5(current_time())[0:25]
    pass