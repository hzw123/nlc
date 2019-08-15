package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.IndexCompleteInform;
@RepositoryRestResource
public interface IndexCompleteInformRepository 
		extends JpaPage<IndexCompleteInform, Long> {

}
