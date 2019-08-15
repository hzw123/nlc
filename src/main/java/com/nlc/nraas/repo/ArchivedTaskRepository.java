package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.ArchivedTask;
@RepositoryRestResource
public interface ArchivedTaskRepository extends JpaPage<ArchivedTask, Long> {

}
