package com.nlc.nraas.domain;


public class ResourceKind {

	private long id;
	/** 资源种类 */
	private String kind;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
}
