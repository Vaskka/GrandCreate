"""backend URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path

from chain.views import *

urlpatterns = [
    path('admin/', admin.site.urls),
    path('call/', deal_user_call),
    path('register/', deal_user_register),
    path('register/ok/', deal_user_ok_to_register),
    path('sign/', deal_user_sign),
    path('logout/', deal_user_log_out),
    path('search_user/', deal_user_search_user),
    path('add_friend/', deal_user_add_friend),
    path('add_result/', deal_user_add_friend_result),
    path('get_friends/', deal_user_get_friends),
    path('confirm_trans/', deal_user_confirm_transform),
    path('send/', deal_user_send),

]
