package com.ghy.yuedu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ghy.yuedu.MainActivity;
import com.ghy.yuedu.R;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.global.Quotations;
import com.ghy.yuedu.util.AnimUtil;
import com.ghy.yuedu.util.SPUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.Random;

/*
* 起始页
* */
public class LaunchActivity extends Activity {

    @ViewInject(R.id.tv_launch_each_start)
    ShimmerTextView tv_launch_each_start;
    Shimmer shimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ViewUtils.inject(this);

        shimmer = new Shimmer().setStartDelay(400).setDuration(4000);

        int whereStartFlag = getIntent().getFlags();
        if (whereStartFlag == Constant.START_FROM_SETTING) {
            //从设置项启动
            tv_launch_each_start.setText(Quotations.sentences[RandomNumber()]);
            shimmer.start(tv_launch_each_start);
        } else {
            //正常启动
            initLaunch();
        }


    }

    private void initLaunch() {
        if (checkIsFirstRun()) {
            //第一次启动app
            tv_launch_each_start.setText(Quotations.firstRun);
            shimmer.start(tv_launch_each_start);

            //写入false
            SPUtil.saveSP(this, Constant.SP_SETTING, Constant.FIRST_RUN_APP, false);
            postDelayedStartActivity(4, LeadActivity.class);
        } else {
            tv_launch_each_start.setText(Quotations.sentences[RandomNumber()]);
            shimmer.start(tv_launch_each_start);

            postDelayedStartActivity(4, MainActivity.class);
        }

    }

    /*
    * 延时启动Activity
    * */
    private void postDelayedStartActivity(int second, final Class<?> activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchActivity.this, activity));
                finish();
            }
        }, second * 1000);
    }

    private boolean checkIsFirstRun() {
        return SPUtil.getBooleanFirstRun(this, Constant.SP_SETTING, Constant.FIRST_RUN_APP);
    }

    /*
    * 生成一个随机数
    * */
    private int RandomNumber() {
        Random random = new Random();
        return random.nextInt(50);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shimmer.cancel();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_alpha_in, getActivityOutAnim());
    }

    private int getActivityOutAnim() {
        return AnimUtil.getActivityOutAnimStyle(this, Constant.SP_SETTING, Constant.ACTIVITY_ANIM_OUT_SETTING);
    }
}
