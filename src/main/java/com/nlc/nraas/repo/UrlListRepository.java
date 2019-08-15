package com.nlc.nraas.repo;

import org.springframework.stereotype.Component;

import com.nlc.nraas.domain.UrlList;
@Component
public interface UrlListRepository extends JpaPage<UrlList, Long> {
	
}
