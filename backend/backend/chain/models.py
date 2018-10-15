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

    pass


class UserSession(models.Model):
    """
    session
    """
    # 关联User
    user = OneToOneField('User', on_delete=models.SET_NULL, null=True)

    # session_token
    session_token = CharField(max_length=32, null=False, blank=False)

    pass


class NotRegisterUser(models.Model):
    """
    为完成成注册的User
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
    pass


class Trade(models.Model):
    """
    Trade model
    """

    # 关联transaction
    transaction = ForeignKey("Transaction", null=True, on_delete=models.SET_NULL)

    # trade_id
    transaction_id = CharField(max_length=64, blank=False, null=False, primary_key=True)

    # 交易类型
    trade_type = IntegerField()

    # 交易人的face_token
    face_token = CharField(max_length=255, null=False, blank=False)

    # 交易额
    trade_value = IntegerField()

    # 关联Balance
    balance = OneToOneField("Balance", on_delete=models.SET_NULL, null=True)
    pass
