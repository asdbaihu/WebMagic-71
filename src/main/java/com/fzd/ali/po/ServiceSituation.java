package com.fzd.ali.po;

/**
 * Created by FZD on 2018/5/17.
 * Description:服务情况
 */
public class ServiceSituation {
    private Float deliverySpeedNum; //发货速度
    private Float deliverySpeedAverageNum; //行业平均发货速度
    private Float reFundSpeedNum; //退款速度
    private Float reFundSpeedAverageNum; // 行业平均退款速度
    private Float reFundRate; //近90天退款率
    private Float reFundAverageRate;
    private Float customServiceRate; //客服介入率
    private Float customServiceAverageRate;
    private Float disputeRate; //纠纷率
    private Float disputeAverageRage;
    private Integer refundTimes; //近90天退款次数

    public Float getDeliverySpeedNum() {
        return deliverySpeedNum;
    }

    public void setDeliverySpeedNum(Float deliverySpeedNum) {
        this.deliverySpeedNum = deliverySpeedNum;
    }

    public Float getDeliverySpeedAverageNum() {
        return deliverySpeedAverageNum;
    }

    public void setDeliverySpeedAverageNum(Float deliverySpeedAverageNum) {
        this.deliverySpeedAverageNum = deliverySpeedAverageNum;
    }

    public Float getReFundSpeedNum() {
        return reFundSpeedNum;
    }

    public void setReFundSpeedNum(Float reFundSpeedNum) {
        this.reFundSpeedNum = reFundSpeedNum;
    }

    public Float getReFundSpeedAverageNum() {
        return reFundSpeedAverageNum;
    }

    public void setReFundSpeedAverageNum(Float reFundSpeedAverageNum) {
        this.reFundSpeedAverageNum = reFundSpeedAverageNum;
    }

    public Float getReFundRate() {
        return reFundRate;
    }

    public void setReFundRate(Float reFundRate) {
        this.reFundRate = reFundRate;
    }

    public Float getReFundAverageRate() {
        return reFundAverageRate;
    }

    public void setReFundAverageRate(Float reFundAverageRate) {
        this.reFundAverageRate = reFundAverageRate;
    }

    public Float getCustomServiceRate() {
        return customServiceRate;
    }

    public void setCustomServiceRate(Float customServiceRate) {
        this.customServiceRate = customServiceRate;
    }

    public Float getCustomServiceAverageRate() {
        return customServiceAverageRate;
    }

    public void setCustomServiceAverageRate(Float customServiceAverageRate) {
        this.customServiceAverageRate = customServiceAverageRate;
    }

    public Float getDisputeRate() {
        return disputeRate;
    }

    public void setDisputeRate(Float disputeRate) {
        this.disputeRate = disputeRate;
    }

    public Float getDisputeAverageRage() {
        return disputeAverageRage;
    }

    public void setDisputeAverageRage(Float disputeAverageRage) {
        this.disputeAverageRage = disputeAverageRage;
    }

    public Integer getRefundTimes() {
        return refundTimes;
    }

    public void setRefundTimes(Integer refundTimes) {
        this.refundTimes = refundTimes;
    }
}
