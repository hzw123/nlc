package com.nlc.nraas.domain;

import javax.persistence.*;

/***
 * 已完成索引的任务列表
 * 
 * @author Dell
 *
 */
@Entity
public class DoneIndexList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private UrlList urlList;
	/** 索引回放链接 */
	private String urlIndex;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UrlList getUrlList() {
		return urlList;
	}

	public void setUrlList(UrlList urlList) {
		this.urlList = urlList;
	}

	public String getUrlIndex() {
		return urlIndex;
	}

	public void setUrlIndex(String urlIndex) {
		this.urlIndex = urlIndex;
	}
}
