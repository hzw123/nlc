package com.nlc.nraas.domain;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.tools.JsonDateSerializer;
import com.nlc.nraas.tools.JsonLongTimeSerializer;

/**
 * 访问记录列表
 * 
 * @author Dell
 *
 */
@Entity
public class AccessList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 访问时间 */
	private Date time;
	/** 时长 */
	private long duration;
	private String ip;
	/** 页数 */
	private long pageNumber;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@JsonSerialize(using=JsonLongTimeSerializer.class)
	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}

}
