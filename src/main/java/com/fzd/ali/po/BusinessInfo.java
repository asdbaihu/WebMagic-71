package com.fzd.ali.po;

import java.util.Date;

/**
 * Created by FZD on 2018/5/17.
 * Description:
 */
public class BusinessInfo {
    /**
     * 编号
     */
    private String id;

    /**
     * 注册时间
     */
    private Date regDate;

    /**
     * 企业类型
     */
    private String type;

    /**
     * 法人
     */
    private String corporate;

    /**
     * 注册资本金额
     */
    private Float regCapital;

    /**
     * 注册资本单位
     */
    private String regCur;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 统一社会信用代码
     */
    private String identifyNo;

    /**
     * 注册地址
     */
    private String regAdr;

    /**
     * 经营期限起
     */
    private Date optPeriodStart;

    /**
     * 经营期限至
     */
    private Date optPeriodEnd;

    /**
     * 经营范围
     */
    private String optScope;

    /**
     * 登记机关
     */
    private String regAuthority;

    private String annuals; //年报时间

    private String authentication;//认证信息

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    public Float getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(Float regCapital) {
        this.regCapital = regCapital;
    }

    public String getRegCur() {
        return regCur;
    }

    public void setRegCur(String regCur) {
        this.regCur = regCur;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifyNo() {
        return identifyNo;
    }

    public void setIdentifyNo(String identifyNo) {
        this.identifyNo = identifyNo;
    }

    public String getRegAdr() {
        return regAdr;
    }

    public void setRegAdr(String regAdr) {
        this.regAdr = regAdr;
    }

    public Date getOptPeriodStart() {
        return optPeriodStart;
    }

    public void setOptPeriodStart(Date optPeriodStart) {
        this.optPeriodStart = optPeriodStart;
    }

    public Date getOptPeriodEnd() {
        return optPeriodEnd;
    }

    public void setOptPeriodEnd(Date optPeriodEnd) {
        this.optPeriodEnd = optPeriodEnd;
    }

    public String getOptScope() {
        return optScope;
    }

    public void setOptScope(String optScope) {
        this.optScope = optScope;
    }

    public String getRegAuthority() {
        return regAuthority;
    }

    public void setRegAuthority(String regAuthority) {
        this.regAuthority = regAuthority;
    }


    public String getAnnuals() {
        return annuals;
    }

    public void setAnnuals(String annuals) {
        this.annuals = annuals;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}
