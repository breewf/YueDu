package com.ghy.yuedu;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ghy.yuedu.activity.ArticleActivity;
import com.ghy.yuedu.activity.JokeActivity;
import com.ghy.yuedu.activity.MusicActivity;
import com.ghy.yuedu.activity.NewsActivity;
import com.ghy.yuedu.activity.SetActivity;
import com.ghy.yuedu.activity.UserLoginActivity;
import com.ghy.yuedu.activity.VideoActivity;
import com.ghy.yuedu.adapter.HomePagerAdapter;
import com.ghy.yuedu.fragment.DayArticleFragment;
import com.ghy.yuedu.fragment.DayPictureFragment;
import com.ghy.yuedu.fragment.DayRecommendFragment;
import com.ghy.yuedu.fragment.DaySentenceFragment;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.listener.DoubleClickListener;
import com.ghy.yuedu.util.AnimUtil;
import com.ghy.yuedu.util.ColorUtil;
import com.ghy.yuedu.util.NetWorkUtil;
import com.ghy.yuedu.util.SPUtil;
import com.ghy.yuedu.util.SnackUtil;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SVBar;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.rey.material.widget.SnackBar;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.ArrayList;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.pedant.SweetAlert.SweetAlertDialog;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MaterialTabListener {

    public static MainActivity mainInstance;

    private DrawerLayout mDrawerLayout;
    private RelativeLayout drawer_content;//侧滑菜单布局
    private ActionBarDrawerToggle mDrawerToggle;

    private SystemBarTintManager mTintManager;
    private Toolbar mToolbar;

    private ImageView user_head_image;//用户头像
    private LinearLayout layout_user;//用户布局

    private MaterialTabHost tabHost;
    private ViewPager viewPager;
    private HomePagerAdapter adapter;
    private ArrayList<Fragment> fragments;

    /*
    * 网络
    * */
    public static boolean netAvailable;
    public static boolean onlyWifi;


    private SnackBar snack_bar;
    boolean firstStart = true;//避免app刚启动时监听到网络连接的广播

    private LinearLayout layout_no_data_remind;//服务器无当日数据时提醒


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainInstance=this;

        initView();

        initToolBar();

        setSystemBarConfig();

        initAppThemeColor();

        initTabPagerAdapter();

        checkNetwork(true);

        registerNetBroadcast();

        //初始化Bmob
        initBmob();

        //检查app是否有更新
        checkUpdate();

//        FileUtil.getYueDuApkSize();

    }

    private void checkUpdate() {
        //初始化自动创建AppVersion表
        //一旦AppVersion表在后台创建成功，建议屏蔽或删除此方法，否则会生成多行记录
//        BmobUpdateAgent.initAppVersion(this);

        //检测更新
        BmobUpdateAgent.update(this);
    }

    private void registerNetBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.CONNECTIVITY_CHANGE_ACTION);
        filter.addAction(Constant.NET_CONNECT_ONLY_WIFI);
        registerReceiver(netWorkBroadCast, filter);
    }

    private void checkNetwork(boolean firstStart) {
        netAvailable = NetWorkUtil.checkNetworkState(this);
        if (!netAvailable) {
            //网络不可用
            SnackUtil.showSnackBarNetError(this,snack_bar, getAppThemeColor());
        } else {
            //网络可用
            if (!NetWorkUtil.isWifi(this)) {
                //移动网络环境
                boolean onlyWifiConnect = SPUtil.getBooleanSP(this, Constant.SP_SETTING, Constant.NET_CONNECT_ONLY_WIFI);
                if (onlyWifiConnect) {
                    onlyWifi = true;
                    SnackUtil.onlyWifiConnect(this,snack_bar,getAppThemeColor());
                } else {
                    onlyWifi = false;
                    if (!firstStart) {
                        //网络恢复提醒
                        SnackUtil.showSnackBarNetOk(snack_bar, getAppThemeColor());
                    }
                }
            } else {
                //wifi网络
                onlyWifi = false;
                if (!firstStart) {
                    //网络恢复提醒
                    SnackUtil.showSnackBarNetOk(snack_bar, getAppThemeColor());
                }
            }
        }

    }

    /*
    * 网络变化广播
    * */
    private BroadcastReceiver netWorkBroadCast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.CONNECTIVITY_CHANGE_ACTION)) {
                //网络发生变化，重新检查网络
                if (firstStart) {
                    //刚启动App不监听网络变化,避免app刚启动时监听到网络连接的广播
                    firstStart = false;
                } else {
                    checkNetwork(false);
                }
            }

            if (action.equals(Constant.NET_CONNECT_ONLY_WIFI)) {
                checkNetwork(false);
            }
        }
    };

    private void initBmob() {
        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "aa70c8bb5373d9df54d7a3c018be395e");

        //推送反馈
        BmobPush.startWork(this, "aa70c8bb5373d9df54d7a3c018be395e");
    }

    private void setSystemBarConfig() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Android 4.4以上版本支持
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
        } else {
            //do nothing...
        }
    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("悦读");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initTabPagerAdapter() {
        addFragment();
        //缓存多个页面防止fragment被销毁
        viewPager.setOffscreenPageLimit(3);
        adapter = new HomePagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabHost.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(tabHost.newTab()
                    .setText(adapter.getPageTitle(i))
                    .setTabListener(this));
        }


    }

    private void addFragment() {
        fragments = new ArrayList<>();
        DayRecommendFragment dayRecommendFragment = new DayRecommendFragment().newInstance();
        DayArticleFragment articleFragment = new DayArticleFragment().newInstance();
        DaySentenceFragment sentenceFragment = new DaySentenceFragment().newInstance();
        DayPictureFragment pictureFragment = new DayPictureFragment().newInstance();
        fragments.add(dayRecommendFragment);
        fragments.add(articleFragment);
        fragments.add(sentenceFragment);
        fragments.add(pictureFragment);
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_content = (RelativeLayout) findViewById(R.id.drawer_content);
        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        viewPager = (ViewPager) this.findViewById(R.id.pager);
        user_head_image = (ImageView) findViewById(R.id.user_head_image);
        user_head_image.setOnClickListener(this);
        layout_user = (LinearLayout) findViewById(R.id.layout_user);
        layout_user.setOnClickListener(this);


        snack_bar = (SnackBar) findViewById(R.id.snack_bar);
        layout_no_data_remind= (LinearLayout) findViewById(R.id.layout_no_data_remind);

    }

    private void initAppThemeColor() {
        //加载设置项中保存的主题颜色
        int getAppThemeColor = SPUtil.getIntSP(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
        if (getAppThemeColor == -1) {
            //应用未设置过主题色，使用默认颜色
            int appColor = this.getResources().getColor(R.color.gray3);
            setSystemBarColor(appColor);
            mToolbar.setBackgroundColor(appColor);
            tabHost.setPrimaryColor(appColor);
        } else {
            setSystemBarColor(getAppThemeColor());
            mToolbar.setBackgroundColor(getAppThemeColor());
            tabHost.setPrimaryColor(getAppThemeColor());
        }
    }

    private void setSystemBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Android 4.4以上版本支持
            mTintManager.setTintColor(color);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(this, SetActivity.class);
        }
        if (id == R.id.action_exit) {
            affirmExit();
        }
        if (id == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(drawer_content)) {
                mDrawerLayout.closeDrawer(drawer_content);
            } else {
                mDrawerLayout.openDrawer(drawer_content);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void affirmExit() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("确定要退出应用吗？")
                .setContentText("真的有点舍不得呢！")
                .setCancelText("我点错了")
                .setConfirmText("下次再见")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.this.finish();
                            }
                        }, 400);
                    }
                })
                .show();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private long firstClickTime;

    @Override
    public void onBackPressed() {
        if (firstClickTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
        }
        firstClickTime = System.currentTimeMillis();
    }

    @Override
    public void onClick(View view) {
        if (view == user_head_image || view == layout_user) {
            //登陆点击事件
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(MainActivity.this, UserLoginActivity.class);
                }
            }, 400);
            mDrawerLayout.closeDrawer(drawer_content);
        }
    }

    /*
    * 主题颜色设置
    * */
    private void setAppThemeColor() {

        //Create color picker view
        final View view = getLayoutInflater().inflate(R.layout.color_picker_dialog, null);
        if (view == null) return;
        //TextView shimmer
        ShimmerTextView tv_color_picker_title = (ShimmerTextView) view.findViewById(R.id.tv_color_picker_title);
        final Shimmer shimmer = new Shimmer().setStartDelay(400).setDuration(4000);
        shimmer.start(tv_color_picker_title);
        //ColorPicker
        final ColorPicker picker = (ColorPicker) view.findViewById(R.id.picker);
        SVBar svBar = (SVBar) view.findViewById(R.id.svbar);
        final TextView hexCode = (TextView) view.findViewById(R.id.hex_code);

        picker.addSVBar(svBar);
        picker.setOldCenterColor(getAppThemeColor());
        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int intColor) {
                String hexColor = Integer.toHexString(intColor).toUpperCase();
                hexCode.setText("#" + hexColor);
            }
        });
        //Config dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(true);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                shimmer.cancel();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //保存主题颜色
                SPUtil.saveSP(MainActivity.this, Constant.SP_SETTING, Constant.APP_THEME_COLOR, picker.getColor());
                //改变主题颜色
                initAppThemeColor();
                shimmer.cancel();
            }
        });
        builder.create().show();
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.view_show_translate_scale_from_bottom);
        view.startAnimation(anim);
    }

    private int getAppThemeColor() {
        return ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    /*
    * 侧滑菜单按钮点击事件
    * */
    public void drawerBtnClick(final View v) {
        if (mDrawerLayout.isDrawerOpen(drawer_content)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int id = v.getId();
                    if (id == R.id.drawer_btn_news) {
                        startActivity(MainActivity.this, NewsActivity.class);
                    } else if (id == R.id.drawer_btn_article) {
                        startActivity(MainActivity.this, ArticleActivity.class);
                    } else if (id == R.id.drawer_btn_joke) {
                        startActivity(MainActivity.this, JokeActivity.class);
                    } else if (id == R.id.drawer_btn_music) {
                        startActivity(MainActivity.this, MusicActivity.class);
                    } else if (id == R.id.drawer_btn_video) {
                        startActivity(MainActivity.this, VideoActivity.class);
                    } else if (id == R.id.drawer_btn_set) {
                        startActivity(MainActivity.this, SetActivity.class);
                    } else if (id == R.id.drawer_btn_exit) {
                        MainActivity.this.finish();
                    } else if (id == R.id.drawer_btn_set_color) {
                        //更换主题颜色点击事件
                        setAppThemeColor();
                    }
                }
            }, 400);
            mDrawerLayout.closeDrawer(drawer_content);
        }

    }

    private void startActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
        overridePendingTransition(getActivityInAnim(), R.anim.activity_alpha_out);
    }

    private int getActivityInAnim() {
        return AnimUtil.getActivityInAnimStyle(this, Constant.SP_SETTING, Constant.ACTIVITY_ANIM_IN_SETTING);
    }

    /*
    * 服务器是否有当日数据提醒
    * */
    public void setRemindTodayData(boolean isData){
      if (isData){
          layout_no_data_remind.setVisibility(View.GONE);
      }else {
          //图标提醒
          layout_no_data_remind.setVisibility(View.VISIBLE);
          //snackBar提醒
          SnackUtil.noDataToday(snack_bar, getAppThemeColor());
      }
    }

    /*
    * 监听屏幕是否双击
    * */
    private DoubleClickListener doubleClickListener=new DoubleClickListener(new DoubleClickListener.OnDoubleClickListener() {
        @Override
        public void onDoubleClick() {
            //显示popupWindow
            showSetPopupWindow();
        }
    });

    private PopupWindow popupWindow;
    private void showSetPopupWindow() {
        //导入布局文件
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_popup_window,null);
        //设置popupWindow的背景颜色
        LinearLayout layout_popup_window= (LinearLayout) popupView.findViewById(R.id.layout_popup_window);
        layout_popup_window.setBackgroundColor(getAppThemeColor());

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.AnimationPopupWindow);
        popupWindow.showAtLocation(findViewById(R.id.main),
                Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /*
    * 更换背景点击事件
    * */
    public void changeBgClick(View view){
       int id = view.getId();
        switch (id){
            case R.id.img_read_bg0:
                //无样式
                changeBgMethod(getResources().getColor(R.color.white));
                break;
            case R.id.img_read_bg1:
                changeBgMethod(getResources().getColor(R.color.read_bg1));
                break;
            case R.id.img_read_bg4:
                changeBgMethod(getResources().getColor(R.color.read_bg3));
                break;
            case R.id.img_read_bg6:
                changeBgMethod(getResources().getColor(R.color.read_bg4));
                break;
            case R.id.img_read_bg16:
                changeBgMethod(getResources().getColor(R.color.read_bg8));
                break;
            case R.id.img_read_bg17:
                changeBgMethod(getResources().getColor(R.color.read_bg9));
                break;
            case R.id.img_read_bg18:
                changeBgMethod(getResources().getColor(R.color.read_bg10));
                break;
            case R.id.img_read_bg19:
                changeBgMethod(getResources().getColor(R.color.read_bg11));
                break;
            case R.id.img_read_bg20:
                changeBgMethod(getResources().getColor(R.color.read_bg12));
                break;
            case R.id.img_read_bg22:
                changeBgMethod(getResources().getColor(R.color.read_bg14));
                break;
            case R.id.img_read_bg23:
                changeBgMethod(getResources().getColor(R.color.read_bg15));
                break;
            case R.id.img_read_bg24:
                changeBgMethod(getResources().getColor(R.color.read_bg16));
                break;
            case R.id.img_read_bg25:
                changeBgMethod(getResources().getColor(R.color.read_bg17));
                break;
            case R.id.img_read_bg26:
                changeBgMethod(getResources().getColor(R.color.read_bg18));
                break;
            case R.id.img_read_bg27:
                changeBgMethod(getResources().getColor(R.color.read_bg19));
                break;

        }
    }

    private void changeBgMethod(int bgId) {
        //更换背景
        DayRecommendFragment.DRFInstance.changBg(bgId);
        DayArticleFragment.DAFInstance.changBg(bgId);
        DaySentenceFragment.DSFInstance.changBg(bgId);
        DayPictureFragment.DPFInstance.changBg(bgId);
    }

    /*
    * 事件分发
    * */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if(doubleClickListener == null) {
            return super.dispatchTouchEvent(event);
        }else{
            doubleClickListener.dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkBroadCast);
    }
}
