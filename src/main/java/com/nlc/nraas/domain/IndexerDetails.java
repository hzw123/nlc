package com.nlc.nraas.domain;

import javax.persistence.*;

/**
 * 索引异常详情类
 * @author Dell
 *
 */
@Entity
public class IndexerDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	/**暂停原因*/
	private String stopReason;
	/**索引异常*/
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private IndexerAlert indexerAlert;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStopReason() {
		return stopReason;
	}
	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}
	public IndexerAlert getIndexerAlert() {
		return indexerAlert;
	}
	public void setIndexerAlert(IndexerAlert indexerAlert) {
		this.indexerAlert = indexerAlert;
	}
}
