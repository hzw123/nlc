package com.nlc.nraas.repo;

import java.util.List;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.nlc.nraas.domain.User;
@RepositoryRestResource
public interface UserRepository extends JpaPage<User, Long> {

	User findByName(String name);
	/**
	 * 查询机构下的用户
	 * @param id
	 * @return
	 */
	List<User> findByOrganizationId(long id);
}

