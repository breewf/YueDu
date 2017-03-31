package com.ghy.yuedu.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GHY on 2015/4/17.
 * 共享文件参数工具类
 */
public class SPUtil {

    /*
    * 保存共享文件参数 String
    * */
    public static void saveSP(Context context,String spName,String key,String value){

        SharedPreferences preferences=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    /*
    * 保存共享文件参数 boolean
    * */
    public static void saveSP(Context context,String spName,String key,Boolean value){

        SharedPreferences preferences=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /*
    * 保存共享文件参数 int
    * */
    public static void saveSP(Context context,String spName,String key,Integer value){

        SharedPreferences preferences=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /*
    * 获取共享文件参数 string
    * */
    public static String getStringSP(Context context,String spName,String key){
        SharedPreferences preferences=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        return preferences.getString(key,"");
    }

    /*
    * 获取共享文件参数 int
    * */
    public static int getIntSP(Context context,String spName,String key){
        SharedPreferences preferences=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        return preferences.getInt(key, -1);
    }

    /*
    * 获取共享文件参数 boolean
    * */
    public static boolean getBooleanSP(Context context,String spName,String key){
        SharedPreferences preferences=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    /*
   *  App是否第一次运行，默认为true
   * */
    public static boolean getBooleanFirstRun(Context context,String spName,String key){
        SharedPreferences preferences=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        return preferences.getBoolean(key,true);
    }

    /*
   * 是否允许推送 默认为true
   * */
    public static boolean getPushSP(Context context,String spName,String key){
        SharedPreferences preferences=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        return preferences.getBoolean(key, true);
    }

    /*
   * App activity切换动画效果
   * */
    public static int getActivityAnimSP(Context context,String spName,String key){
        SharedPreferences preferences=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        return preferences.getInt(key, -1);
    }


}
