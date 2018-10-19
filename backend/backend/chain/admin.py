from django.contrib import admin

# Register your models here.
from chain.models import User, UserSession, NotRegisterUser,UserMapping, UserFriendRequestOrder, Transaction, Balance, Trade

admin.site.register(User)

admin.site.register(UserSession)
admin.site.register(NotRegisterUser)
admin.site.register(UserMapping)
admin.site.register(UserFriendRequestOrder)
admin.site.register(Transaction)
admin.site.register(Balance)
admin.site.register(Trade)
