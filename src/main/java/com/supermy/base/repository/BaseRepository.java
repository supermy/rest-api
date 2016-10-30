package com.supermy.base.repository;

import com.google.common.base.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by moyong on 16/10/26.
 * 首先我们来添加一个自定义的接口：

 添加BaseRepository接口
 BaseRepository继承了PagingAndSortingRepository，这样可以保证所有Repository都有基本的增删改查以及分页等方法。
 在BaseRepository上添加@NoRepositoryBean标注，这样Spring Data Jpa在启动时就不会去实例化BaseRepository这个接口
 添加support(String modelType)方法，表示该Repository的领域对象是否为modelType类型
 然后，使所有Repository接口都继承BaseRepository
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    boolean support(String modelType);

    Optional<T> deleteById(ID id);


}