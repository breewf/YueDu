package com.ghy.yuedu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ghy.yuedu.MainActivity;
import com.ghy.yuedu.R;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.util.AnimUtil;

/*
* 引导页
* */
public class LeadActivity extends Activity {

    Button btn_lead_startApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        btn_lead_startApp = (Button) findViewById(R.id.btn_lead_startApp);

        int whereStartFlag = getIntent().getIntExtra("comeFrom", 0);
        if (whereStartFlag == Constant.START_FROM_SETTING) {
            //从设置项启动
            btn_lead_startApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(LeadActivity.this, "您已开启，感谢有您", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            btn_lead_startApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(LeadActivity.this, MainActivity.class));
                    finish();
                }
            });
        }
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
