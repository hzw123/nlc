package com.nlc.nraas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.Server;
import com.nlc.nraas.enums.ServerStatus;
@RepositoryRestResource
public interface ServerRepository extends JpaPage<Server,Long> {
	@Query(value="select * from servers where status=:status",nativeQuery=true)
	List<Server> findByStatus(ServerStatus status);

}
