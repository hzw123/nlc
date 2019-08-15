package com.nlc.nraas.domain;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nlc.nraas.enums.UserStatus;
import com.nlc.nraas.tools.JsonEnumSerializer;

@Entity
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(unique = true, nullable = false)
	private String name;
	
	private UserStatus status=UserStatus.ENABLE;
	
	private long number;
	
	public Role(){
		
	}
	
	public Role(String name) {
		super();
		this.name = name;
	}
	
	public Role(Long id, String name, UserStatus status, long number) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.number = number;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@JsonSerialize(using=JsonEnumSerializer.class)
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}
	
}
