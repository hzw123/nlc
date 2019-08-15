package com.nlc.nraas.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
/**
 * WARC文件索引
 * @author Dell
 *
 */
@Entity
public class WarcFile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	/**文件名称*/
	private String name;
	/**用时*/
	private long totalTime;
	private long size;
	/**文档总数*/
	private long totalDocument;
	private String urlIndex;
	private String fullIndex;
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
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
	public long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getTotalDocument() {
		return totalDocument;
	}
	public void setTotalDocument(long totalDocument) {
		this.totalDocument = totalDocument;
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
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
}
