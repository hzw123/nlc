package com.nlc.nraas.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.PushStatus;
import com.nlc.nraas.tools.JsonDateSerializer;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * 编目
 * 
 * @author Dell
 */
@Entity
public class Catalogue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/***/
	private String url;
	/** 其他名称 */
	private String otherName;
	/** 翻译名称 */
	private String translatedName;
	/** 来源网站 */
	private String webAdrees;
	/** 语种 */
	private String language;
	/** 国别 */
	private String nationality;
	/** 行政级别 */
	private String administrativeLevel;
	/** 中图法分类号 */
	private String code;
	/** 版权信息 */
	private String copyrightInformation;
	/** 采集单位 */
	private String acquisitionUnit;
	/** 采集时间 */
	private Date acquisitionAt;
	/** 是否属于资源专题 */
	private boolean falg;
	/** 专题名称 */
	private String specialName;
	/** 专题开始时间 */
	private Date specialStartAt;
	/** 专题结束时间 */
	private Date specialStopAt;
	/** 专题介绍 */
	private String specialIntroduce;
	/** 发布时间 */
	private Date publishAt;
	/** 发布地址 */
	private String publishAdr;
	/** 备注信息 */
	private String note;
	/** 采集单位 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Organization organization;
	/** 资源类型 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ResourceType resourceType;
	/** 资源格式 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ResourceFormat resourceFormat;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private UrlList urlList;
	private PushStatus status = PushStatus.NOT_PUSH;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getTranslatedName() {
		return translatedName;
	}

	public void setTranslatedName(String translatedName) {
		this.translatedName = translatedName;
	}

	public String getWebAdrees() {
		return webAdrees;
	}

	public void setWebAdrees(String webAdrees) {
		this.webAdrees = webAdrees;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAdministrativeLevel() {
		return administrativeLevel;
	}

	public void setAdministrativeLevel(String administrativeLevel) {
		this.administrativeLevel = administrativeLevel;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCopyrightInformation() {
		return copyrightInformation;
	}

	public void setCopyrightInformation(String copyrightInformation) {
		this.copyrightInformation = copyrightInformation;
	}

	public String getAcquisitionUnit() {
		return acquisitionUnit;
	}

	public void setAcquisitionUnit(String acquisitionUnit) {
		this.acquisitionUnit = acquisitionUnit;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getAcquisitionAt() {
		return acquisitionAt;
	}

	public void setAcquisitionAt(Date acquisitionAt) {
		this.acquisitionAt = acquisitionAt;
	}

	public boolean isFalg() {
		return falg;
	}

	public void setFalg(boolean falg) {
		this.falg = falg;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getSpecialStartAt() {
		return specialStartAt;
	}

	public void setSpecialStartAt(Date specialStartAt) {
		this.specialStartAt = specialStartAt;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getSpecialStopAt() {
		return specialStopAt;
	}

	public void setSpecialStopAt(Date specialStopAt) {
		this.specialStopAt = specialStopAt;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public String getSpecialIntroduce() {
		return specialIntroduce;
	}

	public void setSpecialIntroduce(String specialIntroduce) {
		this.specialIntroduce = specialIntroduce;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getPublishAt() {
		return publishAt;
	}

	public void setPublishAt(Date publishAt) {
		this.publishAt = publishAt;
	}

	public String getPublishAdr() {
		return publishAdr;
	}

	public void setPublishAdr(String publishAdr) {
		this.publishAdr = publishAdr;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public ResourceFormat getResourceFormat() {
		return resourceFormat;
	}

	public void setResourceFormat(ResourceFormat resourceFormat) {
		this.resourceFormat = resourceFormat;
	}

	public UrlList getUrlList() {
		return urlList;
	}

	public void setUrlList(UrlList urlList) {
		this.urlList = urlList;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public PushStatus getStatus() {
		return status;
	}

	public void setStatus(PushStatus status) {
		this.status = status;
	}
}
