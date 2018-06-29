package com.fzd.ali.po;

/**
 * Created by FZD on 2018/5/17.
 * Description:交易情况
 */
public class TradeSituation {
    private Integer tradeDealSumNum;//累计成交数trade-average-num
    private Integer tradeDealAverageNum; //行业均值trade-average-num
    private Integer tradeBuyerSumNum; // 累计买家数
    private Integer tradeBuyerAverageNum; //行业均值
    private Float repurchaseRate; //重复采购率
    private Float repurchaseRateAverage; //行业均值
    private Float VerifiedMemberRate; //认证会员占比
    private Integer VerifiedMemberNum; //认证会员人数
    private Float tbTmMemberRate; //淘宝天猫占比
    private Integer tbTmMemberNum; //淘宝天猫人数

    public Integer getTradeDealSumNum() {
        return tradeDealSumNum;
    }

    public void setTradeDealSumNum(Integer tradeDealSumNum) {
        this.tradeDealSumNum = tradeDealSumNum;
    }

    public Integer getTradeDealAverageNum() {
        return tradeDealAverageNum;
    }

    public void setTradeDealAverageNum(Integer tradeDealAverageNum) {
        this.tradeDealAverageNum = tradeDealAverageNum;
    }

    public Integer getTradeBuyerSumNum() {
        return tradeBuyerSumNum;
    }

    public void setTradeBuyerSumNum(Integer tradeBuyerSumNum) {
        this.tradeBuyerSumNum = tradeBuyerSumNum;
    }

    public Integer getTradeBuyerAverageNum() {
        return tradeBuyerAverageNum;
    }

    public void setTradeBuyerAverageNum(Integer tradeBuyerAverageNum) {
        this.tradeBuyerAverageNum = tradeBuyerAverageNum;
    }

    public Float getRepurchaseRate() {
        return repurchaseRate;
    }

    public void setRepurchaseRate(Float repurchaseRate) {
        this.repurchaseRate = repurchaseRate;
    }

    public Float getRepurchaseRateAverage() {
        return repurchaseRateAverage;
    }

    public void setRepurchaseRateAverage(Float repurchaseRateAverage) {
        this.repurchaseRateAverage = repurchaseRateAverage;
    }

    public Float getVerifiedMemberRate() {
        return VerifiedMemberRate;
    }

    public void setVerifiedMemberRate(Float verifiedMemberRate) {
        VerifiedMemberRate = verifiedMemberRate;
    }

    public Integer getVerifiedMemberNum() {
        return VerifiedMemberNum;
    }

    public void setVerifiedMemberNum(Integer verifiedMemberNum) {
        VerifiedMemberNum = verifiedMemberNum;
    }

    public Float getTbTmMemberRate() {
        return tbTmMemberRate;
    }

    public void setTbTmMemberRate(Float tbTmMemberRate) {
        this.tbTmMemberRate = tbTmMemberRate;
    }

    public Integer getTbTmMemberNum() {
        return tbTmMemberNum;
    }

    public void setTbTmMemberNum(Integer tbTmMemberNum) {
        this.tbTmMemberNum = tbTmMemberNum;
    }
}
