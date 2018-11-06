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
    path("grand/chain/register/try/", user_try_register),
    path("grand/chain/register/verify/", user_confirm_verify),
    path("grand/chain/register/send/", user_resend_code),
    path("grand/chain/register/face/insert/", user_insert_face_token),
    path("grand/chain/login/", user_login),
    path("grand/chain/logout/", user_logout),
    path("grand/chain/user/inquire/", user_find_from_email_user_id),
    path("grand/chain/user/add/friend/", user_try_add_friend),
    path("grand/chain/user/confirm/friend/", user_confirm_add_friend),
    path("grand/chain/user/charge/", user_charger),
    path("grand/chain/user/get/facetoken/", user_get_face_token),
    path("grand/chain/trade/transfer/", transaction_try_trade),
    path("grand/chain/trade/receive/", transaction_confirm_trade),
    path("grand/chain/trade/inquire/", transaction_inquire_trade),
    path("grand/chain/trade/unread/", transaction_inquire_unread),
    path("admin/", admin.site.urls)
]
