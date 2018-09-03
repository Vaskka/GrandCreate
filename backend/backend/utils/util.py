import hashlib

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
