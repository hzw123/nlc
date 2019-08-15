package com.nlc.nraas.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.SaveStatus;
import com.nlc.nraas.tools.JsonDateSerializer;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * 任务数据储存
 * 
 * @author Dell
 */
@Entity
public class TaskSave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 储存时间 */
	private Date saveAt;
	private SaveStatus saveStatus = SaveStatus.NOT_STORE;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private CategoryList categoryList;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private DoneIndexList doneIndexList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getSaveAt() {
		return saveAt;
	}

	public void setSaveAt(Date saveAt) {
		this.saveAt = saveAt;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public SaveStatus getSaveStatus() {
		return saveStatus;
	}

	public void setSaveStatus(SaveStatus saveStatus) {
		this.saveStatus = saveStatus;
	}

	public CategoryList getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(CategoryList categoryList) {
		this.categoryList = categoryList;
	}

	public DoneIndexList getDoneIndexList() {
		return doneIndexList;
	}

	public void setDoneIndexList(DoneIndexList doneIndexList) {
		this.doneIndexList = doneIndexList;
	}

}
