package com.nlc.nraas.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.tools.JsonDateSerializer;

/**
 * 储存信息
 * 
 * @author Dell
 *
 */
@Entity
public class SaveInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 数据完整性校验信息 */
	private String validateInfo;
	/** 封装文件 */
	private String[] documents;
	/** 储存路径 */
	private String saveWay;
	/** 文件大小G */
	private double count;
	private Date lastAt;
	/** 网址 */
	private String url;
	/** 储存人名字 */
	private String name;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private CataloQualityOk cataloQualityOk;
	/** 服务器 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Server server;
	/** 备注信息 */
	private String remarks;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValidateInfo() {
		return validateInfo;
	}

	public void setValidateInfo(String validateInfo) {
		this.validateInfo = validateInfo;
	}

	public String[] getDocuments() {
		return documents;
	}

	public void setDocuments(String[] documents) {
		this.documents = documents;
	}

	public String getSaveWay() {
		return saveWay;
	}

	public void setSaveWay(String saveWay) {
		this.saveWay = saveWay;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLastAt() {
		return lastAt;
	}

	public void setLastAt(Date lastAt) {
		this.lastAt = lastAt;
	}

	public CataloQualityOk getCataloQualityOk() {
		return cataloQualityOk;
	}

	public void setCataloQualityOk(CataloQualityOk cataloQualityOk) {
		this.cataloQualityOk = cataloQualityOk;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
