package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.IndexDetails;
@RepositoryRestResource
public interface IndexDetailsRepository extends JpaPage<IndexDetails,Long> {

}
