package com.fzd.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

/**
 * @ClassName: CallRecords
 * @Description: 通话记录
 * @author: chenyang
 * @date: 2017年7月19日 上午10:47:06
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SCMobileVoice {
    /**
     * callClass : 本地
     * fee1 : 0.00
     * startDate : 2017/07/01 01:47:04
     * duration : 12秒
     * newCallFlag : 1
     * trafficWay : 主叫
     * prodprcName : 标准资费
     * callPlace : 成都市
     * otherParty : 1008611
     */
    /**
     * 唯一标识符
     */
    private String id;

    /**
     * 手机号码，关联信息
     */
    private String telString;

    /**
     * 拨打时间 yyyy-mm
     */
//	@JsonProperty("startDate")

    @JsonDeserialize(using = DateDeserializer.MyDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dateTime;

    /**
     * 通话时间yyyy-MM-dd HH:mm:ss
     */
//	@JsonProperty("startDate")
    private Date callTime;

    /**
     * 通话时长
     */
    private String talkTime;

    /**
     * 通话地点
     */
    private String callArea;

    /**
     * 通话类型：主叫/接听
     */
    private String callType;

    /**
     * 与本机通话号码
     */
    private String talkTel;

    /**
     * 与本机通话号码归属地
     */
    private String talkTelArea;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    @JsonProperty("callTime")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCallTime() {
        return callTime;
    }
    @JsonProperty(value = "startDate")
    @JsonDeserialize(using = DateDeserializer.MyDateDeserializer.class)
    public void setStartDate(Date startDate) {
        this.callTime = startDate;
        this.dateTime = startDate;
    }
    @JsonProperty("talkTime")
    public String getTalkTime() {
        return talkTime;
    }

    @JsonProperty("duration")
    public void setTalkTime(String talkTime) {
        this.talkTime = talkTime;
    }
    @JsonProperty("callArea")
    public String getCallArea() {
        return callArea;
    }

    @JsonProperty("callPlace")
    public void setCallArea(String callArea) {
        this.callArea = callArea;
    }
    @JsonProperty("callType")
    public String getCallType() {
        return callType;
    }

    @JsonProperty("trafficWay")
    public void setCallType(String callType) {
        this.callType = callType;
    }
    @JsonProperty("talkTel")
    public String getTalkTel() {
        return talkTel;
    }

    @JsonProperty("otherParty")
    public void setTalkTel(String talkTel) {
        this.talkTel = talkTel;
    }

    public String getTelString() {
        return telString;
    }

    public void setTelString(String telString) {
        this.telString = telString;
    }

    public String getTalkTelArea() {
        return talkTelArea;
    }

    public void setTalkTelArea(String talkTelArea) {
        this.talkTelArea = talkTelArea;
    }

    @Override
    public String toString() {
        return "CallRecords [id=" + id + ", telString=" + telString + ", dateTime=" + dateTime + ", callTime="
                + callTime + ", talkTime=" + talkTime + ", callArea=" + callArea + ", callType=" + callType
                + ", talkTel=" + talkTel + ", talkTelArea=" + talkTelArea + "]";
    }
}

