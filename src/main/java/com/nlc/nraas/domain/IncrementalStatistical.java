package com.nlc.nraas.domain;

/**
 * 增量统计
 * @author Dell
 */
public class IncrementalStatistical {

	private long id;
	/**资源种类*/
	private ResourceType resourceType;
	/**资源格式*/
	private ResourceFormat resourceFormat;
	/**建设数量*/
	private Amount constructionCount;
	/**建设容量*/
	private Amount constructionCapacity;
	/**发布数量*/
	private Amount releaseCount;
	/**发布容量*/
	private Amount releaseCapacity;
	/**备注*/
	private String remarks;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ResourceType getResourceType() {
		return resourceType;
	}
	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	public ResourceFormat getResourceFormat() {
		return resourceFormat;
	}
	public void setResourceFormat(ResourceFormat resourceFormat) {
		this.resourceFormat = resourceFormat;
	}
	public Amount getConstructionCount() {
		return constructionCount;
	}
	public void setConstructionCount(Amount constructionCount) {
		this.constructionCount = constructionCount;
	}
	public Amount getConstructionCapacity() {
		return constructionCapacity;
	}
	public void setConstructionCapacity(Amount constructionCapacity) {
		this.constructionCapacity = constructionCapacity;
	}
	public Amount getReleaseCount() {
		return releaseCount;
	}
	public void setReleaseCount(Amount releaseCount) {
		this.releaseCount = releaseCount;
	}
	public Amount getReleaseCapacity() {
		return releaseCapacity;
	}
	public void setReleaseCapacity(Amount releaseCapacity) {
		this.releaseCapacity = releaseCapacity;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}