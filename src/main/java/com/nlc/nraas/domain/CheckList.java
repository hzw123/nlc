package com.nlc.nraas.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.CheckListStatus;
import com.nlc.nraas.tools.JsonDateSerializer;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * 检查点列表
 * 
 * @author Dell
 *
 */
@Entity
public class CheckList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private CheckListStatus status = CheckListStatus.CREATE;
	/** 创建时间 */
	private Date createAt;
	/** 创建进度 */
	private double createSchedule;
	private long size;
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

	@JsonSerialize(using = JsonEnumSerializer.class)
	public CheckListStatus getStatus() {
		return status;
	}

	public void setStatus(CheckListStatus status) {
		this.status = status;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public double getCreateSchedule() {
		return createSchedule;
	}

	public void setCreateSchedule(double createSchedule) {
		this.createSchedule = createSchedule;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
