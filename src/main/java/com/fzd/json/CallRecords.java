package com.fzd.json;

import java.util.Date;

/**
 * 
 * @ClassName:  CallRecords   
 * @Description: 通话记录   
 * @author: chenyang 
 * @date:   2017年7月19日 上午10:47:06   
 *
 */
public class CallRecords {
	
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
	private Date dateTime;
	
	/**
	 * 通话时间yyyy-MM-dd HH:mm:ss
	 */
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

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	public String getTalkTime() {
		return talkTime;
	}

	public void setTalkTime(String talkTime) {
		this.talkTime = talkTime;
	}

	public String getCallArea() {
		return callArea;
	}

	public void setCallArea(String callArea) {
		this.callArea = callArea;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getTalkTel() {
		return talkTel;
	}

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
