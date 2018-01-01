package com.ghy.yuedu.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ghy.yuedu.R;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.util.AnimUtil;
import com.ghy.yuedu.util.ColorUtil;
import com.ghy.yuedu.util.NetWorkUtil;
import com.ghy.yuedu.util.SPUtil;
import com.ghy.yuedu.util.SystemBarUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rey.material.widget.Switch;

import cn.bmob.v3.update.BmobUpdateAgent;

public class SetActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.toolbar)
    Toolbar mToolbar;

    @ViewInject(R.id.set_net_layout1)
    RelativeLayout set_net_layout1;//仅WIFI联网

    @ViewInject(R.id.set_net_switch1)
    Switch netSwitch1;//仅WIFI联网

    @ViewInject(R.id.set_other_btn1)
    Button set_other_btn1;//关于
    @ViewInject(R.id.set_other_btn2)
    Button set_other_btn2;//帮助
    @ViewInject(R.id.set_other_btn3)
    Button set_other_btn3;//意见反馈
    @ViewInject(R.id.set_other_btn4)
    Button set_other_btn4;//给个好评

    @ViewInject(R.id.set_btn_app_set1)
    Button set_btn_app_set1;//检查更新
    @ViewInject(R.id.set_btn_app_set2)
    Button set_btn_app_set2;
    @ViewInject(R.id.set_btn_app_set3)
    Button set_btn_app_set3;//activity切换动画效果

    @ViewInject(R.id.set_btn_network1)
    Button set_btn_network1;//系统网络设置

    @ViewInject(R.id.set_look_btn1)
    Button set_look_btn1;//查看启动页
    @ViewInject(R.id.set_look_btn2)
    Button set_look_btn2;//查看引导页
    @ViewInject(R.id.set_look_btn3)
    Button set_look_btn3;//查看测试页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ViewUtils.inject(this);

        SystemBarUtil.setSystemBarColor(this);
        initToolBar();

        initView();

        //加载保存的设置项
        initSaveSetConfig();
    }

    private void initSaveSetConfig() {

        //加载网络设置配置
        if (SPUtil.getBooleanSP(this, Constant.SP_SETTING, Constant.NET_CONNECT_ONLY_WIFI)) {
            netSwitch1.setChecked(true);
        } else {
            netSwitch1.setChecked(false);
        }

    }

    private void initView() {

        set_net_layout1.setOnClickListener(this);
        netSwitch1.setOnClickListener(this);

        set_other_btn1.setOnClickListener(this);
        set_other_btn2.setOnClickListener(this);
        set_other_btn3.setOnClickListener(this);
        set_other_btn4.setOnClickListener(this);

        set_btn_network1.setOnClickListener(this);

        set_btn_app_set1.setOnClickListener(this);
        set_btn_app_set2.setOnClickListener(this);
        set_btn_app_set3.setOnClickListener(this);

        set_look_btn1.setOnClickListener(this);
        set_look_btn2.setOnClickListener(this);
        set_look_btn3.setOnClickListener(this);

    }

    private void initToolBar() {
        mToolbar.setTitle("设置");
        setSupportActionBar(mToolbar);
        int appColor = ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
        mToolbar.setBackgroundColor(appColor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_set, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            overridePendingTransition(R.anim.activity_alpha_in, getActivityOutAnim());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == set_net_layout1) {
            if (netSwitch1.isChecked()) {
                netSwitch1.setChecked(false);
                changeNetworkSet(Constant.NET_CONNECT_ONLY_WIFI, false);
                sendNetConnectBroadcast();
            } else {
                netSwitch1.setChecked(true);
                changeNetworkSet(Constant.NET_CONNECT_ONLY_WIFI, true);
                sendNetConnectBroadcast();
            }
        } else if (view == netSwitch1) {
            if (netSwitch1.isChecked()) {
                changeNetworkSet(Constant.NET_CONNECT_ONLY_WIFI, true);
                sendNetConnectBroadcast();
            } else {
                changeNetworkSet(Constant.NET_CONNECT_ONLY_WIFI, false);
                sendNetConnectBroadcast();
            }
        } else if (view == set_other_btn1) {
            //关于
            startActivity(this, AboutActivity.class);
        } else if (view == set_other_btn2) {
            //帮助
            startActivity(this, HelpActivity.class);
        } else if (view == set_other_btn3) {
            //反馈
            startActivity(this, AdviceActivity.class);
        } else if (view == set_other_btn4) {
            //好评
            gotoMarket();
        } else if (view == set_btn_network1) {
            //打开系统WIFI设置
            setSysNetWork();
        } else if (view == set_btn_app_set2) {
            //保存主题颜色
            int color = Color.parseColor(set_btn_app_set2.getTag().toString());
            SPUtil.saveSP(SetActivity.this, Constant.SP_SETTING, Constant.APP_THEME_COLOR, color);
            showToast("设置成功，重启生效");
        } else if (view == set_btn_app_set3) {
            //activity切换动画效果设置
            startActivity(this, AnimSetActivity.class);
        } else if (view == set_look_btn1) {
            //启动页
            Intent intent = new Intent(this, LaunchActivity.class);
            intent.putExtra("comeFrom", Constant.START_FROM_SETTING);
            startActivity(intent);
            overridePendingTransition(getActivityInAnim(), R.anim.activity_alpha_out);
        } else if (view == set_look_btn2) {
            //引导页
            Intent intent = new Intent(this, LeadActivity.class);
            intent.putExtra("comeFrom", Constant.START_FROM_SETTING);
            startActivity(intent);
            overridePendingTransition(getActivityInAnim(), R.anim.activity_alpha_out);
        } else if (view == set_look_btn3) {
            startActivity(this, TestActivity.class);
        } else if (view == set_btn_app_set1) {
            //检查更新
            checkUpdate();
        }
    }

    /*
    * 检查更新
    * */
    private void checkUpdate() {
        if (NetWorkUtil.checkNetworkState(this)) {
            //网络可用检查更新
            showToast("正在检查更新");
            BmobUpdateAgent.forceUpdate(this);
        } else {
            showToast("无网络连接");
        }
    }

    /*
    * 去电子市场好评
    * */
    private void gotoMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /*
    * 发送网络设置广播
    * */
    private void sendNetConnectBroadcast() {
        Intent mIntent = new Intent(Constant.NET_CONNECT_ONLY_WIFI);
        sendBroadcast(mIntent);
    }

    private void startAnimShow(View view, int animStyle) {
        Animation anim = AnimationUtils.loadAnimation(this, animStyle);
        view.startAnimation(anim);
    }

    private void startAnimHide(final View view, int animStyle) {
        Animation anim = AnimationUtils.loadAnimation(this, animStyle);
        view.startAnimation(anim);
    }

    /*
    * 打开系统WIFI设置
    * */
    private void setSysNetWork() {
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    /*
    * 保存推送设置
    * */
    private void changePushSet(String s, boolean b) {
        SPUtil.saveSP(this, Constant.SP_SETTING, s, b);
    }

    /*
    * 保存网络配置
    * */
    private void changeNetworkSet(String s, boolean b) {
        SPUtil.saveSP(this, Constant.SP_SETTING, s, b);
    }


    private int getAppThemeColor() {
        int color = ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
        return color;
    }

    private void startActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
        overridePendingTransition(getActivityInAnim(), R.anim.activity_alpha_out);
    }

    private int getActivityInAnim() {
        return AnimUtil.getActivityInAnimStyle(this, Constant.SP_SETTING, Constant.ACTIVITY_ANIM_IN_SETTING);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_alpha_in, getActivityOutAnim());
    }

    private int getActivityOutAnim() {
        return AnimUtil.getActivityOutAnimStyle(this, Constant.SP_SETTING, Constant.ACTIVITY_ANIM_OUT_SETTING);
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void showToastLong(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

}
