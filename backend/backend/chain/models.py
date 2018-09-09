from django.db import models

# Create your models here.
from django.db.models import CharField, DateTimeField, FloatField, ManyToManyField, TextField, ForeignKey, \
    BooleanField, CASCADE, OneToOneField, SET_NULL, DO_NOTHING, IntegerField


class User(models.Model):
    """
    用户模型
    """

    # 用户名
    user_name = CharField(max_length=50)

    # 密码
    password = CharField(max_length=50)

    # 邮箱
    email = CharField(max_length=50)

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 头像
    head_image = TextField()

    # 账户余额
    balance = IntegerField()

    def __str__(self):
        return self.user_name
        pass


class FriendMapping(models.Model):
    """ 手动实现好友映射关系 """
    """ 同步更新 """
    one_user = IntegerField(blank=True, null=True)

    another_user = IntegerField(blank=True, null=True)

    pass


class Talk(models.Model):
    """
    聊天记录
    """
    # 文字内容
    content = TextField()

    # 创建时间
    create_time = DateTimeField(auto_now_add=True)

    # 内容类型
    kind = CharField(max_length=60, default="PlainText")

    # 发送者
    send = ForeignKey(User, on_delete=DO_NOTHING, blank=True, null=True, related_name="send_talk")

    # 接收者
    receive = ForeignKey(User, on_delete=DO_NOTHING, blank=True, null=True, related_name="receive_talk")

    # 是否是新消息
    is_new = BooleanField()

    pass


class UserSession(models.Model):
    """
    用户Session表
    """
    # 关联用户
    user = OneToOneField(User, on_delete=DO_NOTHING, blank=True, null=True)
    # token
    token = CharField(max_length=255)

    pass


class FriendApply(models.Model):
    """ 好友申请 """

    # 发送者
    sender = ForeignKey(User, on_delete=DO_NOTHING, blank=True, null=True, related_name="sender")
    # 接受者
    receiver = ForeignKey(User, on_delete=DO_NOTHING, blank=True, null=True, related_name="receiver")

    pass
