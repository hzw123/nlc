package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.TopicType;
@RepositoryRestResource
public interface TopicTypeRepository extends JpaPage<TopicType, Long> {

	TopicType findByTypeName(String name);
}
