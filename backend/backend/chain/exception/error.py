class BaseChainException(Exception):

    def __init__(self, **kwargs):
        # 发生异常的类名
        self.chain_class = kwargs["chain_class"]

        # 异常时间
        self.time = kwargs["time"]

        # 请求json
        self.request_json = kwargs["request_json"]

        # 返回json
        self.response_json = kwargs["response_json"]

        # 操作
        self.method = kwargs["method"]
        pass
