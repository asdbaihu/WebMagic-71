package com.fzd.ali.po;

import java.util.List;

/**
 * Created by FZD on 2018/5/17.
 * Description:
 */
public class CompanyInfo {

    private Integer cxtYear; //诚信通年数
    private String zmxyLevel; //芝麻信用等级
    private Integer zmyxScore; //芝麻信用分
    private String contacts; //联系人
    private String telString; //联系电话
    private String introduce; //介绍
    private String huangyeUrl; //黄页链接

    private BusinessInfo businessInfo;

    public Integer getCxtYear() {
        return cxtYear;
    }

    public void setCxtYear(Integer cxtYear) {
        this.cxtYear = cxtYear;
    }

    public String getZmxyLevel() {
        return zmxyLevel;
    }

    public void setZmxyLevel(String zmxyLevel) {
        this.zmxyLevel = zmxyLevel;
    }

    public Integer getZmyxScore() {
        return zmyxScore;
    }

    public void setZmyxScore(Integer zmyxScore) {
        this.zmyxScore = zmyxScore;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTelString() {
        return telString;
    }

    public void setTelString(String telString) {
        this.telString = telString;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getHuangyeUrl() {
        return huangyeUrl;
    }

    public void setHuangyeUrl(String huangyeUrl) {
        this.huangyeUrl = huangyeUrl;
    }

    public BusinessInfo getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
    }
}
