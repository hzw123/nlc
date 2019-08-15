package com.nlc.nraas.domain;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.ReadStatus;
import com.nlc.nraas.tools.JsonDateSerializer;
import com.nlc.nraas.tools.JsonEnumSerializer;

/**
 * 来信的信息
 * 
 * @author Dell
 *
 */
@Entity
@Table
public class Mail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 来信时间 */
	private Date letterAt;
	/** 送信的名字 */
	@NotEmpty(message = "送信人不能为空")
	@Column(nullable = false)
	private String name;
	/** 送信的email */
	private String email;
	/** 送信的电话 */
	private String phone;
	/** 描述信息 */
	@Column(nullable = false)
	private String description;
	/** 收信的用户 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User user;
	private ReadStatus status = ReadStatus.NOT_READ;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLetterAt() {
		return letterAt;
	}

	public void setLetterAt(Date letterAt) {
		this.letterAt = letterAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonSerialize(using = JsonEnumSerializer.class)
	public ReadStatus getStatus() {
		return status;
	}

	public void setStatus(ReadStatus status) {
		this.status = status;
	}

}
