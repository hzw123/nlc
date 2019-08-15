package com.nlc.nraas.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 编目质检不合格
 * @author Dell
 *
 */
@Entity
public class CataloQualityNot {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Catalogue catalogue;
	/**原因*/
	private String reason;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Catalogue getCatalogue() {
		return catalogue;
	}
	public void setCatalogue(Catalogue catalogue) {
		this.catalogue = catalogue;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
