package com.supermy.base.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * Created by moyong on 16/10/26.
 * 接下来我们来创建一个自定义的RepositoryFactoryBean来代替默认的RepositoryFactoryBean。RepositoryFactoryBean负责返回一个
 * RepositoryFactory，Spring Data Jpa 将使用RepositoryFactory来创建Repository具体实现，这里我们用BaseRepositoryImpl
 * 代替SimpleJpaRepository作为Repository接口的实现。这样我们就能够达到为所有Repository添加自定义方法的目的。
 */


public class BaseRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends
        JpaRepositoryFactoryBean<R, T, I> {

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new MyRepositoryFactory(em);
    }

    private static class MyRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

        private final EntityManager em;

        public MyRepositoryFactory(EntityManager em) {
            super(em);
            this.em = em;
        }

//        @Override
//        protected Object getTargetRepository(RepositoryMetadata metadata) {
//            return new BaseRepositoryImpl<T, I>((Class<T>) metadata.getDomainType(), em);
//        }

        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            return new BaseRepositoryImpl<T, I>((Class<T>) information.getDomainType(), em);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;
        }
    }
}