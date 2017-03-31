package com.ghy.yuedu.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ghy.yuedu.R;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.util.AnimUtil;
import com.ghy.yuedu.util.ColorUtil;
import com.ghy.yuedu.util.ViewUtil;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.view.ViewHelper;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class ScrollViewToolbarWithImageActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    @ViewInject(R.id.image_scroll_toolbar)
    private View mImageView;
    @ViewInject(R.id.toolbar_scroll_withImage)
    private View mToolbarView;
    @ViewInject(R.id.scroll_toolbar_image)
    private ObservableScrollView mScrollView;

    private int mParallaxImageHeight;

    SystemBarTintManager mTintManager;
    boolean mTintFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view_toolbar_with_image);
        ViewUtils.inject(this);

        setSystemBar();

        initScrollViewToolbarWithImage();


    }

    private void setSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Android 4.4以上版本支持
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintFlag=true;
        }else {
            mTintFlag=false;
        }
    }

    private void initScrollViewToolbarWithImage() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_scroll_withImage));
        if (mTintFlag){
            mTintManager.setTintColor(ColorUtil.getColorWithAlpha(0, getAppThemeColor()));
        }
        //获取状态栏的高度，设置toolbar的距离
        int statusHeight = ViewUtil.getStatusHeight(this);
        mToolbarView.setPadding(0, statusHeight, 0, 0);
        mToolbarView.setBackgroundColor(ColorUtil.getColorWithAlpha(0, getAppThemeColor()));
        mScrollView.setScrollViewCallbacks(this);
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    private int getAppThemeColor() {
        return ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_scroll_view_toolbar_with_image, menu);
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

    /*
    * ObservableScrollViewCallbacks实现方法
    * */
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getAppThemeColor();
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        if (mTintFlag){
            //状态栏颜色渐变，从透明---->>>主题颜色
            mTintManager.setTintColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        }
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

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
