package com.nlc.nraas.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 添加栏目内容
 * @author Dell
 *
 */
@Entity
public class ChannelContent {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String img;
	/**发布格式*/
	@NotEmpty(message="格式不能为空")
	private String type;
	/**页面内容*/
	@Column(nullable=true)
	private String pageContent;
	/**关键词*/
	private String keyWord;
	/**页面链接*/
	private String url;
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Programa programa;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPageContent() {
		return pageContent;
	}
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
