package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.DoneIndexList;
@RepositoryRestResource
public interface DoneIndexListRepository extends JpaPage<DoneIndexList,Long>{

}
