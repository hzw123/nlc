package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.TemplateType;
@RepositoryRestResource
public interface TemplateTypeRepository extends JpaPage<TemplateType, Long> {

}
