package com.ghy.yuedu.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by GHY on 2015/6/1.
 */
public class DataDate extends BmobObject {
    String date;//日期
    Integer periodical;//期刊数
    String datekey;//查询键

    public Integer getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Integer periodical) {
        this.periodical = periodical;
    }

    public String getDatekey() {
        return datekey;
    }

    public void setDatekey(String datekey) {
        this.datekey = datekey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
