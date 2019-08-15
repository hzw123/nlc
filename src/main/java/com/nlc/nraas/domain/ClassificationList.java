package com.nlc.nraas.domain;

/**
 * 分类明细
 * 
 * @author Dell
 */
public class ClassificationList {

	private long id;
	/** url数量 */
	private long urlCount;
	/** 采集次数 */
	private long collectNumber;
	/** 压缩前大小 */
	private long sizeBc;
	/** 压缩后大小 */
	private long sizeAc;
	/** 储存服务器id */
	private String saveIp;
	/** 储存路径 */
	private String saveLoad;
	/** 种子数 */
	private long seedsCount;
	/** 发布次数 */
	private long pushCount;
	/** 运行时长 */
	private long runTime;
	/** 备注 */
	private String remarks;
	/** 资源种类 */
	private ResourceKind resourceKind;
	/** 资源列表 */
	private Catalogue Catalogue;
	/** 储存信息 */
	private SaveInfo saveInfo;
	/** 资源格式 */
	private ResourceFormat resourceFormat;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUrlCount() {
		return urlCount;
	}

	public void setUrlCount(long urlCount) {
		this.urlCount = urlCount;
	}

	public long getCollectNumber() {
		return collectNumber;
	}

	public void setCollectNumber(long collectNumber) {
		this.collectNumber = collectNumber;
	}

	public long getSizeBc() {
		return sizeBc;
	}

	public void setSizeBc(long sizeBc) {
		this.sizeBc = sizeBc;
	}

	public long getSizeAc() {
		return sizeAc;
	}

	public void setSizeAc(long sizeAc) {
		this.sizeAc = sizeAc;
	}

	public String getSaveIp() {
		return saveIp;
	}

	public void setSaveIp(String saveIp) {
		this.saveIp = saveIp;
	}

	public String getSaveLoad() {
		return saveLoad;
	}

	public void setSaveLoad(String saveLoad) {
		this.saveLoad = saveLoad;
	}

	public long getSeedsCount() {
		return seedsCount;
	}

	public void setSeedsCount(long seedsCount) {
		this.seedsCount = seedsCount;
	}

	public long getPushCount() {
		return pushCount;
	}

	public void setPushCount(long pushCount) {
		this.pushCount = pushCount;
	}

	public long getRunTime() {
		return runTime;
	}

	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ResourceKind getResourceKind() {
		return resourceKind;
	}

	public void setResourceKind(ResourceKind resourceKind) {
		this.resourceKind = resourceKind;
	}

	public Catalogue getCatalogue() {
		return Catalogue;
	}

	public void setCatalogue(Catalogue catalogue) {
		Catalogue = catalogue;
	}

	public SaveInfo getSaveInfo() {
		return saveInfo;
	}

	public void setSaveInfo(SaveInfo saveInfo) {
		this.saveInfo = saveInfo;
	}

	public ResourceFormat getResourceFormat() {
		return resourceFormat;
	}

	public void setResourceFormat(ResourceFormat resourceFormat) {
		this.resourceFormat = resourceFormat;
	}

}
