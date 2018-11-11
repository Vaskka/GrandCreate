import datetime
import json

import requests

from chain.exception.error import BaseChainException
from chain.models import Transaction
from utils import util

chain_host = "http://localhost:3000/api/"

chain_header = {
    "Content-Type": "application/json",
    "Accept": "application/json"
}


def create_trader(trade_id, user_id, face_token):
    """
    添加一个Trader
    :param trade_id: 链上trader唯一标示
    :param user_id: 用户唯一标示
    :param face_token: 生物信息
    :return: dict: 返回信息集合
    """
    url = chain_host + "Trader"

    data = {
        "$class": "com.vaskka.chaina.Trader",
        "tradeId": trade_id,
        "user_id": user_id,
        "face_token": face_token
    }
    response = requests.post(url=url, headers=chain_header, json=data)

    result = json.loads(response.text)

    if response.status_code != 200 and result["error"]["statusCode"] != 500:
        raise BaseChainException(chain_class="com.vaskka.chaina.Trader",
                                 time=util.current_time(),
                                 request_json=json.dumps(data),
                                 response_json=response.text)

    return result


def create_balance(balance_id, trader_id, value):
    """
    添加一个balance
    :param balance_id: 链上资产唯一标示
    :param trader_id: 关联trader唯一标示
    :param value: 具体数额
    :return: dict: 结果集合
    """
    url = chain_host + "Balance"

    data = {
        "$class": "com.vaskka.chaina.Balance",
        "balanceId": balance_id,
        "value": value,
        "owner": trader_id
    }

    response = requests.post(url=url, headers=chain_header, json=data)

    result = json.loads(response.text)

    if response.status_code != 200 and result["error"]["statusCode"] != 200:
        raise BaseChainException(chain_class="com.vaskka.chaina.Balance",
                                 time=util.current_time(),
                                 request_json=json.dumps(data),
                                 response_json=response.text,
                                 method="POST")

    return result


def change_balance_value_on_fabric(trade_id, balance_id, value):
    """
    更改Balance值
    :param trade_id: 交易者唯一标示
    :param balance_id: 链上资产唯一标示
    :param value: 具体交易金额
    :return: dict: 信息集合
    """
    url = chain_host + "Balance/" + balance_id

    data = {
        "$class": "com.vaskka.chaina.Balance",
        "balanceId": balance_id,
        "value": value,
        "owner": trade_id
    }

    response = requests.put(url=url, headers=chain_header, json=data)

    result = json.loads(response.text)

    if response.status_code != 200 and result["error"]["statusCode"] != 200:
        raise BaseChainException(chain_class="com.vaskka.chaina.Balance",
                                 time=util.current_time(),
                                 request_json=json.dumps(data),
                                 response_json=response.text,
                                 method="PUT")

    return result

    pass


def create_trade(face_token, t_type, value, order_id, balance_id):
    """
    产生交易
    :param balance_id: 链上资产唯一标示
    :param face_token: 生物信息
    :param t_type: 交易类型
    :param value: 交易额
    :param order_id: 交易唯一标示
    :return: dict 结果信息集合
    """
    time_stamp = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%SZ")

    url = chain_host + "Trade"

    data = {
        "$class": "com.vaskka.chaina.Trade",
        "balance": balance_id,
        "face_token": face_token,
        "type": t_type,
        "tradeValue": value,
        "order_id": order_id,
        "transactionId": "",
        "timestamp": time_stamp
    }

    response = requests.post(url=url, headers=chain_header, json=data)

    result = json.loads(response.text)

    if response.status_code != 200 and result["error"]["statusCode"] != 200:
        raise BaseChainException(chain_class="com.vaskka.chaina.Trade",
                                 time=util.current_time(),
                                 request_json=json.dumps(data),
                                 response_json=response.text,
                                 method="POST")

    return result


def get_all_transaction():
    """
    得到全部的交易记录
    :return: dict 结果信息集合
    """

    url = chain_host + "Trade"

    response = requests.get(url=url, headers=chain_header)

    result = json.loads(response.text)

    if response.status_code != 200 and result["error"]["statusCode"] != 200:
        raise BaseChainException(chain_class="com.vaskka.chaina.Trade",
                                 time=util.current_time(),
                                 request_json="GET",
                                 response_json=response.text,
                                 method="POST")

    return result
    pass
