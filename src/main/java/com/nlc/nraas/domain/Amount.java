package com.nlc.nraas.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 统计增量
 * @author Dell
 *
 */
@Entity
public class Amount {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	/**月增量*/
	private long mouthIt;
	/**年增量*/
	private long yearIt;
	/**总量*/
	private long total;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMouthIt() {
		return mouthIt;
	}
	public void setMouthIt(long mouthIt) {
		this.mouthIt = mouthIt;
	}
	public long getYearIt() {
		return yearIt;
	}
	public void setYearIt(long yearIt) {
		this.yearIt = yearIt;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
}
