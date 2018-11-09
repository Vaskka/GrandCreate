package com.vaskka.frontend.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 常用工具
 */
public class UsualUtil {

    /**
     * 利用Toast展示message
     * @param context 要展示的上下文
     * @param message 信息
     */
    public static void showWithToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 展示正在加载的dialog
     * @param context 在显示的上下文
     * @param runningString 正在加载时的文字
     * @param okString 成功后的文字
     * @param failString 失败后的文字
     * @return LoadingDialog 对象
     */
    public static LoadingDialog showLoading(Context context, String runningString, String okString, String failString) {
        LoadingDialog ld = new LoadingDialog(context);
        ld.setLoadingText(runningString)
                .setSuccessText(okString)
                .setFailedText(failString)
                .setInterceptBack(true)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_ONE)
                .show();

        return ld;
    }


    /**
     * 定制弹出框
     * @param context 上下文
     * @param title 标题
     * @param content 内容
     * @param okClickListener 按钮触发
     */
    public static void showInfoInDialog(Context context, String title, String content, DialogInterface.OnClickListener okClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title).setMessage(content)
                .setPositiveButton("ok", okClickListener).create();

        alertDialog.show();
    }

    /**
     * 转换分到元
     * @param smallBalance 分单位的String
     * @return 元单位的String
     */
    public static String fromSmallBalanceGetNormalBalance(String smallBalance) {
        StringBuilder bigBalance = new StringBuilder(smallBalance);

        if (smallBalance.length() <= 2) {
            float value = Integer.valueOf(smallBalance);
            float fValue = value / 100;

            return String.valueOf(fValue);
        }
        bigBalance.insert(smallBalance.length() - 2, '.');

        return  bigBalance.toString();
    }

    /**
     * 字符串时间转Date
     * @param time String
     * @return Date
     */
    public static Date fromStringToDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
