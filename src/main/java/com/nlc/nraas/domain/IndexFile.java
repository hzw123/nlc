package com.nlc.nraas.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.IndexStatus;
import com.nlc.nraas.tools.JsonEnumSerializer;
import com.nlc.nraas.tools.JsonLongTimeSerializer;

/**
 * 索引文件类
 * 
 * @author Dell
 *
 */
@Entity
public class IndexFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 索引名称 */
	private String name;
	/** 文件大小 */
	private long fileSize;
	/** 已建立的文档数量 */
	private long buildDocCount;
	/** 文档总数 */
	private long DocTotal;
	/** URL索引 */
	private String urlIndex;
	/** URL索引状态 */
	private IndexStatus urlStatus = IndexStatus.NOT_BUILD;
	/** URL已用时间 */
	private long urlElapsedTime;
	/** URL剩余时间 */
	private long urlRemainingTime;
	/** 全文索引 */
	private String fullIndex;
	/** 全文索引状态 */
	private IndexStatus fullStatus = IndexStatus.NOT_BUILD;
	/** 全文已用时间 */
	private long fullElapsedTime;
	/** 全文剩余时间 */
	private long fullRemainingTime;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Task task;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public long getBuildDocCount() {
		return buildDocCount;
	}

	public void setBuildDocCount(long buildDocCount) {
		this.buildDocCount = buildDocCount;
	}

	public long getDocTotal() {
		return DocTotal;
	}

	public void setDocTotal(long docTotal) {
		DocTotal = docTotal;
	}

	public String getUrlIndex() {
		return urlIndex;
	}

	public void setUrlIndex(String urlIndex) {
		this.urlIndex = urlIndex;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public IndexStatus getUrlStatus() {
		return urlStatus;
	}

	public void setUrlStatus(IndexStatus urlStatus) {
		this.urlStatus = urlStatus;
	}

	public long getUrlElapsedTime() {
		return urlElapsedTime;
	}

	public void setUrlElapsedTime(long urlElapsedTime) {
		this.urlElapsedTime = urlElapsedTime;
	}

	public long getUrlRemainingTime() {
		return urlRemainingTime;
	}

	public void setUrlRemainingTime(long urlRemainingTime) {
		this.urlRemainingTime = urlRemainingTime;
	}

	public String getFullIndex() {
		return fullIndex;
	}

	public void setFullIndex(String fullIndex) {
		this.fullIndex = fullIndex;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public IndexStatus getFullStatus() {
		return fullStatus;
	}

	public void setFullStatus(IndexStatus fullStatus) {
		this.fullStatus = fullStatus;
	}

	@JsonSerialize(using = JsonLongTimeSerializer.class)
	public long getFullElapsedTime() {
		return fullElapsedTime;
	}

	public void setFullElapsedTime(long fullElapsedTime) {
		this.fullElapsedTime = fullElapsedTime;
	}

	@JsonSerialize(using = JsonLongTimeSerializer.class)
	public long getFullRemainingTime() {
		return fullRemainingTime;
	}

	public void setFullRemainingTime(long fullRemainingTime) {
		this.fullRemainingTime = fullRemainingTime;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
