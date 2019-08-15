package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.SaveInfo;
@RepositoryRestResource
public interface SaveInfoRepository extends JpaPage<SaveInfo, Long> {

}
