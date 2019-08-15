package com.nlc.nraas.domain;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

import com.nlc.nraas.enums.OrganizationStatus;
/**
 * 机构
 * @author Dell
 *
 */
@Entity 
public class Organization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/**机构名称*/
	@NotEmpty(message="机构名称不能为空")
	@Column(unique = true, nullable = false)
	private String name;
	/**机构代码*/
	@NotEmpty(message="机构代码不能为空")
	@Column(unique = true, nullable = false)
	private String code;
	/**联系人*/
	private String contactsName;
	/**联系电话*/
	private String phone;
	/**机构状态*/
	private OrganizationStatus status=OrganizationStatus.START;
	/**用户数*/
	private long number;
	public Organization(){
		
	}
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContactsName() {
		return contactsName;
	}
	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public OrganizationStatus getStatus() {
		return status;
	}
	public void setStatus(OrganizationStatus status) {
		this.status = status;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
}
