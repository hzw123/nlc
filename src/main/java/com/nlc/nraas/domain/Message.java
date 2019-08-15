package com.nlc.nraas.domain;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.ReadStatus;
import com.nlc.nraas.tools.JsonDateSerializer;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * 通知
 * 
 * @author Dell
 *
 */
@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private Date finishAt;
	/** 通知的任务名称 */
	private String taskName;
	/** 通知的用户 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User user;
	/** 消息内容 */
	private String messages;
	private ReadStatus status = ReadStatus.NOT_READ;

	public long getId() {
		return id;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getFinishAt() {
		return finishAt;
	}

	public void setFinishAt(Date finishAt) {
		this.finishAt = finishAt;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public ReadStatus getStatus() {
		return status;
	}

	public void setStatus(ReadStatus status) {
		this.status = status;
	}

	public void setId(long id) {
		this.id = id;
	}
}
