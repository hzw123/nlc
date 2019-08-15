package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.CheckList;
@RepositoryRestResource
public interface CheckListRepository extends JpaPage<CheckList,Long> {

}
