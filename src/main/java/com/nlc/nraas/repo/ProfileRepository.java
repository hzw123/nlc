package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.Profile;
@RepositoryRestResource
public interface ProfileRepository extends JpaPage<Profile, Long> {

}
