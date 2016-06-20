/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.supermy.geo.mongo;

import com.mongodb.*;
import com.mongodb.async.client.*;
import com.mongodb.async.client.MongoClient;
import com.mongodb.connection.ClusterSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * netty+mongo 异步调用;
 * 配置文件设置mongo;
 * <p/>
 * 只负责完成数据插入-司机信息:< geo:id|精度|维度|时间 >;
 */
@Component
@Qualifier("mymongo")
@PropertySource("classpath:application.properties")
public class MongoRepository {


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

    @Value("${mongo.mytasks}")
    private String mytasks;

    @Value("${mongo.upgeo}")
    public String upgeo;

    @Value("${driver.msg}")
    public String driverMsg;

    @Value("${mongo.tasks}")
    public String mongoTasks;

    @Value("${mongo.tasks1}")
    public String mongoTasks1;

    @Bean(name = "mongoClient")
    public MongoClient mongoClient() {
        System.out.println("*******************" + mongoAddress);

        ClusterSettings clusterSettings = ClusterSettings.builder().hosts(Arrays.asList(new ServerAddress(mongoAddress))).build();
        MongoCredential credential = MongoCredential.createCredential(mongoUser, mydb, mongoPasswd.toCharArray());

//        MongoClientSettings settings = MongoClientSettings.builder().streamFactoryFactory(new NettyStreamFactoryFactory()).
        MongoClientSettings settings = MongoClientSettings.builder().clusterSettings(clusterSettings).credentialList(Arrays.asList(credential)).build();

//        ClusterSettings clusterSettings = ClusterSettings.builder().hosts(asList(new ServerAddress("localhost"))).build();
//        MongoClientSettings settings = MongoClientSettings.builder().clusterSettings(clusterSettings).build();
//        MongoClient mongoClient = MongoClients.create(settings);


//        MongoClient mongoClient = new MongoClient(new ServerAddress(), Arrays.asList(credential));

        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient;
    }

    @Bean(name = "mongoDatabase")
    public MongoDatabase mongoDatabase() {
        System.out.println("*******************" + mydb);
        MongoDatabase db = mongoClient().getDatabase(mydb);

        return mongoClient().getDatabase(mydb);
    }

    @Bean(name = "mongoCollection")
    public MongoCollection mongoCollection() {
        System.out.println("*******************" + mycoll);

        MongoDatabase mydb = mongoDatabase();
        MongoCollection coll = mydb.getCollection(mycoll);
        // TODO: 16/5/10  手动创建索引;设定数据的有效期
        //> db.location.ensureIndex( {position: "2d"} )


        return coll;
    }

    @Bean(name = "mongoCollectionTask")
    public MongoCollection mongoCollectionTask() {
        System.out.println("*******************" + mytasks);

        MongoDatabase mydb = mongoDatabase();
        MongoCollection coll = mydb.getCollection(mytasks);
        // TODO: 16/5/10  手动创建索引;设定数据的有效期
        //> db.location.ensureIndex( {position: "2d"} )


        return coll;
    }

}
