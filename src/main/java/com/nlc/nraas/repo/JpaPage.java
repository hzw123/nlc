package com.nlc.nraas.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface JpaPage<T,ID extends Serializable> 
		extends JpaSpecificationExecutor<T>,
		PagingAndSortingRepository<T, ID>{

}
