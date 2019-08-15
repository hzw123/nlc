package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.Task;
@RepositoryRestResource
public interface TaskRepository extends JpaPage<Task,Long>{

	public Task findById(long id);
	
	public Task findByName(String name);
}
