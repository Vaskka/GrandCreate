# Generated by Django 2.1 on 2018-10-22 08:30

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('chain', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='userfriendrequestorder',
            name='create_time',
        ),
        migrations.RemoveField(
            model_name='userfriendrequestorder',
            name='update_time',
        ),
    ]
