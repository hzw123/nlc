package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.nlc.nraas.domain.Task;

@Component
@RepositoryEventHandler(TaskRepository.class)
public class TaskRepositoryEventHandler {

    @HandleAfterCreate
    public Task handleAfterCreate(Task task) {
    	return null;
    }
}