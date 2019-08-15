package com.nlc.nraas.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.ReadStatus;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * 已完成索引的消息类
 * 
 * @author Dell
 *
 */
@Entity
public class IndexCompleteInform {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private DoneIndexList doneIndexList;
	/** 消息内容 */
	private String info;
	private ReadStatus status = ReadStatus.NOT_READ;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public ReadStatus getStatus() {
		return status;
	}

	public void setStatus(ReadStatus status) {
		this.status = status;
	}

	public DoneIndexList getDoneIndexList() {
		return doneIndexList;
	}

	public void setDoneIndexList(DoneIndexList doneIndexList) {
		this.doneIndexList = doneIndexList;
	}
}
