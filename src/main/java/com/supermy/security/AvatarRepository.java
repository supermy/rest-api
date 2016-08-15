package com.supermy.security;

import com.supermy.security.domain.Authority;
import com.supermy.security.domain.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 权限清单
 */
@RepositoryRestResource(collectionResourceRel = "avatar", path = "avatar")
public interface AvatarRepository extends JpaRepository<Avatar, Long> , JpaSpecificationExecutor<Avatar> {


}