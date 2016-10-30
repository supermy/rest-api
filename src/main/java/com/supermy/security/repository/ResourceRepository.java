package com.supermy.security.repository;

import com.supermy.security.domain.Group;
import com.supermy.security.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * 资源清单
 */
@RepositoryRestResource(collectionResourceRel = "resource", path = "resource")
public interface ResourceRepository extends JpaRepository<Resource, Long> , JpaSpecificationExecutor<Resource> {

    public List<Resource> findAllByOrderByModuleAsc();
    public List<Resource> findTop10ByOrderByModuleDesc();
    public List<Resource> findAllByPidOrderByModuleAsc(@Param("pid") String pid);
    @Query(value = "select u from Resource u where u.id in :pid")
    public List<Resource> findAll(@Param("pid") String pid);


}