package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.Organization;
@RepositoryRestResource
public interface OrganizationRepository extends JpaPage<Organization,Long> {
	/**
	 * 根据机构名称查找
	 * @return
	 */
	OrganizationRepository findByName(String name);
}
