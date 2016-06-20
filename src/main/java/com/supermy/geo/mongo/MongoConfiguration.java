package com.supermy.geo.mongo;


import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import org.springframework.context.annotation.Configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;


import java.util.Arrays;

/**
 * Created by moyong on 15/4/1.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class MongoConfiguration {

    //private MongoClient mongoClient;

    @Value("${mongo.address}")
    private String mongoAddress;

    @Value("${mongo.user}")
    private String mongoUser;

    @Value("${mongo.passwd}")
    private String mongoPasswd;


    @Value("${mongo.db}")
    private String mydb;

    @Value("${mongo.coll}")
    private String mycoll;



    public @Bean MongoDbFactory mongoDbFactory() throws Exception {

        MongoCredential credential = MongoCredential.createCredential(mongoUser,
                mydb,
                mongoPasswd.toCharArray());

        MongoClient mongoClient = new MongoClient(new ServerAddress(), Arrays.asList(credential));


        return new SimpleMongoDbFactory(mongoClient,mydb);


    }

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }


}
