package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.nlc.nraas.domain.Role;
import com.nlc.nraas.domain.User;

@RepositoryRestResource
public interface RoleRepository extends JpaPage<Role, Long> {
	User findByName(String name);
}
