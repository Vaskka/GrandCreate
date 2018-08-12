from django.contrib import admin

# Register your models here.
from chain.models import User, Talk, UserSession, FriendApply, FriendMapping

admin.site.register(User)
admin.site.register(Talk)
admin.site.register(UserSession)
# admin.site.register(Friend)
admin.site.register(FriendApply)
admin.site.register(FriendMapping)
