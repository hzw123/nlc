package com.nlc.nraas.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.nlc.nraas.domain.CategoryList;
@RepositoryRestResource
public interface CategoryListRepository extends JpaPage<CategoryList,Long> {

}
