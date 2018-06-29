package com.fzd.ali.po;

import java.util.List;

/**
 * Created by FZD on 2018/5/17.
 * Description:交易信用记录
 */
public class CreditDetail {
    private TradeSituation tradeSituation; //交易情况
    private ServiceSituation serviceSituation; //服务情况
    private StarStat starStat; //店铺总体满意度

    public TradeSituation getTradeSituation() {
        return tradeSituation;
    }

    public void setTradeSituation(TradeSituation tradeSituation) {
        this.tradeSituation = tradeSituation;
    }

    public ServiceSituation getServiceSituation() {
        return serviceSituation;
    }

    public void setServiceSituation(ServiceSituation serviceSituation) {
        this.serviceSituation = serviceSituation;
    }

    public StarStat getStarStat() {
        return starStat;
    }

    public void setStarStat(StarStat starStat) {
        this.starStat = starStat;
    }
}
