package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.Message;
@RepositoryRestResource
public interface MessageRepository extends JpaPage<Message,Long> {

}
