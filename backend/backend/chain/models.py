from django.db import models

# Create your models here.
from django.db.models import CharField, DateTimeField, FloatField, ManyToManyField, TextField, ForeignKey, \
    BooleanField, CASCADE, OneToOneField, SET_NULL, DO_NOTHING, IntegerField


class User(models.Model):
    """
    用户model
    """

    # 主键id
    user_id = CharField(max_length=32, primary_key=True)

    # trade_id
    trade_id = CharField(max_length=32, null=True)

    # 昵称
    nick_name = CharField(max_length=255, null=False, blank=False)

    # password (md5)
    password = CharField(max_length=32, null=False, blank=False)

    # email
    email = CharField(max_length=255, null=False, blank=False)

    # face_token
    face_token = CharField(max_length=255, null=False, blank=False, default="NULL")

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)

    pass


class UserMapping(models.Model):
    """
    好友映射, 主键自增
    """
    one_user_id = OneToOneField('User', on_delete=models.SET_NULL, null=True)

    another_user_id = OneToOneField('User', on_delete=models.SET_NULL, null=True)

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)
    pass


class UserSession(models.Model):
    """
    session
    """
    # 关联User
    user = OneToOneField('User', on_delete=models.SET_NULL, null=True)

    # session_token
    session_token = CharField(max_length=32, null=False, blank=False)

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)

    pass


class NotRegisterUser(models.Model):
    """
    未完成成注册的User
    """
    # 主键
    user_id = CharField(max_length=32, primary_key=True)

    # 昵称
    nick_name = CharField(max_length=255, null=False, blank=False)

    # password (md5)
    password = CharField(max_length=32, null=False, blank=False)

    # email
    email = CharField(max_length=255, null=False, blank=False)

    # verify code
    verify_code = CharField(max_length=4, null=False, blank=False)

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)

    pass


class UserFriendRequestOrder(models.Model):
    """
    好友请求订单model
    """
    # 订单主键
    friend_order_id = CharField(max_length=32, primary_key=True)

    # 发起人
    sponsor = OneToOneField("User", on_delete=models.SET_NULL, null=True)

    # 接受人
    recipient = OneToOneField("User", on_delete=models.SET_NULL, null=True)

    # 订单创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 订单更新时间
    update_time = DateTimeField(default=True)

    pass


class Transaction(models.Model):
    """
    转账订单model
    一次transaction代表两次Trade（一次扣款一次充值）
    """
    # 主键
    order_id = CharField(max_length=32, primary_key=True)

    # 转账人
    sender = OneToOneField("User", null=True, on_delete=models.SET_NULL)

    # 首款人
    receiver = OneToOneField("User", null=True, on_delete=models.SET_NULL)

    # 状态 0-已完成 1-待确认 2-已取消
    status = IntegerField()

    # 交易额
    transaction_value = IntegerField()

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)

    pass


class Balance(models.Model):
    """
    余额
    """
    # 关联用户
    user = OneToOneField('User', on_delete=models.SET_NULL, null=True)

    # 余额
    value = IntegerField()

    # 主键
    balanceId = CharField(max_length=32, primary_key=True)

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 更新时间
    update_time = DateTimeField(auto_now=True)
    pass


class Trade(models.Model):
    """
    Trade model
    """

    # 关联transaction
    transaction = ForeignKey("Transaction", null=True, on_delete=models.SET_NULL)

    # trade_id (取款链上交易id)
    transaction_id = CharField(max_length=64, blank=False, null=False, primary_key=True)

    # 交易类型
    trade_type = IntegerField()

    # 交易人的face_token
    face_token = CharField(max_length=255, null=False, blank=False)

    # 交易额
    trade_value = IntegerField()

    # 交易时间
    trade_time = DateTimeField()

    # 关联Balance
    balance = OneToOneField("Balance", on_delete=models.SET_NULL, null=True)
    pass
