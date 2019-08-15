package com.nlc.nraas.domain;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

import com.nlc.nraas.enums.Status;

/**
 * 参数模板
 * 
 * @author Dell
 *
 */
@Entity
public class Template {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 模版名称 */
	@NotEmpty(message = "模版名称不能为空")
	@Column(nullable = false)
	private String skinname;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private TemplateType type;
	/** 模板内容html */
	private String html;
	/** 模板状态 */
	private Status status = Status.PAUSE;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSkinname() {
		return skinname;
	}

	public void setSkinname(String skinname) {
		this.skinname = skinname;
	}

	public TemplateType getType() {
		return type;
	}

	public void setType(TemplateType type) {
		this.type = type;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
