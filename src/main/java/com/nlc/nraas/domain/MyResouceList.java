package com.nlc.nraas.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.tools.JsonDateSerializer;

/**
 * 自定义资源列表
 * 
 * @author Dell
 *
 */
@Entity
public class MyResouceList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 文章 */
	private String article;
	/** 作者 */
	private String author;
	/** 发布时间 */
	private Date pushAt;
	/** 位置 */
	private int local;

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getPushAt() {
		return pushAt;
	}

	public void setPushAt(Date pushAt) {
		this.pushAt = pushAt;
	}
}
