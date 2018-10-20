import json

import requests

chain_host = "http://localhost:3000/api/"

chain_header = {
    "Content-Type": "application/json",
    "Accept": "application/json"
}


def create_trader(trade_id, user_id, face_token):
    """
    添加一个Trader
    :param trade_id:
    :param user_id:
    :param face_token:
    :return:
    """
    url = chain_host + "Trader"

    data = {
        "$class": "com.vaskka.chaina.Trader",
        "tradeId": trade_id,
        "user_id": user_id,
        "face_token": face_token
    }
    response = requests.post(url=url, headers=chain_header, json=data)

    return json.loads(response.text)
