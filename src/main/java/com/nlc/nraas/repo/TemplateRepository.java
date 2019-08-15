package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.Template;
@RepositoryRestResource
public interface TemplateRepository extends JpaPage<Template, Long> {

}
