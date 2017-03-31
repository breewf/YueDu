package com.ghy.yuedu.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by GHY on 2015/6/3.
 */
public class DaySentence extends BmobObject{
    String date;
    String sentence;
    String sendes;
    Integer periodical;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSendes() {
        return sendes;
    }

    public void setSendes(String sendes) {
        this.sendes = sendes;
    }

    public Integer getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Integer periodical) {
        this.periodical = periodical;
    }
}
