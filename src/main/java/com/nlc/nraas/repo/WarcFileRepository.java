package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.WarcFile;

@RepositoryRestResource
public interface WarcFileRepository extends JpaPage<WarcFile, Long> {

}
