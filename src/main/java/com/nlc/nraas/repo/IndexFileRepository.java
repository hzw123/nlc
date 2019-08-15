package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.IndexFile;
@RepositoryRestResource
public interface IndexFileRepository extends JpaPage<IndexFile, Long> {

}
