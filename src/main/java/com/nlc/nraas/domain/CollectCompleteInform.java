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
 * 采集完成消息通知
 * 
 * @author Dell
 *
 */
@Entity
public class CollectCompleteInform {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Task task;
	/** 消息内容 */
	private String info;
	private ReadStatus status = ReadStatus.NOT_READ;

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
}
