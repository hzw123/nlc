package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.ResourceKind;

@RepositoryRestResource
public interface ResourceKindRepository extends JpaPage<ResourceKind, Long> {

}
