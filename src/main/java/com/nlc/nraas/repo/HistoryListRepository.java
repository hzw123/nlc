package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.HistoryList;
@RepositoryRestResource
public interface HistoryListRepository extends JpaPage<HistoryList,Long>{

}
