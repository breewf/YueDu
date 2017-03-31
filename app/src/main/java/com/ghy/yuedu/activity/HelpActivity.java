package com.ghy.yuedu.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ghy.yuedu.R;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.util.AnimUtil;
import com.ghy.yuedu.util.ColorUtil;
import com.ghy.yuedu.util.SystemBarUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rey.material.widget.FloatingActionButton;

public class HelpActivity extends AppCompatActivity {

    @ViewInject(R.id.toolbar)
    Toolbar mToolbar;

    @ViewInject(R.id.btn_float_action_button)
    FloatingActionButton btn_float_action_button;//展开折叠按钮
    boolean isClick = false;

    @ViewInject(R.id.set_help_btn1)
    Button set_help_btn1;
    @ViewInject(R.id.set_help_btn2)
    Button set_help_btn2;
    @ViewInject(R.id.set_help_btn3)
    Button set_help_btn3;
    @ViewInject(R.id.set_help_btn4)
    Button set_help_btn4;
    @ViewInject(R.id.set_help_btn5)
    Button set_help_btn5;
    @ViewInject(R.id.set_help_btn6)
    Button set_help_btn6;

    TextView[] tv_set;

    @ViewInject(R.id.set_tv_help1)
    TextView set_tv_help1;
    @ViewInject(R.id.set_tv_help2)
    TextView set_tv_help2;
    @ViewInject(R.id.set_tv_help3)
    TextView set_tv_help3;
    @ViewInject(R.id.set_tv_help4)
    TextView set_tv_help4;
    @ViewInject(R.id.set_tv_help5)
    TextView set_tv_help5;
    @ViewInject(R.id.set_tv_help6)
    TextView set_tv_help6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ViewUtils.inject(this);

        SystemBarUtil.setSystemBarColor(this);
        initToolBar();

        initView();

        initFloatingActionButton();

    }

    private void initFloatingActionButton() {
        btn_float_action_button.setBackgroundColor(getAppThemeColor());
        btn_float_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof FloatingActionButton) {
                    FloatingActionButton bt = (FloatingActionButton) v;
                    bt.setLineMorphingState((bt.getLineMorphingState() + 1) % 2, true);
                }
                if (!isClick) {
                    isClick = true;
                    showToast("展开全部问题");
                    allCanVisible();
                } else {
                    isClick = false;
                    showToast("折叠全部问题");
                    allCanNotVisible();
                }
            }
        });
    }

    private void allCanNotVisible() {
        for (int i = 0; i < tv_set.length; i++) {
            tv_set[i].setVisibility(View.GONE);
        }
    }

    private void allCanVisible() {
        for (int i = 0; i < tv_set.length; i++) {
            tv_set[i].setVisibility(View.VISIBLE);
        }
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        tv_set = new TextView[6];
        //展开或折叠全部
        tv_set[0] = set_tv_help1;
        tv_set[1] = set_tv_help2;
        tv_set[2] = set_tv_help3;
        tv_set[3] = set_tv_help4;
        tv_set[4] = set_tv_help5;
        tv_set[5] = set_tv_help6;

    }

    private void initToolBar() {
        mToolbar.setTitle("帮助");
        setSupportActionBar(mToolbar);
        int appColor = ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
        mToolbar.setBackgroundColor(appColor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_help, menu);
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

    public void setHelpBtnClick(View v) {

        if (v == set_help_btn1) {
            setVisibility(set_tv_help1);
        } else if (v == set_help_btn2) {
            setVisibility(set_tv_help2);
        } else if (v == set_help_btn3) {
            setVisibility(set_tv_help3);
        } else if (v == set_help_btn4) {
            setVisibility(set_tv_help4);
        } else if (v == set_help_btn5) {
            setVisibility(set_tv_help5);
        } else if (v == set_help_btn6) {
            setVisibility(set_tv_help6);
        }
    }

    private void setVisibility(final View v) {
        if (v.getVisibility() == View.VISIBLE) {
            startAnimHide(v, R.anim.view_hide_translate_scale_to_left);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    v.setVisibility(View.GONE);
                }
            }, 600);
        } else {
            v.setVisibility(View.VISIBLE);
            startAnimShow(v, R.anim.view_show_translate_scale_from_top);
        }
    }

    private void startAnimShow(View view, int animStyle) {
        Animation anim = AnimationUtils.loadAnimation(this, animStyle);
        view.startAnimation(anim);
    }

    private void startAnimHide(final View view, int animStyle) {
        Animation anim = AnimationUtils.loadAnimation(this, animStyle);
        view.startAnimation(anim);
    }

    private int getAppThemeColor() {
        return ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
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
