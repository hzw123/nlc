package com.nlc.nraas.repo;


import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.ServerThread;
@RepositoryRestResource
public interface ServerThreadRepository extends JpaPage<ServerThread, Long> {
}
