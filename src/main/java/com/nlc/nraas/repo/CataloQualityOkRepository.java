package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.nlc.nraas.domain.CataloQualityOk;
@RepositoryRestResource
public interface CataloQualityOkRepository extends JpaPage<CataloQualityOk,Long> {

}
