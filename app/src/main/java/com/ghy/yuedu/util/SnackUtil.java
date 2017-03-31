package com.ghy.yuedu.util;

import android.content.Context;
import android.view.View;

import com.ghy.yuedu.R;
import com.rey.material.widget.SnackBar;

/**
 * Created by GHY on 2015/6/3.
 * 底部信息栏工具类
 */
public class SnackUtil {

    public static void showSnackBarNetError(Context context, SnackBar snackBar, int bgColor) {
        if (snackBar.getVisibility() != View.VISIBLE)
            snackBar.applyStyle(R.style.SnackBarSingleLine)
                    .text("无网络连接！  请检查您的网络设置！")
                    .actionText("我知道了")
                    .actionTextColor(context.getResources().getColor(R.color.white))
                    .duration(0)
                    .backgroundColor(bgColor)
                    .show();
    }

    public static void onlyWifiConnect(Context context, SnackBar snackBar, int bgColor) {
        if (snackBar.getVisibility() != View.VISIBLE)
            snackBar.applyStyle(R.style.SnackBarSingleLine)
                    .text("仅WIFI下连接网络！  请检查您的网络设置！")
                    .actionText("我知道了")
                    .actionTextColor(context.getResources().getColor(R.color.white))
                    .duration(0)
                    .backgroundColor(bgColor)
                    .show();
    }

    public static void showSnackBarNetOk(SnackBar snackBar, int bgColor) {
        if (snackBar.getVisibility() != View.VISIBLE)
            snackBar.applyStyle(R.style.SnackBarSingleLine)
                    .text("网络已连接！")
                    .actionText(null)
                    .duration(2000)
                    .backgroundColor(bgColor)
                    .show();
    }

    public static void noDataToday(SnackBar snackBar, int bgColor) {
        if (snackBar.getVisibility() != View.VISIBLE)
            snackBar.applyStyle(R.style.SnackBarMultiLine)
                    .text("抱歉呢！未查询到今日数据！\n我错了！改天再来好不好呢？")
                    .actionText(null)
                    .duration(4000)
                    .backgroundColor(bgColor)
                    .show();
    }

    public static void initDataSuccess(SnackBar snackBar, int bgColor) {
        if (snackBar.getVisibility() != View.VISIBLE)
            snackBar.applyStyle(R.style.SnackBarSingleLine)
                    .text("加载数据成功！  下拉查看往期内容！")
                    .actionText(null)
                    .duration(2000)
                    .backgroundColor(bgColor)
                    .show();
    }

    public static void errorAndCheckNetOrTryLater(SnackBar snackBar, int bgColor) {
        if (snackBar.getVisibility() != View.VISIBLE)
            //数据加载失败
            snackBar.applyStyle(R.style.SnackBarMultiLine)
                    .text("抱歉！数据加载失败！\n请检查您的网络设置或稍后再试！")
                    .actionText(null)
                    .duration(2000)
                    .backgroundColor(bgColor)
                    .show();
    }
}
