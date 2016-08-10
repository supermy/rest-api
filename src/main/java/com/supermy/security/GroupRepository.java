package com.supermy.security;

import com.supermy.security.domain.Authority;
import com.supermy.security.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 群组清单
 */
@RepositoryRestResource(collectionResourceRel = "group", path = "group")
public interface GroupRepository extends JpaRepository<Group, Long> , JpaSpecificationExecutor<Group> {


}