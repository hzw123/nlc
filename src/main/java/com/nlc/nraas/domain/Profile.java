package com.nlc.nraas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * heritrix模板
 *
 */
@Entity
public class Profile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	/**参数模版名称*/
	@NotEmpty(message = "模版名称不能为空") 
	@Column(unique = true, nullable = false)
	private String name;
	/**模版描述*/
	private String description;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
