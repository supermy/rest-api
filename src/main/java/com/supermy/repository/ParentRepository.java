package com.supermy.repository;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.supermy.domain.Parent;
import com.supermy.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;
//import org.springframework.security.access.prepost.PostAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;

/**
 * http://spring.io/understanding/HATEOAS
 * http://spring.io/understanding/REST
 * http://spring.io/guides/gs/accessing-data-rest/
 *
 * 自动实现rest 框架;
 * 支持Name 约定；
 * 支持hsql 查询；
 * 支持原生sql 查询；
 *
 */

@RepositoryRestResource(collectionResourceRel = "parent", path = "parent")
public interface ParentRepository extends JpaRepository<Parent, Long> {

    List findByName(@Param("name") String name);

    @Query(value = "select u.id,u.name,u.code from Parent u where u.name = :name")
    Object[] findSpecName(@Param("name") String name);

    //n还可以使用@Query来指定本地查询原生sql，只要设置nativeQuery为true，比如：
    //select * from t_parent where name like '111,name2=222' or name like NULL
    @Query(value = "select * from t_parent where name like %:name1 or name like %:name2 ",nativeQuery=true)
    List findNativeName(@Param("name1") String name1, @Param("name2") String name2);

}