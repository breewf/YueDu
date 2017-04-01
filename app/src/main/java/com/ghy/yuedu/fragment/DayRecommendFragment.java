package com.ghy.yuedu.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.ghy.yuedu.bean.DayRecommend;
import com.ghy.yuedu.global.Constant;
import com.ghy.yuedu.network.ImageLoader;
import com.ghy.yuedu.util.ColorUtil;
import com.ghy.yuedu.util.SPUtil;
import com.ghy.yuedu.util.SnackUtil;
import com.ghy.yuedu.view.MyLoadingDialog;
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
public class DayRecommendFragment extends Fragment implements View.OnClickListener {

    public static DayRecommendFragment DRFInstance;

    public DayRecommendFragment newInstance() {
        DayRecommendFragment dayRecommendFragment = new DayRecommendFragment();
        return dayRecommendFragment;
    }

    @ViewInject(R.id.layout_day_rec)
    RelativeLayout layout_day_rec;

    @ViewInject(R.id.rec_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.snack_bar)
    SnackBar snack_bar;//底部提示栏

    @ViewInject(R.id.layout_day_recommend_sentence)
    RelativeLayout layout_day_recommend_sentence;
    @ViewInject(R.id.layout_day_recommend_article)
    RelativeLayout layout_day_recommend_article;
    @ViewInject(R.id.layout_day_recommend_picture)
    RelativeLayout layout_day_recommend_picture;

    @ViewInject(R.id.tv_periodical_number)
    TextView tv_periodical_number;//推荐页面期刊数
    @ViewInject(R.id.layout_top_line)
    RelativeLayout layout_top_line;//顶部横线
    @ViewInject(R.id.tv_day_recommend_date)
    TextView tv_day_recommend_date;//推荐页面日期
    @ViewInject(R.id.view_date_cut_line)
    View view_date_cut_line;//底部日期上方分割线

    @ViewInject(R.id.tv_day_recommend_sentence)
    TextView tv_day_recommend_sentence;//推荐句子
    @ViewInject(R.id.tv_day_recommend_sentence_des)
    TextView tv_day_recommend_sentence_des;//推荐句子解读
    @ViewInject(R.id.tv_day_recommend_article)
    TextView tv_day_recommend_article;//推荐文章
    @ViewInject(R.id.tv_day_recommend_article_des)
    TextView tv_day_recommend_article_des;//推荐文章解读
    @ViewInject(R.id.iv_day_recommend_pic)
    ImageView iv_day_recommend_pic;//推荐图片
    @ViewInject(R.id.tv_day_recommend_pic_des)
    TextView tv_day_recommend_pic_des;//推荐图片描述

    String getDate;//获取日期
    Boolean isToday = false;//当日是否有数据
    Integer periodicalNumber;//获取期刊数
    Boolean startNoNet = false;//启动时无网络，用于标记下拉刷新时刷新今日内容
    Integer datePeriodicalNumber;//通过日期获得的期刊数，回到今日使用

    String getPicUrl;//图片的网址

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_recommend, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DRFInstance = this;
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

        //加载中动画
        MyLoadingDialog.createLoadingDialog(getActivity(), "加载中，请稍后").show();

        //获取当天日期
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
                        //得到期刊数并保存到本地
                        periodicalNumber = dataDate.getPeriodical();
                        SPUtil.saveSP(getActivity(), Constant.SP_SETTING, Constant.PERIODICAL_NUMBER, periodicalNumber);
                        datePeriodicalNumber = periodicalNumber;
                        break;
                    }
                }
                if (isToday) {
                    //加载数据
                    initData();
                } else {
                    //服务器无数据提示
                    swipeRefreshLayout.setRefreshing(false);
                    MainActivity.mainInstance.setRemindTodayData(false);
                    MyLoadingDialog.dismissLoadingDialog();
                }
            }

            @Override
            public void onError(int i, String s) {
            }
        });

    }

    /*
    * 加载数据
    * */
    private void initData() {
        BmobQuery<DayRecommend> dayRecommendQuery = new BmobQuery<>();
        dayRecommendQuery.addWhereEqualTo("date", getDate);
        dayRecommendQuery.findObjects(getActivity(), new FindListener<DayRecommend>() {
            @Override
            public void onSuccess(List<DayRecommend> dayRecommends) {
                MyLoadingDialog.dismissLoadingDialog();
                swipeRefreshLayout.setRefreshing(false);
                parserData(dayRecommends);
            }

            @Override
            public void onError(int i, String s) {
                MyLoadingDialog.dismissLoadingDialog();
                SnackUtil.errorAndCheckNetOrTryLater(snack_bar, getAppThemeColor());
            }
        });

    }

    /*
    * 解析数据
    * */
    private void parserData(List<DayRecommend> dayRecommends) {
        //推荐期刊数
        periodicalNumber = dayRecommends.get(0).getPeriodical();
        tv_periodical_number.setText("第" + periodicalNumber + "期");
        //顶部横线可见
        layout_top_line.setVisibility(View.VISIBLE);
        //底部分割线可见
        view_date_cut_line.setVisibility(View.VISIBLE);

        String getType = dayRecommends.get(0).getType();
        if (getType.equals("sentence")) {
            layout_day_recommend_article.setVisibility(View.GONE);
            layout_day_recommend_picture.setVisibility(View.GONE);
            layout_day_recommend_sentence.setVisibility(View.VISIBLE);
            //遇到@换行，遇到#空一行
            String s = dayRecommends.get(0).getSentence().replace("@", "\n").replace("#", "\n\n");
            tv_day_recommend_sentence.setText(s);
            //设置行间距
            tv_day_recommend_sentence.setLineSpacing(20, 1.2f);
            String getDes = dayRecommends.get(0).getSendes();
            if (getDes != null && !getDes.equals("")) {
                tv_day_recommend_sentence_des.setText("心有灵犀：" + getDes);
            } else {
                tv_day_recommend_sentence_des.setText("心有灵犀：暂无内容");
            }
        } else if (getType.equals("picture")) {
            layout_day_recommend_article.setVisibility(View.GONE);
            layout_day_recommend_sentence.setVisibility(View.GONE);
            layout_day_recommend_picture.setVisibility(View.VISIBLE);
            getPicUrl = dayRecommends.get(0).getPicture();
            //加载图片
            displayImageView(getPicUrl);
            //设置图片点击事件
            iv_day_recommend_pic.setOnClickListener(this);

            //遇到@换行，遇到#空一行
            String picDes = dayRecommends.get(0).getPicdes().replace("@", "\n").replace("#", "\n\n");
            if (picDes != null && !picDes.equals("")) {
                tv_day_recommend_pic_des.setText(picDes);
            } else {
                tv_day_recommend_pic_des.setText("暂无描述");
            }

        } else if (getType.equals("article")) {
            layout_day_recommend_sentence.setVisibility(View.GONE);
            layout_day_recommend_picture.setVisibility(View.GONE);
            layout_day_recommend_article.setVisibility(View.VISIBLE);
            //遇到@换行，遇到#空一行
            String s = dayRecommends.get(0).getArticle().replace("@", "\n").replace("#", "\n\n");
            tv_day_recommend_article.setText(s);
            //设置行间距
            tv_day_recommend_article.setLineSpacing(20, 1.2f);
            String getDes = dayRecommends.get(0).getArtdes();
            if (getDes != null && !getDes.equals("")) {
                tv_day_recommend_article_des.setText("心灵碰撞：" + getDes);
            } else {
                tv_day_recommend_article_des.setText("心灵碰撞：暂无内容");
            }
        }
        //推荐页面日期
        tv_day_recommend_date.setText(dayRecommends.get(0).getDate());

        //加载数据成功提醒
        SnackUtil.initDataSuccess(snack_bar, getAppThemeColor());
    }

    /*
    * 加载图片
    * */
    private void displayImageView(String getPicUrl) {
        ImageLoader imageLoader = new ImageLoader(getActivity());
        imageLoader.display(iv_day_recommend_pic, getPicUrl);
        //设置图片宽高
        ViewGroup.LayoutParams params = iv_day_recommend_pic.getLayoutParams();
        params.width = getDisplayWidth();
        params.height = getDisplayWidth() * 5 / 6;
        iv_day_recommend_pic.setLayoutParams(params);
    }

    /*
    * 获取手机屏幕宽度
    * */
    private int getDisplayWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels; //当前分辨率宽度
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

            /*
            * 下拉刷新方法
            * */
            private void refreshMethod() {
                //APP首次使用加载的本地期刊数值为-1
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
        BmobQuery<DayRecommend> dayRecommendQuery2 = new BmobQuery<>();
        dayRecommendQuery2.addWhereEqualTo("periodical", periodicalNum);
        dayRecommendQuery2.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        dayRecommendQuery2.findObjects(getActivity(), new FindListener<DayRecommend>() {
            @Override
            public void onSuccess(List<DayRecommend> dayRecommends) {
                parserData(dayRecommends);
                //加载成功
                swipeRefreshLayout.setRefreshing(false);
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
        BmobQuery<DayRecommend> dayRecommendQuery2 = new BmobQuery<>();
        dayRecommendQuery2.addWhereEqualTo("periodical", periodicalNum);
        dayRecommendQuery2.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        dayRecommendQuery2.findObjects(getActivity(), new FindListener<DayRecommend>() {
            @Override
            public void onSuccess(List<DayRecommend> dayRecommends) {
                parserData(dayRecommends);
                //如果加载了缓存数据成功,则认为是有网络，防止下拉刷新往日数据时又重新加载今日数据
                startNoNet = false;
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

    private int getAppThemeColor() {
        return ColorUtil.getAppThemeColor(getActivity(), Constant.SP_SETTING, Constant.APP_THEME_COLOR);
    }

    private void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.mipmap.image_load_error) {
            //说明加载图片失败，点击重新加载
            displayImageView(getPicUrl);
        } else {
            //全屏查看并下载
            Intent intent = new Intent(getActivity(), PictureShowActivity.class);
            intent.putExtra("picUrl", getPicUrl);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                iv_day_recommend_pic, getString(R.string.transition_scene_img));
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }

    }

    /*
    * 更换背景
    * */
    public void changBg(int bgId) {
        layout_day_rec.setBackgroundColor(bgId);
    }
}
