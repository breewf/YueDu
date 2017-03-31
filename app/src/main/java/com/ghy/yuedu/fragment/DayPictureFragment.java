package com.ghy.yuedu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ghy.yuedu.MainActivity;
import com.ghy.yuedu.R;
import com.ghy.yuedu.activity.PictureShowActivity;
import com.ghy.yuedu.bean.DataDate;
import com.ghy.yuedu.bean.DayPicture;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.network.ImageLoader;
import com.ghy.yuedu.util.ColorUtil;
import com.ghy.yuedu.util.SPUtil;
import com.ghy.yuedu.util.SnackUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rey.material.widget.SnackBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by GHY on 2015-05-11.
 */
public class DayPictureFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.layout_day_pic)
    RelativeLayout layout_day_pic;

    @ViewInject(R.id.pic_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.snack_bar)
    SnackBar snack_bar;

    @ViewInject(R.id.tv_date_number)
    TextView tv_date_number;//日期
    @ViewInject(R.id.layout_date_line)
    RelativeLayout layout_date_line;//横线
    @ViewInject(R.id.iv_day_pic)
    ImageView iv_day_pic;//图片
    @ViewInject(R.id.tv_day_pic_des)
    TextView tv_day_pic_des;//图片描述

    @ViewInject(R.id.view_cut_line)
    View view_cut_line;//分割线

    String getDate;//获取日期
    Boolean isToday = false;//当日是否有数据
    Integer periodicalNumber;//获取期刊数
    Boolean startNoNet = false;//启动时无网络，用于标记下拉刷新时刷新今日内容
    Integer datePeriodicalNumber;//通过日期获得的期刊数

    String getPicUrl;//图片的网址

    public static DayPictureFragment DPFInstance;

    public DayPictureFragment newInstance() {
        DayPictureFragment dayPictureFragment = new DayPictureFragment();
        return dayPictureFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_picture, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DPFInstance = this;
        initSwipeRefreshLayout();

        //先加载本地的期刊数，无网络时，使用本地的期刊数
        periodicalNumber = SPUtil.getIntSP(getActivity(), Constant.SP_SETTING, Constant.PERIODICAL_NUMBER);
        datePeriodicalNumber = periodicalNumber;

        judgeNetAndInitDataFirst();

    }

    private void judgeNetAndInitDataFirst() {
        //判断网络环境
        //无网络，加载缓存期刊数据，有网络，加载网络今日数据
        if (MainActivity.netAvailable) {
            if (MainActivity.onlyWifi) {
                startNoNet = true;
                initPeriodicalDataCache(datePeriodicalNumber);
            } else {
                QueryDate();
                startNoNet = false;
            }
        } else {
            startNoNet = true;
            initPeriodicalDataCache(datePeriodicalNumber);
        }
    }

    private void QueryDate() {
        getDate = getDate();
        //查询当天日期有无数据
        BmobQuery<DataDate> dataDateQuery = new BmobQuery<>();
        dataDateQuery.addWhereEqualTo("datekey", "date");
        dataDateQuery.findObjects(getActivity(), new FindListener<DataDate>() {
            @Override
            public void onSuccess(List<DataDate> list) {
                for (DataDate dataDate : list) {
                    if (dataDate.getDate().equals(getDate)) {
                        isToday = true;
                        //得到当日数据期刊数
                        periodicalNumber = dataDate.getPeriodical();
                        datePeriodicalNumber = periodicalNumber;
                        break;
                    }
                }
                if (isToday) {
                    //加载数据
                    initData();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    MainActivity.mainInstance.setRemindTodayData(false);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    private void initData() {
        final BmobQuery<DayPicture> dayPictureQuery = new BmobQuery<>();
        dayPictureQuery.addWhereEqualTo("date", getDate);
        dayPictureQuery.findObjects(getActivity(), new FindListener<DayPicture>() {
            @Override
            public void onSuccess(List<DayPicture> list) {
                parserData(list);
            }

            @Override
            public void onError(int i, String s) {
                SnackUtil.errorAndCheckNetOrTryLater(snack_bar, getAppThemeColor());
            }
        });
    }

    private void parserData(List<DayPicture> dayPictures) {
        //期刊数
        periodicalNumber = dayPictures.get(0).getPeriodical();

        tv_date_number.setText(dayPictures.get(0).getDate());
        //顶部横线可见
        layout_date_line.setVisibility(View.VISIBLE);
        //分割线可见
        view_cut_line.setVisibility(View.VISIBLE);

        getPicUrl = dayPictures.get(0).getPicture();
        //加载图片
        displayImageView(getPicUrl);
        //设置图片点击事件
        iv_day_pic.setOnClickListener(this);

        //遇到@换行，遇到#空一行
        String picDes = dayPictures.get(0).getPicdes().replace("@", "\n").replace("#", "\n\n");
        if (picDes == null || picDes.equals("")) {
            tv_day_pic_des.setText("暂无描述");
        } else {
            tv_day_pic_des.setText(picDes);
        }

        //加载数据成功提醒
        SnackUtil.initDataSuccess(snack_bar, getAppThemeColor());

    }

    /*
    * 加载图片
    * */
    private void displayImageView(String getPicUrl) {
        ImageLoader imageLoader = new ImageLoader(getActivity());
        imageLoader.display(iv_day_pic, getPicUrl);
        //设置图片宽高
        ViewGroup.LayoutParams params = iv_day_pic.getLayoutParams();
        params.width = getDisplayWidth();
        params.height = getDisplayWidth() * 5 / 6;
        iv_day_pic.setLayoutParams(params);
    }

    /*
  * 获取手机屏幕宽度
  * */
    private int getDisplayWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels; //当前分辨率宽度
    }


    private int getAppThemeColor() {
        return ColorUtil.getAppThemeColor(getActivity(), Constant.SP_SETTING, Constant.APP_THEME_COLOR);
    }

    /*
  * 获取当天日期
  * */
    private String getDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sDateFormat.format(new Date());
    }

    private void initSwipeRefreshLayout() {
        ColorUtil.setSwipeRefreshColor(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //隐藏今日无数据提醒
                MainActivity.mainInstance.setRemindTodayData(true);
                //下拉刷新不再判断网络环境
                refreshMethod();
            }

            private void refreshMethod() {
                if (periodicalNumber != -1) {
                    if (periodicalNumber != 1) {
                        periodicalNumber--;
                        if (startNoNet) {
                            //如果启动时无网络,则网络恢复后下拉刷新当日内容
                            startNoNet = false;
                            initPeriodicalData(periodicalNumber + 1);
                        } else {
                            //理论上服务器上期刊数是由小到大顺序排列，即期刊减1一定存在
                            initPeriodicalData(periodicalNumber);
                        }
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        if (snack_bar.getVisibility() != View.VISIBLE)
                            snack_bar.applyStyle(R.style.SnackBarSingleLine)
                                    .text("没有更多数据啦！")
                                    .duration(4000)
                                    .actionTextColor(getResources().getColor(R.color.white))
                                    .actionText("回到今日")
                                    .actionClickListener(new SnackBar.OnActionClickListener() {
                                        @Override
                                        public void onActionClick(SnackBar snackBar, int i) {
                                            initPeriodicalDataCache(datePeriodicalNumber);
                                        }
                                    })
                                    .backgroundColor(getAppThemeColor())
                                    .show();
                    }
                } else {
                    //有网络时刷新重新查询当天
                    judgeNetAndInitDataFirst();
                }
            }
        });
    }

    private void initPeriodicalData(final int periodicalNum) {
        BmobQuery<DayPicture> dayPictureQuery2 = new BmobQuery<>();
        dayPictureQuery2.addWhereEqualTo("periodical", periodicalNum);
        dayPictureQuery2.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        dayPictureQuery2.findObjects(getActivity(), new FindListener<DayPicture>() {
            @Override
            public void onSuccess(List<DayPicture> dayPictures) {
                swipeRefreshLayout.setRefreshing(false);
                parserData(dayPictures);
                //加载成功
                if (snack_bar.getVisibility() != View.VISIBLE)
                    snack_bar.applyStyle(R.style.SnackBarSingleLine)
                            .text("数据加载成功！")
                            .actionText("回到今日")
                            .actionTextColor(getResources().getColor(R.color.white))
                            .actionClickListener(new SnackBar.OnActionClickListener() {
                                @Override
                                public void onActionClick(SnackBar snackBar, int i) {
                                    initPeriodicalDataCache(datePeriodicalNumber);
                                }
                            })
                            .duration(2000)
                            .backgroundColor(getAppThemeColor())
                            .show();

            }

            @Override
            public void onError(int i, String s) {
                swipeRefreshLayout.setRefreshing(false);
                //刷新失败期刊数+1
                periodicalNumber = periodicalNum + 1;
                //无网络加载缓存时遇到无缓存时设置提醒无网络
                if (!MainActivity.netAvailable) {
                    //网络不可用
                    SnackUtil.showSnackBarNetError(getActivity(), snack_bar, getAppThemeColor());
                } else {
                    if (MainActivity.onlyWifi) {
                        //移动网络不可用
                        SnackUtil.onlyWifiConnect(getActivity(), snack_bar, getAppThemeColor());
                    }
                }
            }
        });
    }

    private void initPeriodicalDataCache(final int periodicalNum) {
        BmobQuery<DayPicture> dayPictureQuery2 = new BmobQuery<>();
        dayPictureQuery2.addWhereEqualTo("periodical", periodicalNum);
        dayPictureQuery2.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        dayPictureQuery2.findObjects(getActivity(), new FindListener<DayPicture>() {
            @Override
            public void onSuccess(List<DayPicture> dayPictures) {
                parserData(dayPictures);
                //启动加载缓存且页面不可见，不进行提示
                //如果加载了缓存数据成功,则认为是有网络，防止下拉刷新往日数据时又重新加载今日数据
                startNoNet = false;
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        int getImageStatus = ImageLoader.loadImageStatus;
        if (getImageStatus == 0) {
            showToast("正在加载请稍后");
        } else if (getImageStatus == -1) {
            //说明加载图片失败，点击重新加载
            displayImageView(getPicUrl);
        } else if (getImageStatus == 1) {
            //全屏查看并下载
            Intent intent = new Intent(getActivity(), PictureShowActivity.class);
            intent.putExtra("picUrl", getPicUrl);
            startActivity(intent);
        } else {
            showToast("出现未知错误");
        }
    }

    private void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    /*
    * 更换背景
    * */
    public void changBg(int bgId) {
        layout_day_pic.setBackgroundColor(bgId);
    }

}
