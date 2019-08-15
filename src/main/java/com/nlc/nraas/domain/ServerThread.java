package com.nlc.nraas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
/**
 * 服务器线程列表
 * @author Dell
 *
 */
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.ServerStatus;
import com.nlc.nraas.tools.JsonEnumSerializer;

@Entity
public class ServerThread {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotEmpty(message = "实例名称不能为空")
	@Column(nullable = false)
	private String name;
	private ServerStatus status = ServerStatus.FREE;
	/** 任务数 */
	private int taskNumber;

	/**
	 * mbean's name, example:
	 * org.archive.crawler:jmxport=8849,name=Heritrix,type=CrawlService,guiport=6789,host=xxx-PC
	 */
	private String beanName;

	@ManyToOne
	private Server server;

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
	public ServerStatus getStatus() {
		return status;
	}

	public void setStatus(ServerStatus status) {
		this.status = status;
	}

	public int getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}

	public Server getServer() {
		return server;
	}

	@JsonBackReference
	public void setServer(Server server) {
		this.server = server;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
}
