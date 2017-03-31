package com.ghy.yuedu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ghy.yuedu.R;
import com.ghy.yuedu.listener.PictureTouchListener;
import com.ghy.yuedu.network.ImageLoader;
import com.ghy.yuedu.util.FileUtil;
import com.ghy.yuedu.util.ViewUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureShowActivity extends AppCompatActivity {

    String getPicUrl;//得到传递过来的图片地址

    @ViewInject(R.id.image_full_screen)
    ImageView image_full_screen;

    @ViewInject(R.id.btn_download_pic)
    Button btn_download_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_show);
        ViewUtils.inject(this);

        getPicUrl=getIntent().getStringExtra("picUrl");

        displayImageView(getPicUrl,image_full_screen);

        image_full_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_full_screen.setScaleType(ImageView.ScaleType.MATRIX);
                image_full_screen.setOnTouchListener(new PictureTouchListener());
            }
        });


        btn_download_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadPic(getPicUrl);
            }
        });

    }

    /*
    * 下载图片的方法
    * 使用xUtils 的 httpUtils
    * */
    HttpHandler httpHandler;
    private void downloadPic(String getPicUrl) {

        //保存位置
        String savePath= FileUtil.createFilePath("downloadPic");
        //根据日期生成一个随机数做为文件名
        String timeTmp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = timeTmp + ".jpg";

        String absolutelyPath=savePath+imageName;

        HttpUtils httpUtils=new HttpUtils();
        httpHandler=httpUtils.download(
                getPicUrl,
                absolutelyPath,
                true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将重新下载。
                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new RequestCallBack<File>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showToast("开始下载，请稍后");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        showToast("下载成功");
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        showToast("下载出错，请重试");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (httpHandler!=null){
            httpHandler.cancel();
        }
    }

    private void showToast(String s) {
        ViewUtil.showToastLengthShort(this,s);
    }

    /*
    * 获取手机屏幕宽度
    * */
    private int getDisplayWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels; //当前分辨率宽度
    }

    private void displayImageView(String getPicUrl,ImageView iv_pic) {
        ImageLoader imageLoader = new ImageLoader(this);
        imageLoader.display(iv_pic, getPicUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_picture_show, menu);
        return true;
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
