package com.supermy.security;

import com.supermy.security.domain.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.supermy.security.domain.User;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

//@RestResource(exported = false)
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {

	/**
	 *
	 * @param username
	 * @return
     */
	List<User> findByUsername(@Param("username") String username);

	/**
	 *
	 * @param user
	 * @param <S>
     * @return
     */
	//@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#user, 'write')")
	<S extends User> User save(@P("user") User user);

	/**
	 *
	 * @return
     */
	//@Override
	//@PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, admin)")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	Page<User> findAll(Pageable pageable);


}