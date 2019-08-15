package com.nlc.nraas.domain;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.AlertType;
import com.nlc.nraas.enums.ReadStatus;
import com.nlc.nraas.tools.JsonDateSerializer;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * 服务器报警信息类
 * 
 * @author Dell
 *
 */
@Entity
public class ServerAlert {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 报警时间 */
	@Column(unique = true, nullable = false)
	private Date alarmAt;
	/** 采集任务 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Task task;
	/** 级别 */
	private int rank;
	/** 报警原因 */
	private String reason;
	/** 报警类型 */
	private AlertType type = AlertType.SERVER_ALERT;
	private ReadStatus status = ReadStatus.NOT_READ;

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

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public AlertType getType() {
		return type;
	}

	public void setType(AlertType type) {
		this.type = type;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public ReadStatus getStatus() {
		return status;
	}

	public void setStatus(ReadStatus status) {
		this.status = status;
	}
}
