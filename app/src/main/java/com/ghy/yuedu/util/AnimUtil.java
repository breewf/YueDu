package com.ghy.yuedu.util;

import android.content.Context;

import com.ghy.yuedu.R;

/**
 * Created by GHY on 2015/5/28.
 */
public class AnimUtil {

    /*
    * 获取activity进入动画
    * */
    public static int getActivityInAnimStyle(Context context,String spName,String key){
        int startAnimStyle=SPUtil.getActivityAnimSP(context, spName,key);
        if (startAnimStyle==-1){
            //使用默认
            return R.anim.activity_in_scale_from_center;
        }else {
            return startAnimStyle;
        }
    }

    /*
    * 获取activity退出动画
    * */
    public static int getActivityOutAnimStyle(Context context,String spName,String key){
        int startAnimStyle=SPUtil.getActivityAnimSP(context, spName,key);
        if (startAnimStyle==-1){
            //使用默认
            return R.anim.activity_out_scale_from_center;
        }else {
            return startAnimStyle;
        }
    }
}
