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
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.Status;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * 栏目
 * 
 * @author Dell
 */
@Entity
public class Programa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotEmpty(message = "栏目名称不能为空")
	@Column(unique = true, nullable = false)
	private String name;
	/** 配置状态 */
	private Status status = Status.DISPARK;
	/** 每一层级的位置 */
	private int local;
	/** 栏目类型 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private TopicType typeName;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Template template;
	private long pid;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Programa> programas;

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

	@JsonSerialize(using = JsonEnumSerializer.class)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public TopicType getTypeName() {
		return typeName;
	}

	public void setTypeName(TopicType typeName) {
		this.typeName = typeName;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public Set<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(Set<Programa> programas) {
		this.programas = programas;
	}

}
