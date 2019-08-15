package com.nlc.nraas.domain;

import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.UserStatus;
import com.nlc.nraas.tools.JsonDateSerializer;
import com.nlc.nraas.tools.JsonEnumSerializer;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "用户名称不能为空")
	@Column(unique = true, nullable = false)
	private String name;

	@NotEmpty(message = "密码不能为空")
	private String password;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Role> roles;

	private UserStatus status = UserStatus.ENABLE;

	/** 机构 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Organization organization;
	/** 用户代码 */
	@Column(length = 2)
	private String code;
	/** 昵称 */
	private String nickName;

	private String email;

	/** 创建时间 */
	private Date createAt;

	/** 修改时间 */
	private Date updateAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@JsonSerialize(using=JsonEnumSerializer.class)
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
}