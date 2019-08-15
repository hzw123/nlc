package com.nlc.nraas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.nlc.nraas.domain.MyResouceList;

@RepositoryRestResource
public interface MyResouceListRepository extends JpaPage<MyResouceList, Long> {

	@Query(value = "select max(local) from my_resource_list", nativeQuery = true)
	public int maxLocal();

	@Query(value = "select *from my_resource_list where local<:local order by local asc", nativeQuery = true)
	public List<MyResouceList> before(@Param(value = "local") int local);

	@Query(value = "select *from my_resource_list where local>:local order by local asc", nativeQuery = true)
	public List<MyResouceList> after(@Param(value = "local") int local);

	@Query(value = "update my_resource_list set local=:local where id=:id", nativeQuery = true)
	@Modifying
	@Transactional
	public void upLocal(@Param(value = "id") long id, @Param(value = "local") int local);
}
