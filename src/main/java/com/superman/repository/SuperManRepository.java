package com.superman.repository;

import com.superman.domain.SuperMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "superman", path = "superman")
public interface SuperManRepository extends JpaRepository<SuperMan, Long> {


}