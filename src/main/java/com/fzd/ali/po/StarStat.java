package com.fzd.ali.po;

import java.util.List;

/**
 * Created by FZD on 2018/5/17.
 * Description:评分统计、总体满意度统计
 */
public class StarStat {
    private List<StarStatItem> starStatItemList;
    private List<Remark> remarkList;

    public List<StarStatItem> getStarStatItemList() {
        return starStatItemList;
    }

    public void setStarStatItemList(List<StarStatItem> starStatItemList) {
        this.starStatItemList = starStatItemList;
    }

    public List<Remark> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<Remark> remarkList) {
        this.remarkList = remarkList;
    }
}
