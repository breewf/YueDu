package com.ghy.yuedu.util;

import android.app.Activity;
import android.os.Build;

import com.ghy.yuedu.global.Constant;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by GHY on 2015/5/13.
 */
public class SystemBarUtil {

    public static void setSystemBarColor(Activity activity){
        SystemBarTintManager mTintManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Android 4.4以上版本支持
            mTintManager = new SystemBarTintManager(activity);
            mTintManager.setStatusBarTintEnabled(true);
            int appThemeColor=ColorUtil.getAppThemeColor(activity, Constant.SP_SETTING,Constant.APP_THEME_COLOR);
            mTintManager.setTintColor(appThemeColor);
        }else {
            //do nothing...
        }
    }

}
