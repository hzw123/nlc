package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.ResourceFormat;
@RepositoryRestResource
public interface ResourceFormatRepository extends JpaPage<ResourceFormat,Long> {
}
