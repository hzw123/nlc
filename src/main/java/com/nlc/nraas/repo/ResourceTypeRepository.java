package com.nlc.nraas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.nlc.nraas.domain.ResourceType;

@RepositoryRestResource
public interface ResourceTypeRepository extends JpaPage<ResourceType, Long> {
	@Query(value = "select max(local) from resource_type", nativeQuery = true)
	public int maxLocal();

	@Query(value = "select *from (select *from resource_type where local<:local order by local desc )l limit 0,1", nativeQuery = true)
	public ResourceType before(@Param(value = "local") int local);

	@Query(value = "select *from resource_type where local<:local order by local asc", nativeQuery = true)
	public List<ResourceType> getBefore(@Param(value = "local") int local);

	@Query(value = "select *from (select *from resource_type where local>:local order by local asc )l limit 0,1", nativeQuery = true)
	public ResourceType after(@Param(value = "local") int local);

	@Query(value = "select *from resource_type where local>:local order by local asc", nativeQuery = true)
	public List<ResourceType> getAfter(@Param(value = "local") int local);

	@Query(value = "update resource_type set local=:local where id=:id", nativeQuery = true)
	@Modifying
	@Transactional
	public void upLocal(@Param(value = "id") long id, @Param(value = "local") int local);
}
