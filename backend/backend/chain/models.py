from django.db import models

# Create your models here.
from django.db.models import CharField, DateTimeField, ForeignKey, OneToOneField, IntegerField
from utils.util import *


class User(models.Model):
    """
    用户model
    """

    # 主键id
    user_id = CharField(max_length=32, primary_key=True)

    # trader_id
    trader_id = CharField(max_length=32, default="NULL")

    # 昵称
    nick_name = CharField(max_length=255)

    # password (md5)
    password = CharField(max_length=32)

    # email
    email = CharField(max_length=255)

    # face_token
    face_token = CharField(max_length=255, default="NULL")

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)

    def __str__(self):
        return self.nick_name

    pass


class UserMapping(models.Model):
    """
    好友映射, 主键自增
    """
    one_user = OneToOneField(User, related_name='one_user', on_delete=models.CASCADE)

    another_user = OneToOneField(User, related_name='another_user', on_delete=models.CASCADE)

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)

    def __str__(self):
        return self.one_user.nick_name + "->" + self.another_user.nick_name

    pass


class UserSession(models.Model):
    """
    session
    """
    # session_id = CharField(max_length=32, primary_key=True, default=md5(current_time()))

    # 关联User
    user = OneToOneField(User, related_name='user', on_delete=models.CASCADE)

    # session_token
    session_token = CharField(max_length=32)

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)

    def __str__(self):
        return self.user.nick_name

    pass


class NotRegisterUser(models.Model):
    """
    未完成成注册的User
    """
    # 主键
    user_id = CharField(max_length=32, primary_key=True)

    # 昵称
    nick_name = CharField(max_length=255)

    # password (md5)
    password = CharField(max_length=32)

    # email
    email = CharField(max_length=255)

    # verify code
    verify_code = CharField(max_length=4)

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)

    def __str__(self):
        return self.nick_name
    pass


class UserFriendRequestOrder(models.Model):
    """
    好友请求订单model
    """
    # 订单主键
    friend_order_id = CharField(max_length=32, primary_key=True)

    # 发起人
    sponsor = OneToOneField(User, related_name="friend_sender", on_delete=models.CASCADE)

    # 接受人
    recipient = OneToOneField(User, related_name="friend_receiver", on_delete=models.CASCADE)

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)

    def __str__(self):
        return self.sponsor.nick_name + "->" + self.recipient.nick_name
    pass


class Transaction(models.Model):
    """
    转账订单model
    一次transaction代表两次Trade（一次扣款一次充值）
    """
    # 主键
    order_id = CharField(max_length=32, primary_key=True)

    # 转账人
    sender = ForeignKey(User, related_name="pay_sender", on_delete=models.CASCADE)

    # 首款人
    receiver = ForeignKey(User, related_name="pay_receiver", on_delete=models.CASCADE)

    # 状态 0-已完成 1-待确认 2-已取消
    status = IntegerField()

    # 交易额
    transaction_value = IntegerField()

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)

    def __str__(self):
        return self.sender.nick_name + "->" + self.receiver.nick_name
    pass


class Balance(models.Model):
    """
    余额
    """
    # 关联用户
    user = OneToOneField(User, related_name='balance', on_delete=models.CASCADE)

    # 余额
    value = IntegerField()

    # 主键
    balance_id = CharField(max_length=32, primary_key=True)

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)

    def __str__(self):
        return self.user.nick_name
    pass


class Trade(models.Model):
    """
    Trade model
    """

    # 关联transaction
    transaction = ForeignKey(Transaction, related_name="transaction", on_delete=models.CASCADE)

    # trade_id (区块链上交易id)
    trade_id = CharField(max_length=255, primary_key=True)

    # 交易类型
    trade_type = IntegerField()

    # 交易人的face_token
    face_token = CharField(max_length=255)

    # 交易额
    trade_value = IntegerField()

    # 交易时间
    trade_time = DateTimeField()

    # 关联Balance
    balance = ForeignKey(Balance, related_name="balance", on_delete=models.CASCADE)

    def __str__(self):
        return self.balance.user.nick_name
    pass
