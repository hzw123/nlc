package com.nlc.nraas.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.ServerStatus;
import com.nlc.nraas.tools.JsonEnumSerializer;

@Entity
public class Server {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** SERVER NAME */
	@NotEmpty(message = "服务器名称不能为空")
	@Column(nullable = false)
	private String name;
	/** SERVERIP */
	@NotEmpty(message = "服务器ip不能为空")
	@Column(nullable = false, unique = true)
	private String ip;

	private String jmxport;
	/** server status */
	private ServerStatus status = ServerStatus.NORMAL;
	/** Have used the thread */
	private long userThread;
	/** The idle thread number */
	private long idleThread;
	/** The total number of threads */
	private long totalThread;

	@OneToMany(cascade = { CascadeType.REMOVE }, fetch = FetchType.EAGER, mappedBy = "server")
	@OrderBy("id asc")
	private Set<ServerThread> serverThreads;

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public ServerStatus getStatus() {
		return status;
	}

	public void setStatus(ServerStatus status) {
		this.status = status;
	}

	public long getUserThread() {
		return userThread;
	}

	public void setUserThread(long userThread) {
		this.userThread = userThread;
	}

	public long getIdleThread() {
		return totalThread - userThread;
	}

	public long getTotalThread() {
		return totalThread;
	}

	public void setTotalThread(long totalThread) {
		this.totalThread = totalThread;
	}

	public String getJmxport() {
		return jmxport;
	}

	public void setJmxport(String jmxport) {
		this.jmxport = jmxport;
	}

	public Set<ServerThread> getServerThreads() {
		return serverThreads;
	}

	public void setServerThreads(Set<ServerThread> serverThreads) {
		this.serverThreads = serverThreads;
	}
}
