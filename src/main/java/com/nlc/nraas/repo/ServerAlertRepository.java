package com.nlc.nraas.repo;

import java.util.List;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.ServerAlert;
import com.nlc.nraas.enums.ReadStatus;
@RepositoryRestResource
public interface ServerAlertRepository extends JpaPage<ServerAlert,Long> {

	List<ServerAlert> findByStatus(ReadStatus status);
}
