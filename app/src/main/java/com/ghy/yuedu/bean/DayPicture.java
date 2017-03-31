package com.ghy.yuedu.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by GHY on 2015/6/3.
 */
public class DayPicture extends BmobObject{
    String date;
    String picture;
    String picdes;
    Integer periodical;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicdes() {
        return picdes;
    }

    public void setPicdes(String picdes) {
        this.picdes = picdes;
    }

    public Integer getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Integer periodical) {
        this.periodical = periodical;
    }
}
