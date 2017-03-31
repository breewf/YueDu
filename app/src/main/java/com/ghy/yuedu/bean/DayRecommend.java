package com.ghy.yuedu.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by GHY on 2015/6/1.
 */
public class DayRecommend extends BmobObject{
    String date;//日期
    String type;//推荐类型
    String sentence;//推荐句子
    String sendes;//推荐句子解读
    String article;//推荐文章
    String artdes;//推荐文章解读
    String picture;//推荐图片
    String picdes;//推荐图片描述
    Integer periodical;//期刊数

    public String getSendes() {
        return sendes;
    }

    public void setSendes(String sendes) {
        this.sendes = sendes;
    }

    public String getArtdes() {
        return artdes;
    }

    public void setArtdes(String artdes) {
        this.artdes = artdes;
    }

    public Integer getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Integer periodical) {
        this.periodical = periodical;
    }

    public String getPicdes() {
        return picdes;
    }

    public void setPicdes(String picdes) {
        this.picdes = picdes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
