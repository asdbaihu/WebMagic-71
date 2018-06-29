package com.fzd.ali.po;

/**
 * Created by FZD on 2018/6/6.
 * Description:
 */
public class AreaTitle extends BasePo{
    private String province;
    private String city;
    private String keywords;
    private int bizType; //1.生产加工 //2.经销批发 //4.招商代理 //8.商业服务
    private boolean finished;
    private Integer order;

    public AreaTitle(String id, String province, String city, String keywords, int bizType, boolean finished, Integer order) {
        super.setId(id);
        this.province = province;
        this.city = city;
        this.keywords = keywords;
        this.bizType = bizType;
        this.finished = finished;
        this.order = order;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
