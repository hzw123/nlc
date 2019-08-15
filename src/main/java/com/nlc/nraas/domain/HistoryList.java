package com.nlc.nraas.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.HistoryListStatus;
import com.nlc.nraas.tools.JsonDateSerializer;
import com.nlc.nraas.tools.JsonEnumSerializer;

/***
 * 发布历史列表
 * 
 * @author Dell
 *
 */
@Entity
public class HistoryList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String publishName;
	private Date publishAt;
	private String publishAddr;
	private HistoryListStatus publishStatus = HistoryListStatus.SUCCESS;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Programa programa;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getPublishAt() {
		return publishAt;
	}

	public void setPublishAt(Date publishAt) {
		this.publishAt = publishAt;
	}

	public String getPublishAddr() {
		return publishAddr;
	}

	public void setPublishAddr(String publishAddr) {
		this.publishAddr = publishAddr;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public HistoryListStatus getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(HistoryListStatus publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

}
