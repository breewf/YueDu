package com.ghy.yuedu.util;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ghy.yuedu.R;

/**
 * Created by GHY on 2015/4/21.
 * 颜色工具类
 */
public class ColorUtil {

    /*
    * 获取主题颜色
    * */
    public static int getAppThemeColor(Context context,String spName,String key){
        int appThemeColor;//应用主题颜色
        //加载设置项中保存的主题颜色
        if (SPUtil.getIntSP(context, spName, key)==-1){
            //应用未设置过主题色，使用默认颜色
            appThemeColor=context.getResources().getColor(R.color.gray3);
        }else {
            appThemeColor=SPUtil.getIntSP(context, spName, key);
        }
        return appThemeColor;
    }

    /*
    * 设置下拉刷新颜色
    * */
    public static void setSwipeRefreshColor(SwipeRefreshLayout swipeRefreshLayout){
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_purple);
    }

    /*
    * 颜色渐变
    * */
    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

}
