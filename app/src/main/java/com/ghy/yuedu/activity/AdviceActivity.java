package com.ghy.yuedu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ghy.yuedu.R;
import com.ghy.yuedu.bean.Feedback;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.util.AnimUtil;
import com.ghy.yuedu.util.ColorUtil;
import com.ghy.yuedu.util.SystemBarUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.listener.SaveListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdviceActivity extends AppCompatActivity {

    @ViewInject(R.id.toolbar)
    Toolbar mToolbar;

    @ViewInject(R.id.met_advice_content)
    MaterialEditText met_advice_content;
    @ViewInject(R.id.met_advice_contact)
    MaterialEditText met_advice_contact;

    @ViewInject(R.id.btn_advice_commit)
    Button btn_advice_commit;

    String advice_content, advice_contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ViewUtils.inject(this);

        SystemBarUtil.setSystemBarColor(this);
        initToolBar();

        initView();
    }

    private void initToolBar() {
        mToolbar.setTitle("意见反馈");
        setSupportActionBar(mToolbar);
        int appColor = ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
        mToolbar.setBackgroundColor(appColor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {

        met_advice_content.setPrimaryColor(getAppThemeColor());
        met_advice_content.setTextColor(getAppThemeColor());
        met_advice_contact.setPrimaryColor(getAppThemeColor());
        met_advice_contact.setTextColor(getAppThemeColor());

        btn_advice_commit.setBackgroundColor(getAppThemeColor());

        btn_advice_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advice_content = met_advice_content.getText().toString();
                advice_contact = met_advice_contact.getText().toString();
                if (advice_content.equals("")) {
                    if (advice_contact.equals("")) {
                        //内容为空，联系方式为空
                        new SweetAlertDialog(AdviceActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("说点什么吧")
                                .setContentText("不是我不懂你，是你不让我去懂你！")
                                .setCancelText("我点错了")
                                .setConfirmText("我知道了")
                                .show();
                    } else {
                        //内容为空，联系方式不为空
                        new SweetAlertDialog(AdviceActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("您什么也没说")
                                .setContentText("沉默不是代表你的错，那是什么？")
                                .setCancelText("沉默是金")
                                .setConfirmText("说来听听")
                                .show();
                    }
                } else {

                    if (advice_contact.equals("")) {
                        //内容不为空，联系方式为空
                        new SweetAlertDialog(AdviceActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("我该怎么联系你")
                                .setContentText("请输入您的联系方式，哪怕是假的也好！")
                                .setCancelText("这样也行").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                new SweetAlertDialog(AdviceActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("还是不要了吧")
                                        .setContentText("正确的联系方式以便可以联系到您哦！")
                                        .setConfirmText("我知道了")
                                        .show();
                            }
                        })
                                .setConfirmText("我知道了")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();
                                        new SweetAlertDialog(AdviceActivity.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("请输入正确的联系方式哦")
                                                .setContentText("亲，正确的联系方式以便可以联系到您哦！")
                                                .setConfirmText("我知道了")
                                                .show();
                                    }
                                })
                                .show();
                    } else {
                        //内容不为空，联系方式不为空
                        //提交数据到服务器
                        sendFeedbackMsg(advice_content,advice_contact);
                    }

                }

            }
        });

    }

    private void sendFeedbackMsg(String content,String contact) {
        Feedback feedback = new Feedback();
        feedback.setContent(content);
        feedback.setContact(contact);
        feedback.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                new SweetAlertDialog(AdviceActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("感谢您的反馈")
                                .setContentText("您反馈的内容已提交，感谢您的意见和建议！")
                                .setCancelText("不用客气")
                                .setConfirmText("我知道了")
                                .show();

                met_advice_content.setText("");
                met_advice_contact.setText("");
            }

            @Override
            public void onFailure(int i, String s) {
                new SweetAlertDialog(AdviceActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("真的非常抱歉呢")
                        .setContentText("反馈提交失败，请检查网络连接后重试！")
                        .setConfirmText("好吧好吧")
                        .setCustomImage(R.mipmap.icon_cry_2)
                        .show();
            }
        });
    }

    private int getAppThemeColor() {
        return ColorUtil.getAppThemeColor(this, Constant.SP_SETTING, Constant.APP_THEME_COLOR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_advice, menu);
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
}
