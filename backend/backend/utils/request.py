
class QueryRequest:
    def __init__(self, query_dict):
        self.email = query_dict["email"]
        self.token = query_dict["token"]
        self.request_time = query_dict["request_time"]
        pass
    pass
