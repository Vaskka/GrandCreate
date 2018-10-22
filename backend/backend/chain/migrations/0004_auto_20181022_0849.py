# Generated by Django 2.1 on 2018-10-22 08:49

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('chain', '0003_auto_20181022_0836'),
    ]

    operations = [
        migrations.AlterField(
            model_name='userfriendrequestorder',
            name='recipient',
            field=models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, related_name='friend_receiver', to='chain.User'),
        ),
        migrations.AlterField(
            model_name='userfriendrequestorder',
            name='sponsor',
            field=models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, related_name='friend_sender', to='chain.User'),
        ),
    ]