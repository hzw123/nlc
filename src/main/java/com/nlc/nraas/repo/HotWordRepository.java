package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.HotWord;
@RepositoryRestResource
public interface HotWordRepository extends JpaPage<HotWord, Long> {

}
