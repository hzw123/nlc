package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.IndexerDetails;
@RepositoryRestResource
public interface IndexerDetailsRepository extends JpaPage<IndexerDetails,Long> {

}
