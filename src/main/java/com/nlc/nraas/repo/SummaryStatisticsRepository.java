package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.SummaryStatistics;
@RepositoryRestResource
public interface SummaryStatisticsRepository extends JpaPage<SummaryStatistics, Long> {

}
