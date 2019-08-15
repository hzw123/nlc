package com.nlc.nraas.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.nlc.nraas.domain.ResourceList;
@RepositoryRestResource
public interface ResourceListRepository extends JpaPage<ResourceList, Long> {

	@Query(value="select max(local) from resource_list",nativeQuery=true)
	public int maxLocal();
	
	@Query(value="update resource_list set local=:local where id=:id",nativeQuery=true)
	@Modifying
	@Transactional
	public void upLocal(@Param(value = "id") long id,@Param(value = "local") int local);
}
