package com.ghy.yuedu.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by GHY on 2015/6/2.
 */
public class DayArticle extends BmobObject {
    String article;
    String artdes;
    String date;
    Integer periodical;

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getArtdes() {
        return artdes;
    }

    public void setArtdes(String artdes) {
        this.artdes = artdes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Integer periodical) {
        this.periodical = periodical;
    }
}
