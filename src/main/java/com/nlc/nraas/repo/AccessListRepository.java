package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.AccessList;
@RepositoryRestResource
public interface AccessListRepository extends JpaPage<AccessList,Long> {
}
