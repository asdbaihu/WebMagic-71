package com.fzd.ali.po;

/**
 * Created by FZD on 2018/5/10.
 * Description:存放淘宝店
 */
public class ShopBase {
    private String name;
    private String url;
    private String spmId;//淘宝url上都带有的参数spm
    private String itemId;//淘宝li参数
    private String shopId;//li参数
    private String memberId; //li参数
    private String creditRemarkUrl; //信用评分url
    private String baseInfoUrl; //基本信息URL

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSpmId() {
        return spmId;
    }

    public void setSpmId(String spmId) {
        this.spmId = spmId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCreditRemarkUrl() {
        return creditRemarkUrl;
    }

    public void setCreditRemarkUrl(String creditRemarkUrl) {
        this.creditRemarkUrl = creditRemarkUrl;
    }

    public String getBaseInfoUrl() {
        return baseInfoUrl;
    }

    public void setBaseInfoUrl(String baseInfoUrl) {
        this.baseInfoUrl = baseInfoUrl;
    }
}
