package com.nlc.nraas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.nlc.nraas.domain.Programa;

@RepositoryRestResource
public interface ProgramaRepository extends JpaPage<Programa, Long> {

	Programa findByName(String name);

	@Query(value = "select max(local) from programa where pid=:pid", nativeQuery = true)
	public int maxLocal(@Param(value = "pid") long pid);

	@Query(value = "update programa set local=:local,pid=:pid where id=:id", nativeQuery = true)
	@Modifying(clearAutomatically = true)
	@Transactional
	public void upLocal(@Param(value = "id") long id, @Param(value = "pid") long pid,
			@Param(value = "local") int local);

	@Query(value = "update programa set local=:local where id=:id", nativeQuery = true)
	@Modifying(clearAutomatically = true)
	@Transactional
	public void upLocal(@Param(value = "id") long id, @Param(value = "local") int local);

	@Query(value = "insert into programa_programas (programa_id,programas_id) values(:pid,:id)", nativeQuery = true)
	@Modifying(clearAutomatically = true)
	@Transactional
	public void addSet(@Param(value = "pid") long pid, @Param(value = "id") long id);

	@Query(value = "update programa_programas set programa_id=:newId where programa_id=:pid and programas_id=:id", nativeQuery = true)
	@Modifying(clearAutomatically = true)
	@Transactional
	public void upSet(@Param(value = "pid") long pid, @Param(value = "id") long id, @Param(value = "newId") long newId);

	@Query(value = "select *from (select *from programa where pid=:pid and local<:local order by local desc) p limit 0,1", nativeQuery = true)
	public Programa getBefore(@Param(value = "pid") long pid, @Param(value = "local") long local);

	@Query(value = "select *from (select *from programa where pid=:pid and local>:local order by local asc) p limit 0,1", nativeQuery = true)
	public Programa getAfter(@Param(value = "pid") long pid, @Param(value = "local") long local);

	@Query(value = "delete from programa_programas where programas_id=:id or programa_id=:id", nativeQuery = true)
	@Modifying(clearAutomatically = true)
	@Transactional
	public void deleteSet(@Param(value = "id") long id);

	@Query(value = "select *from programa where pid=:pid and local<:local order by local asc", nativeQuery = true)
	public List<Programa> before(@Param(value = "pid") long pid, @Param(value = "local") long local);

	@Query(value = "select *from programa where pid=:pid and local>:local order by local asc", nativeQuery = true)
	public List<Programa> after(@Param(value = "pid") long pid, @Param(value = "local") long local);

}
