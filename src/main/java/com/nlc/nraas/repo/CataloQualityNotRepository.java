package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.CataloQualityNot;
@RepositoryRestResource
public interface CataloQualityNotRepository extends JpaPage<CataloQualityNot, Long> {

}
