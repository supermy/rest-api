package com.supermy.security;

import com.supermy.security.domain.Group;
import com.supermy.security.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 资源清单
 */
@RepositoryRestResource(collectionResourceRel = "resource", path = "resource")
public interface ResourceRepository extends JpaRepository<Resource, Long> , JpaSpecificationExecutor<Resource> {


}