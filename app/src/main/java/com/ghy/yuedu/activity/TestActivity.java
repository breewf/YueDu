package com.ghy.yuedu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ghy.yuedu.R;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.util.AnimUtil;
import com.ghy.yuedu.util.ColorUtil;
import com.ghy.yuedu.util.SystemBarUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.ProgressView;
import com.rey.material.widget.SnackBar;

import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.toolbar)
    Toolbar mToolbar;
    @ViewInject(R.id.test_btn1)
    Button test_btn1;
    @ViewInject(R.id.test_btn2)
    Button test_btn2;
    @ViewInject(R.id.test_btn3)
    Button test_btn3;
    @ViewInject(R.id.test_btn4)
    Button test_btn4;
    @ViewInject(R.id.test_btn5)
    Button test_btn5;
    @ViewInject(R.id.test_btn6)
    Button test_btn6;
    @ViewInject(R.id.test_btn7)
    Button test_btn7;
    @ViewInject(R.id.test_btn8)
    Button test_btn8;

    @ViewInject(R.id.button_bt_float_color)
    FloatingActionButton button_bt_float_color;
    @ViewInject(R.id.button_bt_float)
    FloatingActionButton button_bt_float;
    @ViewInject(R.id.button_bt_float_wave)
    FloatingActionButton button_bt_float_wave;
    @ViewInject(R.id.button_bt_float_wave_color)
    FloatingActionButton button_bt_float_wave_color;

    @ViewInject(R.id.btn_activity1)
    Button btn_activity1;
    @ViewInject(R.id.btn_activity2)
    Button btn_activity2;


    @ViewInject(R.id.test_btn_snackBar)
    Button test_btn_snackBar;
    @ViewInject(R.id.test_btn_snackBar2)
    Button test_btn_snackBar2;
    @ViewInject(R.id.main_sn)
    SnackBar mSnackBar;
    @ViewInject(R.id.main_sn2)
    SnackBar mSnackBar2;

    boolean ProgressViewFlag=false;
    @ViewInject(R.id.progress_pv_linear)
    ProgressView progress_pv_linear;
    @ViewInject(R.id.progress_pv_linear_colors)
    ProgressView progress_pv_linear_colors;

    @ViewInject(R.id.progress_pv_linear_determinate)
    ProgressView progress_pv_linear_determinate;
    @ViewInject(R.id.progress_pv_linear_buffer)
    ProgressView progress_pv_linear_buffer;
    float progressSize=0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ViewUtils.inject(this);

        SystemBarUtil.setSystemBarColor(this);
        initToolBar();

        initView();

        initFloatingActionButton();

        initProgressView();

    }

    private void initProgressView() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (ProgressViewFlag) {
                    handler.sendEmptyMessage(1);
                    ProgressViewFlag = false;
                } else {
                    handler.sendEmptyMessage(2);
                    ProgressViewFlag = true;
                }
            }
        }, 0, 4000);

        progress_pv_linear_determinate.start();
        progress_pv_linear_buffer.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (progressSize < 1f) {
                    progressSize += 0.01f;
                    handler.sendEmptyMessage(3);
                } else {
                    progressSize = 0f;
                }
            }
        }, 0, 100);

    }

    private void initFloatingActionButton() {

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v instanceof FloatingActionButton) {
                    FloatingActionButton bt = (FloatingActionButton) v;
                    bt.setLineMorphingState((bt.getLineMorphingState() + 1) % 2, true);
                }
            }
        };

        button_bt_float.setOnClickListener(listener);
        button_bt_float_color.setBackgroundColor(getAppThemeColor());
        button_bt_float_color.setOnClickListener(listener);

        button_bt_float_wave.setOnClickListener(listener);
        button_bt_float_wave_color.setBackgroundColor(getAppThemeColor());
        button_bt_float_wave_color.setOnClickListener(listener);

    }

    private void initView() {
        test_btn1.setOnClickListener(this);
        test_btn2.setOnClickListener(this);
        test_btn3.setOnClickListener(this);
        test_btn4.setOnClickListener(this);
        test_btn5.setOnClickListener(this);
        test_btn6.setOnClickListener(this);
        test_btn7.setOnClickListener(this);
        test_btn8.setOnClickListener(this);


        btn_activity1.setOnClickListener(this);
        btn_activity2.setOnClickListener(this);

        test_btn_snackBar.setOnClickListener(this);
        test_btn_snackBar2.setOnClickListener(this);


    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    progress_pv_linear.stop();
                    progress_pv_linear_colors.stop();
                    break;
                case 2:
                    progress_pv_linear.start();
                    progress_pv_linear_colors.start();
                    break;
                case 3:
                    progress_pv_linear_determinate.setProgress(progressSize);
                    progress_pv_linear_buffer.setProgress(progressSize);
                    progress_pv_linear_buffer.setSecondaryProgress(progressSize + 0.1f);
                    break;
            }
        }
    };

    private void initToolBar() {
        mToolbar.setTitle("测试页面");
        setSupportActionBar(mToolbar);
        int appColor = ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
        mToolbar.setBackgroundColor(appColor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_test, menu);
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

    private int getAppThemeColor() {
        return ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
    }

    @Override
    public void onClick(View view) {
        if (view == test_btn1) {
            new SweetAlertDialog(this).setTitleText("标题文本信息").setConfirmText("确定").show();
        } else if (view == test_btn2) {
            new SweetAlertDialog(this)
                    .setTitleText("加点内容好不好？")
                    .setContentText("我是内容，喜欢吗？")
                    .setConfirmText("喜欢")
                    .show();
        } else if (view == test_btn3) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("错误发生了")
                    .setContentText("Something go wrong!")
                    .setConfirmText("我知道了")
                    .show();
        } else if (view == test_btn4) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("你确定吗？")
                    .setContentText("警告！您正在造成不可预知的后果！")
                    .setConfirmText("知道啦")
                    .show();
        } else if (view == test_btn5) {
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("干的漂亮！")
                    .setContentText("You clicked the button!")
                    .setConfirmText("嗯嗯")
                    .setCancelText("低调")
                    .show();
        } else if (view == test_btn6) {
            new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("自定义图标")
                    .setContentText("我是一个自定义的图标，虽然不是很好看！")
                    .setConfirmText("还可以啦")
                    .setCustomImage(R.drawable.ic_launcher)
                    .show();
        } else if (view == test_btn7) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("监听取消和确定按钮点击事件")
                    .setConfirmText("确定")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Toast.makeText(TestActivity.this, "您点击了确定", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setCancelText("取消")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            Toast.makeText(TestActivity.this, "您点击了取消", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        } else if (view == test_btn8) {
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("拼命加载中...");
            pDialog.setCancelable(true);
            pDialog.show();
        } else if (view == btn_activity1) {
            startActivity(new Intent(TestActivity.this, ScrollViewToolbarWithImageActivity.class));
            overridePendingTransition(getActivityInAnim(), R.anim.activity_alpha_out);
        } else if (view == btn_activity2) {
            startActivity(new Intent(TestActivity.this, ScrollViewToolbarActivity.class));
            overridePendingTransition(getActivityInAnim(), R.anim.activity_alpha_out);
        } else if (view == test_btn_snackBar) {
            //弹出单行snackBar
            mSnackBar.applyStyle(R.style.SnackBarSingleLine)
                    .text("这是一个单行的文本内容")
                    .actionTextColor(getResources().getColor(R.color.white))
                    .actionText("取消")
                    .duration(0)
                    .backgroundColor(getAppThemeColor())
                    .show();
        } else if (view == test_btn_snackBar2) {
            //弹出多行snackBar
            mSnackBar2.applyStyle(R.style.SnackBarMultiLine)
                    .text("这是一个多行的文本内容\n没有取消按钮4秒后自动消失")
                    .actionText(null)
                    .backgroundColor(getAppThemeColor())
                    .duration(4000)
                    .show();
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
