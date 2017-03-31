package com.ghy.yuedu.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.ghy.yuedu.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

public class ImageLoader {

    private BitmapUtils bitmapUtils;
    public Context mContext;
    public static int loadImageStatus=0;

    public ImageLoader(Context mContext) {

        this.mContext = mContext;

        bitmapUtils = new BitmapUtils(mContext);
        bitmapUtils.configDefaultLoadingImage(R.mipmap.image_load_default);// 默认背景图片
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.image_load_error);// 加载失败图片
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);// 设置图片压缩类型

    }

    public class CustomBitmapLoadCallBack extends
            DefaultBitmapLoadCallBack<ImageView> {
        @Override
        public void onLoading(ImageView container, String uri,
                              BitmapDisplayConfig config, long total, long current) {
            loadImageStatus=0;//正在加载图片

        }

        @Override
        public void onLoadCompleted(ImageView container, String uri,
                                    Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            fadeInDisplay(container, bitmap);
            loadImageStatus=1;//加载图片成功
        }

        @Override
        public void onLoadFailed(ImageView container, String uri,
                                 Drawable drawable) {
            super.onLoadFailed(container, uri, drawable);
            loadImageStatus=-1;//加载图片失败
        }

        private final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(
                android.R.color.transparent);

        private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
            final TransitionDrawable transitionDrawable = new TransitionDrawable(
                    new Drawable[]{
                            TRANSPARENT_DRAWABLE,
                            new BitmapDrawable(imageView.getResources(), bitmap)});
            imageView.setImageDrawable(transitionDrawable);
            transitionDrawable.startTransition(500);
        }

    }

    public void display(ImageView imageView, String url) {// 外部接口函数
        bitmapUtils.display(imageView, url, new CustomBitmapLoadCallBack());
    }

}
