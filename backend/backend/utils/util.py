import hashlib


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
