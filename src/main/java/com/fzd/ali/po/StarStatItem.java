package com.fzd.ali.po;

/**
 * Created by FZD on 2018/5/17.
 * Description:星级评分类型，按一周、一月、六月、半年进行统计
 */
public class StarStatItem {
    private String starLevel;
    private Integer weekly; //最近一周
    private Integer monthly; //最近一月
    private Integer halfYearly; //最近六月
    private Integer totals;
    private Integer percent;
    private Integer halfYearBefore;//半年以前

    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    public Integer getWeekly() {
        return weekly;
    }

    public void setWeekly(Integer weekly) {
        this.weekly = weekly;
    }

    public Integer getMonthly() {
        return monthly;
    }

    public void setMonthly(Integer monthly) {
        this.monthly = monthly;
    }

    public Integer getHalfYearly() {
        return halfYearly;
    }

    public void setHalfYearly(Integer halfYearly) {
        this.halfYearly = halfYearly;
    }

    public Integer getTotals() {
        return totals;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
    }

    public Integer getHalfYearBefore() {
        return halfYearBefore;
    }

    public void setHalfYearBefore(Integer halfYearBefore) {
        this.halfYearBefore = halfYearBefore;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }
}
