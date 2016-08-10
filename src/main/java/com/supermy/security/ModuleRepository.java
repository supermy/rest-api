package com.supermy.security;

import com.supermy.security.domain.Module;
import com.supermy.security.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 模块清单
 */
@RepositoryRestResource(collectionResourceRel = "module", path = "module")
public interface ModuleRepository extends JpaRepository<Module, Long> , JpaSpecificationExecutor<Module> {


}