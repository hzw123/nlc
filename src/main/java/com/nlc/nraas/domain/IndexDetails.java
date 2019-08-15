package com.nlc.nraas.domain;

import javax.persistence.*;
/**
 * 索引消息详情类
 * @author Dell
 */
@Entity
public class IndexDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private IndexCompleteInform indexCompleteInform;
	/**URL索引*/
	private String urlIndex;
	/**全文索引*/
	private String fullIndex;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public IndexCompleteInform getIndexCompleteInform() {
		return indexCompleteInform;
	}
	public void setIndexCompleteInform(IndexCompleteInform indexCompleteInform) {
		this.indexCompleteInform = indexCompleteInform;
	}
	public String getUrlIndex() {
		return urlIndex;
	}
	public void setUrlIndex(String urlIndex) {
		this.urlIndex = urlIndex;
	}
	public String getFullIndex() {
		return fullIndex;
	}
	public void setFullIndex(String fullIndex) {
		this.fullIndex = fullIndex;
	}

}
