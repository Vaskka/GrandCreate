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

    tid = get_trade_id()

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
                # 注册在正式用户中, 删除未注册记录
                insert_into_user(param_check[2], uid, tid)
                # 注册在session中
                result = insert_into_user_session(param_check[2]["email"])
                return res.success_response({"user_id": result[1], "session_token": result[0]})
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

            # 用户资产上链
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

        result = insert_into_user_session(param_check[2]["email"])

        if result[2] == 1:
            return res.error_response(201, "user already login", {"user_id": result[1], "session_token": result[0]})

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

        result = from_email_get_user_id_and_nick_name(param_check[2])

        return res.success_response({"inquire_email": param_check[2]["inquire_email"], "result": result[0]})
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

        result = insert_into_friend_request(param_check[2])
        if result == 1:
            return res.error_response(501, "user does not exist", {"add_user_id": "NULL", "add_user_nick_name": "NULL"})
        if result == 2:
            return res.error_response(502, "request has already sent", {"add_user_id": "NULL", "add_user_nick_name": "NULL"})

        return res.success_response({"add_user_id": param_check[2]["add_user_id"], "add_user_nick_name": from_user_id_get_nick_name(param_check[2]["add_user_id"])})
    except Exception as e:
        return res.error_response(500, str(e), {"add_user_id": "NULL", "add_user_nick_name": "NULL"})
    pass


def user_confirm_add_friend(request):
    """
    用户同意添加好友
    :param request:
    :return:
    """
    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "user_id", "session_token", "friend_order_id"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1],
                                      {"add_user_id": "NULL", "add_user_nick_name": "NULL"})

        # 业务检查
        # 身份检查
        if not session_check(param_check[2]):
            return res.error_response(504, "identity error", {"add_user_id": "NULL", "add_user_nick_name": "NULL"})
        # 检查用户是否存在
        if not check_user_exist_with_email(param_check[2]):
            return res.error_response(501, "user does not exist", {"add_user_id": "NULL", "add_user_nick_name": "NULL"})

        # email user_id 是否匹配
        if not check_user_id_email_matched(email=param_check[2]["email"], user_id=param_check[2]["user_id"]):
            return res.error_response(503, "email or user_id error",
                                      {"add_user_id": "NULL", "add_user_nick_name": "NULL"})

        # 检查订单是否存在
        if not check_friend_order_exist_and_delete(param_check[2]["friend_order_id"]):
            return res.error_response(502, "order_id does not exist",
                                      {"add_user_id": "NULL", "add_user_nick_name": "NULL"})
        result = from_friend_order_id_get_user_id_and_nick_name(param_check[2]["friend_order_id"])
        delete_friend_order(param_check[2]["friend_order_id"])
        # 添加朋友映射
        add_friend_mapping(one_user_id=param_check[2]["user_id"], another_user_id=result[0])

        return res.success_response({"add_user_id": result[0],
                                     "add_user_nick_name": result[1]})
    except Exception as e:
        return res.error_response(500, str(e), {"add_user_id": "NULL", "add_user_nick_name": "NULL"})
    pass


def user_charger(request):
    """
    账户充值
    :param request:
    :return:
    """

    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "user_id", "session_token", "trade_value"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1],
                                      {"balance": "NULL"})

        # 业务检查
        # 身份检查
        if not session_check(param_check[2]):
            return res.error_response(504, "identity error", {"balance": "NULL"})
        # 检查用户是否存在
        if not check_user_exist_with_email(param_check[2]):
            return res.error_response(501, "user does not exist", {"balance": "NULL"})

        # email user_id 是否匹配
        if not check_user_id_email_matched(email=param_check[2]["email"], user_id=param_check[2]["user_id"]):
            return res.error_response(503, "email or user_id error",
                                      {"balance": "NULL"})

        # 检查是否已经进行生物信息登记
        if not check_face_token_is_already_exist(param_check[2]):
            return res.error_response(501, "user face_token does not exist", {"balance": "NULL"})

        # 更改Balance, 过程信息上链
        bal = charge_balance(param_check[2]["user_id"], param_check[2]["trade_value"])

        return res.success_response({"balance": bal})

    except Exception as e:
        return res.error_response(500, str(e), {"balance": "NULL"})
    pass


def user_get_face_token(request):
    """
    用户得到链上的face_token
    :param request:
    :return:
    """

    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "user_id", "session_token"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1],
                                      {"face_token": "NULL"})

        # 业务检查
        # 身份检查
        if not session_check(param_check[2]):
            return res.error_response(504, "identity error", {"face_token": "NULL"})
        # 检查用户是否存在
        if not check_user_exist_with_email(param_check[2]):
            return res.error_response(501, "user does not exist", {"face_token": "NULL"})

        # email user_id 是否匹配
        if not check_user_id_email_matched(email=param_check[2]["email"], user_id=param_check[2]["user_id"]):
            return res.error_response(503, "email or user_id error", {"face_token": "NULL"})

        # 更改Balance, 过程信息上链
        ft = from_user_id_get_face_token(param_check[2]["user_id"])

        return res.success_response({"face_token": ft})

    except Exception as e:
        return res.error_response(500, str(e), {"face_token": "NULL"})

    pass


def transaction_try_trade(request):
    """
    向用户尝试发起转账
    :param request:
    :return:
    """

    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "user_id", "session_token", "trade_value", "receiver_user_email", "face_token"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1],
                                      {"balance": "NULL"})

        # 业务检查
        # 身份检查
        if not session_check(param_check[2]):
            return res.error_response(504, "identity error", {"balance": "NULL"})
        # 检查用户是否存在
        if not check_user_exist_with_email(param_check[2]):
            return res.error_response(501, "user does not exist", {"balance": "NULL"})

        # email user_id 是否匹配
        if not check_user_id_email_matched(email=param_check[2]["email"], user_id=param_check[2]["user_id"]):
            return res.error_response(503, "email or user_id error", {"balance": "NULL"})

        # 检查余额
        if not check_balance_can_suffer_value_and_pay(param_check[2]["user_id"], param_check[2]["trade_value"]):
            return res.error_response(502, "insufficient balance", {"balance": "NULL"})

        # 检查收款方是否存在
        if not inner_check_user_exist_with_email(param_check[2]["receiver_user_email"]):
            return res.error_response(501, "receiver user does not exist", {"balance": "NULL"})

        # 验证生物信息
        if not check_face_token(param_check[2]["user_id"], param_check[2]["face_token"]):
            return res.error_response(505, "bioinformatics error", {"balance": "NULL"})


        # 插入在订单表并上链
        insert_into_trans_order(param_check[2]["user_id"], param_check[2]["receiver_user_email"], param_check[2]["trade_value"], param_check[2]["face_token"])

        return res.success_response({"balance": str(get_balance(param_check[2]["user_id"]))})

    except Exception as e:
        return res.error_response(500, repr(e), {"balance": "NULL"})
    pass


def transaction_confirm_trade(request):
    """
    用户同意交易申请
    :param request:
    :return:
    """
    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "user_id", "session_token", "order_id", "face_token"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1],
                                      {"balance": "NULL"})

        # 业务检查
        # 身份检查
        if not session_check(param_check[2]):
            return res.error_response(504, "identity error", {"balance": "NULL"})

        # 检查用户是否存在
        if not check_user_exist_with_email(param_check[2]):
            return res.error_response(501, "user does not exist", {"balance": "NULL"})

        # email user_id 是否匹配
        if not check_user_id_email_matched(email=param_check[2]["email"], user_id=param_check[2]["user_id"]):
            return res.error_response(503, "email or user_id error", {"balance": "NULL"})

        # 检查订单是否存在
        if not check_trans_order_is_exist(param_check[2]["order_id"]):
            return res.error_response(504, "order not exist", {"balance": "NULL"})

        # 验证生物信息
        if not check_face_token(param_check[2]["user_id"], param_check[2]["face_token"]):
            return res.error_response(505, "bioinformatics error", {"balance": "NULL"})

        # 修改订单表并上链
        v = update_trans_order(param_check[2]["user_id"], param_check[2]["order_id"], param_check[2]["face_token"])

        # 转款
        charge_user(param_check[2]["user_id"], v)
        return res.success_response({"balance": str(get_balance(param_check[2]["user_id"]))})

    except Exception as e:
        return res.error_response(500, str(e), {"balance": "NULL"})
    pass


def transaction_inquire_trade(request):
    """
    用户查询全部交易
    :param request:
    :return:
    """
    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "user_id", "session_token"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1],
                                      {"record": []})

        # 业务检查
        # 身份检查
        if not session_check(param_check[2]):
            return res.error_response(504, "identity error", {"record": []})

        # 检查用户是否存在
        if not check_user_exist_with_email(param_check[2]):
            return res.error_response(501, "user does not exist", {"record": []})

        # email user_id 是否匹配
        if not check_user_id_email_matched(email=param_check[2]["email"], user_id=param_check[2]["user_id"]):
            return res.error_response(503, "email or user_id error", {"record": []})

        # 链上查询
        trade_dict = get_all_transaction()

        record = []
        bid = from_user_id_get_balance_id(param_check[2]["user_id"])
        for trade in trade_dict:
            # 筛选与自己资产相关的trade记录
            if bid != from_fabric_balance_get_balance_id(trade["balance"]):
                continue

            # 时间 类型 交易额
            tsp = datetime.datetime.strptime(trade["timestamp"], "%Y-%m-%dT%H:%M:%S.%fZ").strftime("%Y-%m-%d %H:%M:%S")
            t_type = trade["type"]
            value = str(trade["tradeValue"])

            record.append({
                "time_stamp": tsp,
                "type": t_type,
                "trade_value": value
            })
            pass

        success_return_dict = {
            "code": 0,
            "msg": "success",
            "record": record
        }

        return JsonResponse(success_return_dict)

    except Exception as e:
        return res.error_response(500, str(e), {"record": []})
    pass


def transaction_inquire_unread(request):
    """
    轮询
    :param request:
    :return:
    """
    res = Response()

    try:
        # 参数检查结果
        param_check = Checker.common_check_param(request, ["email", "user_id", "session_token"])

        if 0 not in param_check:
            return res.error_response(param_check[0], param_check[1],
                                      {"unread": []})

        # 业务检查
        # 身份检查
        if not session_check(param_check[2]):
            return res.error_response(504, "identity error", {"unread": []})

        # 检查用户是否存在
        if not check_user_exist_with_email(param_check[2]):
            return res.error_response(501, "user does not exist", {"unread": []})

        # email user_id 是否匹配
        if not check_user_id_email_matched(email=param_check[2]["email"], user_id=param_check[2]["user_id"]):
            return res.error_response(503, "email or user_id error", {"unread": []})

        # 查询未完成的代收款订单
        unread_dict_list = get_unread(param_check[2]["user_id"])

        return res.success_response({"unread", unread_dict_list})

    except Exception as e:
        return res.error_response(500, str(e), {"unread": []})
    pass
    pass
