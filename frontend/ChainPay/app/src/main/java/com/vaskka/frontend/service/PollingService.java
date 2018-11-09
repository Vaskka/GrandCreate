package com.vaskka.frontend.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.vaskka.api.chain.user.entity.response.BaseResponse;
import com.vaskka.api.chain.user.entity.response.GetUnreadResponse;
import com.vaskka.api.chain.user.entity.response.TransactionInquireResponse;
import com.vaskka.api.chain.user.lib.ApiTool;
import com.vaskka.api.chain.user.lib.User;
import com.vaskka.frontend.utils.ConstUtil;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PollingService extends Service {

    private PollingCallback callback;

    private DownLoadBinder downLoadBinder = new DownLoadBinder();

    public class DownLoadBinder extends Binder {
        /**
         * PollingService
         * @return
         */
        public PollingService getService() {
            return PollingService.this;
        }
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downLoadBinder;
    }

    public static interface PollingCallback {
        /**
         * 得到实时更新的数据
         *
         * @return
         */
        public void getList(List<GetUnreadResponse.UnreadItem> list );
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    ApiTool.doTransactionInquire(User.getInstance(), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            GetUnreadResponse resp = (GetUnreadResponse) BaseResponse.load(response.body().string(), GetUnreadResponse.class);

                            if (resp.getCode() == 0) {
                                List<GetUnreadResponse.UnreadItem> list = resp.getUnread();

                                callback.getList(list);

                            }
                        }
                    });


                }
            }, 0, ConstUtil.POLLING_PASSING);


        return START_STICKY;
    }

    /**
     * 提供接口回调方法
     * @param callback
     */
    public void setCallback(PollingCallback callback) {
        this.callback = (PollingCallback) callback;
    }




}
