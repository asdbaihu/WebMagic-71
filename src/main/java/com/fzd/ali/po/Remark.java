package com.fzd.ali.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by FZD on 2018/5/17.
 * Description: 评论
 */
public class Remark {


    /**
     * tpLogoURL :
     * offerName : 2018夏季新款纯亚麻抽绳松紧腰阔腿裤 大码女装文艺茶服 S515
     * offerURL : https://rate.1688.com/remark/offerSnapshot/view.htm?trade_id=MTQwOTkyODgyOTcwMTk5NTQ1&offer_id=567461255088&is_encrypt=true
     * postRole :
     * remarkTime : 2018-05-17 00:22:39
     * offer : true
     * explainTime :
     * remarkContent : 满意!
     * explainContent :
     * starLevel : FIVE
     * tradeId : 14099288297019****
     * tradeType : ALIPAY_TRADE
     * memberId : 邯**人
     */

    private String tpLogoURL;//商品URL
    private String offerName;//商品名称
    private String offerURL;
    private String postRole;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date remarkTime;//评论时间
    private boolean offer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date explainTime;
    private String remarkContent; //评论内容
    private String explainContent;
    private String starLevel;//星级
    private String tradeId; //订单号
    private String tradeType; //订单类型
    private String memberId;

    public String getTpLogoURL() {
        return tpLogoURL;
    }

    public void setTpLogoURL(String tpLogoURL) {
        this.tpLogoURL = tpLogoURL;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferURL() {
        return offerURL;
    }

    public void setOfferURL(String offerURL) {
        this.offerURL = offerURL;
    }

    public String getPostRole() {
        return postRole;
    }

    public void setPostRole(String postRole) {
        this.postRole = postRole;
    }

    public Date getRemarkTime() {
        return remarkTime;
    }

    public void setRemarkTime(Date remarkTime) {
        this.remarkTime = remarkTime;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public Date getExplainTime() {
        return explainTime;
    }

    public void setExplainTime(Date explainTime) {
        this.explainTime = explainTime;
    }

    public String getRemarkContent() {
        return remarkContent;
    }

    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent;
    }

    public String getExplainContent() {
        return explainContent;
    }

    public void setExplainContent(String explainContent) {
        this.explainContent = explainContent;
    }

    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
