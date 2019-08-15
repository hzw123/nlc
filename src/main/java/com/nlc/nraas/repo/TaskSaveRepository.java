package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.TaskSave;

@RepositoryRestResource
public interface TaskSaveRepository extends JpaPage<TaskSave, Long> {

}
