package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.CollectCompleteInform;
@RepositoryRestResource
public interface CollectCompleteInformRepository 
	extends JpaPage<CollectCompleteInform, Long> {

}
