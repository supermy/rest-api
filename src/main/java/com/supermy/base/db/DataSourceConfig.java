package com.supermy.base.db;

import com.Application;
import com.superman.domain.SuperMan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by moyong on 16/12/27.
 */
//@Configuration
public class DataSourceConfig {
    @Primary //默认数据源
    @Bean
    @ConfigurationProperties(prefix = "spring.druid.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean(name = "mysqlDS")
//    @Qualifier("mysqlDS")
//    @Primary
//    @ConfigurationProperties(prefix="spring.druid.mysql")
//    public DataSource primaryDataSource(){
//        return DataSourceBuilder.create().build();
//    }

    @Bean(name = "oracleDS")
    @Qualifier("oracleDS")
    @ConfigurationProperties(prefix="spring.druid.oracle")
    public DataSource oracleDataSource(){
        return DataSourceBuilder.create().build();
    }



    @Primary
    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(mysqlDataSource())
                .packages(Application.class)
                .persistenceUnit("mysql")
                .build();
        Properties properties = new Properties();
        properties.setProperty("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        em.setJpaProperties(properties);
        return em;
    }


    @Bean(name = "oracleEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(oracleDataSource())
                .packages(SuperMan.class)
                .persistenceUnit("oracle")
                .build();
        Properties properties = new Properties();
        properties.setProperty("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        em.setJpaProperties(properties);
        return em;
    }


    @Bean(name = "mysqlTransactionManager")
    @Primary
    PlatformTransactionManager mysqlTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(mysqlEntityManagerFactory(builder).getObject());
    }

    @Bean(name = "oracleTransactionManager")
    @Primary
    PlatformTransactionManager mssqlTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(oracleEntityManagerFactory(builder).getObject());
    }

}