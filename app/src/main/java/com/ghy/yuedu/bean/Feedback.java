package com.ghy.yuedu.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by GHY on 2015/7/27.
 * 用户反馈
 */
public class Feedback extends BmobObject {
    //反馈内容
    private String content;
    //联系方式
    private String contact;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
