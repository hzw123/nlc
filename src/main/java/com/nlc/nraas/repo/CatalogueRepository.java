package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.Catalogue;
@RepositoryRestResource
public interface CatalogueRepository extends JpaPage<Catalogue,Long> {

}
