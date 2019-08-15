package com.nlc.nraas.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.Status;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * 资源类型
 * 
 * @author Dell
 */
@Entity
public class ResourceType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 描述 */
	private String des;
	/** 资源类型名称 */
	private String name;
	/** 图片 */
	private String picture;
	/** 任务数 */
	private long taskNumber;
	/** 状态 */
	private Status status = Status.DISPARK;
	/** 说在位置 */
	private int local;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public long getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(long taskNumber) {
		this.taskNumber = taskNumber;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}
}
