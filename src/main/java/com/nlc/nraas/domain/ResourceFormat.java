package com.nlc.nraas.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/***
 * 资源格式
 * @author Dell
 *
 */
@Entity
public class ResourceFormat {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	/**资源格式名称*/
	private String format;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}
