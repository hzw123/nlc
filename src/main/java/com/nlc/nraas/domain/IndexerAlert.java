package com.nlc.nraas.domain;

import java.sql.Date;
import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.AlertType;
import com.nlc.nraas.enums.IndexType;
import com.nlc.nraas.enums.ReadStatus;
import com.nlc.nraas.tools.JsonDateSerializer;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * 索引异常
 * 
 * @author Dell
 *
 */
@Entity
public class IndexerAlert {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 报警时间 */
	@Column(nullable = false)
	private Date alarmAt;
	/** 报警原因 */
	private AlertType reason;
	/** 索引 */
	private IndexType type;
	/** 状态 */
	private ReadStatus status = ReadStatus.NOT_READ;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Task task;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getAlarmAt() {
		return alarmAt;
	}

	public void setAlarmAt(Date alarmAt) {
		this.alarmAt = alarmAt;
	}

	public AlertType getReason() {
		return reason;
	}

	public void setReason(AlertType reason) {
		this.reason = reason;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public IndexType getType() {
		return type;
	}

	public void setType(IndexType type) {
		this.type = type;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public ReadStatus getStatus() {
		return status;
	}

	public void setStatus(ReadStatus status) {
		this.status = status;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
