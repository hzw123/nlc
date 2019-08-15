package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nlc.nraas.domain.ChannelContent;
@RepositoryRestResource
public interface ChannelContentRepository extends JpaPage<ChannelContent, Long> {

}
