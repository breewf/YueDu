package com.ghy.yuedu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ghy.yuedu.R;
import com.ghy.yuedu.global.AnimName;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.util.AnimUtil;
import com.ghy.yuedu.util.ColorUtil;
import com.ghy.yuedu.util.SPUtil;
import com.ghy.yuedu.util.SystemBarUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AnimSetActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnLongClickListener {

    @ViewInject(R.id.toolbar)
    Toolbar mToolbar;

    @ViewInject(R.id.set_btn_anim_in_set1)
    Button set_btn_anim_in_set1;
    @ViewInject(R.id.set_btn_anim_in_set2)
    Button set_btn_anim_in_set2;
    @ViewInject(R.id.set_btn_anim_in_set3)
    Button set_btn_anim_in_set3;
    @ViewInject(R.id.set_btn_anim_in_set4)
    Button set_btn_anim_in_set4;
    @ViewInject(R.id.set_btn_anim_in_set5)
    Button set_btn_anim_in_set5;
    @ViewInject(R.id.set_btn_anim_in_set6)
    Button set_btn_anim_in_set6;
    @ViewInject(R.id.set_btn_anim_in_set7)
    Button set_btn_anim_in_set7;
    @ViewInject(R.id.set_btn_anim_in_set8)
    Button set_btn_anim_in_set8;
    @ViewInject(R.id.set_btn_anim_in_set9)
    Button set_btn_anim_in_set9;
    @ViewInject(R.id.set_btn_anim_in_set10)
    Button set_btn_anim_in_set10;
    @ViewInject(R.id.set_btn_anim_in_set11)
    Button set_btn_anim_in_set11;
    @ViewInject(R.id.set_btn_anim_in_set12)
    Button set_btn_anim_in_set12;
    @ViewInject(R.id.set_btn_anim_in_set13)
    Button set_btn_anim_in_set13;
    @ViewInject(R.id.set_btn_anim_in_set14)
    Button set_btn_anim_in_set14;
    @ViewInject(R.id.set_btn_anim_in_set15)
    Button set_btn_anim_in_set15;

    @ViewInject(R.id.set_btn_anim_out_set1)
    Button set_btn_anim_out_set1;
    @ViewInject(R.id.set_btn_anim_out_set2)
    Button set_btn_anim_out_set2;
    @ViewInject(R.id.set_btn_anim_out_set3)
    Button set_btn_anim_out_set3;
    @ViewInject(R.id.set_btn_anim_out_set4)
    Button set_btn_anim_out_set4;
    @ViewInject(R.id.set_btn_anim_out_set5)
    Button set_btn_anim_out_set5;
    @ViewInject(R.id.set_btn_anim_out_set6)
    Button set_btn_anim_out_set6;
    @ViewInject(R.id.set_btn_anim_out_set7)
    Button set_btn_anim_out_set7;
    @ViewInject(R.id.set_btn_anim_out_set8)
    Button set_btn_anim_out_set8;
    @ViewInject(R.id.set_btn_anim_out_set9)
    Button set_btn_anim_out_set9;
    @ViewInject(R.id.set_btn_anim_out_set10)
    Button set_btn_anim_out_set10;
    @ViewInject(R.id.set_btn_anim_out_set11)
    Button set_btn_anim_out_set11;
    @ViewInject(R.id.set_btn_anim_out_set12)
    Button set_btn_anim_out_set12;
    @ViewInject(R.id.set_btn_anim_out_set13)
    Button set_btn_anim_out_set13;
    @ViewInject(R.id.set_btn_anim_out_set14)
    Button set_btn_anim_out_set14;
    @ViewInject(R.id.set_btn_anim_out_set15)
    Button set_btn_anim_out_set15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_set);
        ViewUtils.inject(this);

        SystemBarUtil.setSystemBarColor(this);
        initToolBar();

        setClickListener();
    }

    private void setClickListener() {
        set_btn_anim_in_set1.setOnClickListener(this);
        set_btn_anim_in_set2.setOnClickListener(this);
        set_btn_anim_in_set3.setOnClickListener(this);
        set_btn_anim_in_set4.setOnClickListener(this);
        set_btn_anim_in_set5.setOnClickListener(this);
        set_btn_anim_in_set6.setOnClickListener(this);
        set_btn_anim_in_set7.setOnClickListener(this);
        set_btn_anim_in_set8.setOnClickListener(this);
        set_btn_anim_in_set9.setOnClickListener(this);
        set_btn_anim_in_set10.setOnClickListener(this);
        set_btn_anim_in_set11.setOnClickListener(this);
        set_btn_anim_in_set12.setOnClickListener(this);
        set_btn_anim_in_set13.setOnClickListener(this);
        set_btn_anim_in_set14.setOnClickListener(this);
        set_btn_anim_in_set15.setOnClickListener(this);


        set_btn_anim_out_set1.setOnClickListener(this);
        set_btn_anim_out_set2.setOnClickListener(this);
        set_btn_anim_out_set3.setOnClickListener(this);
        set_btn_anim_out_set4.setOnClickListener(this);
        set_btn_anim_out_set5.setOnClickListener(this);
        set_btn_anim_out_set6.setOnClickListener(this);
        set_btn_anim_out_set7.setOnClickListener(this);
        set_btn_anim_out_set8.setOnClickListener(this);
        set_btn_anim_out_set9.setOnClickListener(this);
        set_btn_anim_out_set10.setOnClickListener(this);
        set_btn_anim_out_set11.setOnClickListener(this);
        set_btn_anim_out_set12.setOnClickListener(this);
        set_btn_anim_out_set13.setOnClickListener(this);
        set_btn_anim_out_set14.setOnClickListener(this);
        set_btn_anim_out_set15.setOnClickListener(this);


        set_btn_anim_in_set1.setOnLongClickListener(this);
        set_btn_anim_in_set2.setOnLongClickListener(this);
        set_btn_anim_in_set3.setOnLongClickListener(this);
        set_btn_anim_in_set4.setOnLongClickListener(this);
        set_btn_anim_in_set5.setOnLongClickListener(this);
        set_btn_anim_in_set6.setOnLongClickListener(this);
        set_btn_anim_in_set7.setOnLongClickListener(this);
        set_btn_anim_in_set8.setOnLongClickListener(this);
        set_btn_anim_in_set9.setOnLongClickListener(this);
        set_btn_anim_in_set10.setOnLongClickListener(this);
        set_btn_anim_in_set11.setOnLongClickListener(this);
        set_btn_anim_in_set12.setOnLongClickListener(this);
        set_btn_anim_in_set13.setOnLongClickListener(this);
        set_btn_anim_in_set14.setOnLongClickListener(this);
        set_btn_anim_in_set15.setOnLongClickListener(this);


        set_btn_anim_out_set1.setOnLongClickListener(this);
        set_btn_anim_out_set2.setOnLongClickListener(this);
        set_btn_anim_out_set3.setOnLongClickListener(this);
        set_btn_anim_out_set4.setOnLongClickListener(this);
        set_btn_anim_out_set5.setOnLongClickListener(this);
        set_btn_anim_out_set6.setOnLongClickListener(this);
        set_btn_anim_out_set7.setOnLongClickListener(this);
        set_btn_anim_out_set8.setOnLongClickListener(this);
        set_btn_anim_out_set9.setOnLongClickListener(this);
        set_btn_anim_out_set10.setOnLongClickListener(this);
        set_btn_anim_out_set11.setOnLongClickListener(this);
        set_btn_anim_out_set12.setOnLongClickListener(this);
        set_btn_anim_out_set13.setOnLongClickListener(this);
        set_btn_anim_out_set14.setOnLongClickListener(this);
        set_btn_anim_out_set15.setOnLongClickListener(this);
    }

    private void initToolBar() {
        mToolbar.setTitle("页面切换动画设置");
        setSupportActionBar(mToolbar);
        int appColor = ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
        mToolbar.setBackgroundColor(appColor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_anim_set, menu);
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_alpha_in, getActivityOutAnim());
    }

    private int getActivityOutAnim() {
        return AnimUtil.getActivityOutAnimStyle(this, Constant.SP_SETTING, Constant.ACTIVITY_ANIM_OUT_SETTING);
    }

    private void startActivity(int animStyle) {
        Intent intent = new Intent(this, AnimTestActivity.class);
        startActivity(intent);
        overridePendingTransition(animStyle, R.anim.activity_alpha_out);
    }

    private void startActivity2(int animStyle) {
        Intent intent = new Intent(this, AnimTestActivity.class);
        intent.setFlags(2);
        intent.putExtra("exitStyle", animStyle);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_alpha_out);
    }

    @Override
    public void onClick(View view) {
        if (view == set_btn_anim_in_set1) {
            startActivity(AnimName.activity_in_scale_from_center);
        } else if (view == set_btn_anim_in_set2) {
            startActivity(AnimName.activity_in_rotate_scale_from_center);
        } else if (view == set_btn_anim_in_set3) {
            startActivity(AnimName.activity_in_scale_from_bottom_left);
        } else if (view == set_btn_anim_in_set4) {
            startActivity(AnimName.activity_in_scale_from_bottom_right);
        } else if (view == set_btn_anim_in_set5) {
            startActivity(AnimName.activity_in_scale_from_top_left);
        } else if (view == set_btn_anim_in_set6) {
            startActivity(AnimName.activity_in_scale_from_top_right);
        } else if (view == set_btn_anim_in_set7) {
            startActivity(AnimName.activity_in_translate_from_bottom);
        } else if (view == set_btn_anim_in_set8) {
            startActivity(AnimName.activity_in_translate_from_left);
        } else if (view == set_btn_anim_in_set9) {
            startActivity(AnimName.activity_in_translate_from_right);
        } else if (view == set_btn_anim_in_set10) {
            startActivity(AnimName.activity_in_translate_from_top);
        } else if (view == set_btn_anim_in_set11) {
            startActivity(AnimName.activity_in_translate_scale_from_bottom);
        } else if (view == set_btn_anim_in_set12) {
            startActivity(AnimName.activity_in_translate_scale_from_left);
        } else if (view == set_btn_anim_in_set13) {
            startActivity(AnimName.activity_in_translate_scale_from_right);
        } else if (view == set_btn_anim_in_set14) {
            startActivity(AnimName.activity_in_translate_scale_from_top);
        } else if (view == set_btn_anim_in_set15) {
            startActivity(AnimName.activity_alpha_in);
        } else if (view == set_btn_anim_out_set1) {
            startActivity2(AnimName.activity_out_scale_from_center);
        } else if (view == set_btn_anim_out_set2) {
            startActivity2(AnimName.activity_out_rotate_scale_from_center);
        } else if (view == set_btn_anim_out_set3) {
            startActivity2(AnimName.activity_out_scale_to_bottom_left);
        } else if (view == set_btn_anim_out_set4) {
            startActivity2(AnimName.activity_out_scale_to_bottom_right);
        } else if (view == set_btn_anim_out_set5) {
            startActivity2(AnimName.activity_out_scale_to_top_left);
        } else if (view == set_btn_anim_out_set6) {
            startActivity2(AnimName.activity_out_scale_to_top_right);
        } else if (view == set_btn_anim_out_set7) {
            startActivity2(AnimName.activity_out_translate_scale_to_bottom);
        } else if (view == set_btn_anim_out_set8) {
            startActivity2(AnimName.activity_out_translate_scale_to_left);
        } else if (view == set_btn_anim_out_set9) {
            startActivity2(AnimName.activity_out_translate_scale_to_right);
        } else if (view == set_btn_anim_out_set10) {
            startActivity2(AnimName.activity_out_translate_scale_to_top);
        } else if (view == set_btn_anim_out_set11) {
            startActivity2(AnimName.activity_out_translate_to_bottom);
        } else if (view == set_btn_anim_out_set12) {
            startActivity2(AnimName.activity_out_translate_to_left);
        } else if (view == set_btn_anim_out_set13) {
            startActivity2(AnimName.activity_out_translate_to_right);
        } else if (view == set_btn_anim_out_set14) {
            startActivity2(AnimName.activity_out_translate_to_top);
        } else if (view == set_btn_anim_out_set15) {
            startActivity2(AnimName.activity_alpha_out);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view == set_btn_anim_in_set1) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_scale_from_center);

        } else if (view == set_btn_anim_in_set2) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_rotate_scale_from_center);
        } else if (view == set_btn_anim_in_set3) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_scale_from_bottom_left);
        } else if (view == set_btn_anim_in_set4) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_scale_from_bottom_right);
        } else if (view == set_btn_anim_in_set5) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_scale_from_top_left);
        } else if (view == set_btn_anim_in_set6) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_scale_from_top_right);
        } else if (view == set_btn_anim_in_set7) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_translate_from_bottom);
        } else if (view == set_btn_anim_in_set8) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_translate_from_left);
        } else if (view == set_btn_anim_in_set9) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_translate_from_right);
        } else if (view == set_btn_anim_in_set10) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_translate_from_top);
        } else if (view == set_btn_anim_in_set11) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_translate_scale_from_bottom);
        } else if (view == set_btn_anim_in_set12) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_translate_scale_from_left);
        } else if (view == set_btn_anim_in_set13) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_translate_scale_from_right);
        } else if (view == set_btn_anim_in_set14) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_in_translate_scale_from_top);
        } else if (view == set_btn_anim_in_set15) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_IN_SETTING, AnimName.activity_alpha_in);
        } else if (view == set_btn_anim_out_set1) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_scale_from_center);
        } else if (view == set_btn_anim_out_set2) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_rotate_scale_from_center);
        } else if (view == set_btn_anim_out_set3) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_scale_to_bottom_left);
        } else if (view == set_btn_anim_out_set4) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_scale_to_bottom_right);
        } else if (view == set_btn_anim_out_set5) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_scale_to_top_left);
        } else if (view == set_btn_anim_out_set6) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_scale_to_top_right);
        } else if (view == set_btn_anim_out_set7) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_translate_scale_to_bottom);
        } else if (view == set_btn_anim_out_set8) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_translate_scale_to_left);
        } else if (view == set_btn_anim_out_set9) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_translate_scale_to_right);
        } else if (view == set_btn_anim_out_set10) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_translate_scale_to_top);
        } else if (view == set_btn_anim_out_set11) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_translate_to_bottom);
        } else if (view == set_btn_anim_out_set12) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_translate_to_left);
        } else if (view == set_btn_anim_out_set13) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_translate_to_right);
        } else if (view == set_btn_anim_out_set14) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_out_translate_to_top);
        } else if (view == set_btn_anim_out_set15) {
            SPUtil.saveSP(this, Constant.SP_SETTING,
                    Constant.ACTIVITY_ANIM_OUT_SETTING, AnimName.activity_alpha_out);
        }
        Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
        return true;
    }
}
