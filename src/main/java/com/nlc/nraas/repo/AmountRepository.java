package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.Amount;
@RepositoryRestResource
public interface AmountRepository extends JpaPage<Amount, Long> {

}
