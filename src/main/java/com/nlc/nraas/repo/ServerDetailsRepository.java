package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.ServerDetails;
@RepositoryRestResource
public interface ServerDetailsRepository extends JpaPage<ServerDetails,Long> {

}
