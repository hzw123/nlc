package com.nlc.nraas.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.IndexStatus;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * 索引任务
 * 
 * @author Dell
 */
@Entity
public class ArchivedTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** URL索引 */
	private String urlIndex;
	/** URL索引状态 */
	private IndexStatus urlStatus = IndexStatus.NOT_BUILD;
	/** 全文索引 */
	private String fullIndex;
	/** 全文索引状态 */
	private IndexStatus fullStatus = IndexStatus.NOT_BUILD;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private UrlList urlList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrlIndex() {
		return urlIndex;
	}

	public void setUrlIndex(String urlIndex) {
		this.urlIndex = urlIndex;
	}

	@JsonSerialize(using=JsonEnumSerializer.class)
	public IndexStatus getUrlStatus() {
		return urlStatus;
	}

	public void setUrlStatus(IndexStatus urlStatus) {
		this.urlStatus = urlStatus;
	}

	public String getFullIndex() {
		return fullIndex;
	}

	public void setFullIndex(String fullIndex) {
		this.fullIndex = fullIndex;
	}

	@JsonSerialize(using=JsonEnumSerializer.class)
	public IndexStatus getFullStatus() {
		return fullStatus;
	}

	public void setFullStatus(IndexStatus fullStatus) {
		this.fullStatus = fullStatus;
	}

	public UrlList getUrlList() {
		return urlList;
	}

	public void setUrlList(UrlList urlList) {
		this.urlList = urlList;
	}

}
