package com.nlc.nraas.domain;

import javax.persistence.*;
/**
 * 服务器异常详情
 * @author Dell
 *
 */
@Entity
public class ServerDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	/**级别*/
	private String level;
	/**信息*/
	private String info;
	/**异常*/
	private String abnormal;
	/**标识符*/
	private String tag;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ServerAlert serverAlert;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getAbnormal() {
		return abnormal;
	}
	public void setAbnormal(String abnormal) {
		this.abnormal = abnormal;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public ServerAlert getServerAlert() {
		return serverAlert;
	}
	public void setServerAlert(ServerAlert serverAlert) {
		this.serverAlert = serverAlert;
	}
}
