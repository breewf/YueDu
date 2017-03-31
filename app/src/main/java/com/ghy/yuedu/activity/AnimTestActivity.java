package com.ghy.yuedu.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ghy.yuedu.R;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.util.ColorUtil;
import com.ghy.yuedu.util.SystemBarUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AnimTestActivity extends AppCompatActivity {

    @ViewInject(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_test);
        ViewUtils.inject(this);

        SystemBarUtil.setSystemBarColor(this);
        initToolBar();

        int getFlag=getIntent().getFlags();
        final int getExitStyle=getIntent().getIntExtra("exitStyle",0);

        if (getFlag==2){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    if (getExitStyle!=0){
                        overridePendingTransition(R.anim.activity_alpha_in,
                                getExitStyle);
                    }
                }
            },1400);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    overridePendingTransition(R.anim.activity_alpha_in,
                            R.anim.activity_alpha_out);
                }
            },1400);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_anim_test, menu);
        return true;
    }

    private void initToolBar() {
        mToolbar.setTitle("页面切换动画预览");
        setSupportActionBar(mToolbar);
        int appColor= ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
        mToolbar.setBackgroundColor(appColor);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
