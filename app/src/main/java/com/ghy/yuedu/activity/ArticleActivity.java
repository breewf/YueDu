package com.ghy.yuedu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ghy.yuedu.R;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.util.AnimUtil;
import com.ghy.yuedu.util.ColorUtil;
import com.ghy.yuedu.util.SystemBarUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ArticleActivity extends AppCompatActivity {

    @ViewInject(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ViewUtils.inject(this);

        SystemBarUtil.setSystemBarColor(this);
        initToolBar();
    }

    private void initToolBar() {
        mToolbar.setTitle("文章");
        setSupportActionBar(mToolbar);
        int appColor= ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
        mToolbar.setBackgroundColor(appColor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_article, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

}
