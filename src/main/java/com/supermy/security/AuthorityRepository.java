package com.supermy.security;

import com.supermy.security.domain.Authority;
import com.supermy.security.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * 权限清单
 */
@RepositoryRestResource(collectionResourceRel = "auth", path = "auth")
public interface AuthorityRepository extends JpaRepository<Authority, Long> , JpaSpecificationExecutor<Authority> {


}