import datetime
import json
import random

from django.http import HttpResponse, JsonResponse

# Create your views here.
from utils import util
from utils.request import QueryRequest
from utils.util import md5


def user_try_register(request):
    """
    用户尝试注册
    :param request:
    :return:
    """
    pass


def user_confirm_verify(request):
    """
    用户验证码验证
    :param request:
    :return:
    """
    pass


def user_resend_code(request):
    """
    用户验证码重新发送
    :param request:
    :return:
    """
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


def transaction(request):
    """
    向用户发起转账
    :param request:
    :return:
    """
    pass