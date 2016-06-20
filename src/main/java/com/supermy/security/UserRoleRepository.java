package com.supermy.security;

import com.supermy.security.domain.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "userrole", path = "userrole")
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<UserRole> findAll(Pageable pageable);



}