package com.nlc.nraas.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 分类列表
 * 
 * @author Dell
 *
 */
@Entity
public class CategoryList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/** 分类名称 */
	private String name;
	/** 任务数 */
	private long taskNumber;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<Task> tasks;
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

	public long getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(long taskNumber) {
		this.taskNumber = taskNumber;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

}
