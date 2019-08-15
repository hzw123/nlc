package com.nlc.nraas.repo;

import java.util.List;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.IndexerAlert;
import com.nlc.nraas.enums.ReadStatus;
@RepositoryRestResource
public interface IndexerAlertRepository extends JpaPage<IndexerAlert,Long>{
	List<IndexerAlert> findByStatus(ReadStatus status);
}
