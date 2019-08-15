package com.nlc.nraas.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.CatalogStatus;
import com.nlc.nraas.enums.QualityType;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * URL列表
 * 
 * @author Dell
 *
 */
@Entity
public class UrlList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Task task;
	private String createName;
	/** 任务完成情况 */
	private boolean falg;
	/** 编目情况 */
	private CatalogStatus status = CatalogStatus.NOT_CATALOG;
	/** 质检结果 */
	private QualityType result = QualityType.NOT_QUALITY;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public boolean isFalg() {
		return falg;
	}

	public void setFalg(boolean falg) {
		this.falg = falg;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public CatalogStatus getStatus() {
		return status;
	}

	public void setStatus(CatalogStatus status) {
		this.status = status;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public QualityType getResult() {
		return result;
	}

	public void setResult(QualityType result) {
		this.result = result;
	}

}
