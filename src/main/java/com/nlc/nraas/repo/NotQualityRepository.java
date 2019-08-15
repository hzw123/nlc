package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.NotQuality;
@RepositoryRestResource
public interface NotQualityRepository extends JpaPage<NotQuality,Long> {

}
