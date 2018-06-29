package com.fzd.ali.po;

import java.util.List;

/**
 * Created by FZD on 2018/5/10.
 * Description:
 */
public class AreaVo {
    private List<Area> areas;
    private List<Prov> provs;

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public List<Prov> getProvs() {
        return provs;
    }

    public void setProvs(List<Prov> provs) {
        this.provs = provs;
    }
}
