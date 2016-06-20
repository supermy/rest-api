/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.supermy.geo.service;


import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.FindIterable;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.supermy.geo.SimpleChatClient;
import com.supermy.geo.mongo.Location;
import com.supermy.geo.mongo.LocationRepository;
import com.supermy.geo.mongo.MongoRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 复杂逻辑处理
 */
// Spring Bean的标识.
@Component
@Transactional
public class WebGeoService {


    @Autowired
    @Qualifier("chatclient")
    private SimpleChatClient imclient;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    @Qualifier("bipool")
    private ExecutorService executorService;

//    @Autowired
//    @Qualifier("mongoClient")
//    private MongoClient mongoClient;

    @Autowired
    @Qualifier("mongoCollection")
    private MongoCollection mongoCollection;

    @Autowired
    @Qualifier("mongoCollectionTask")
    private MongoCollection mongoCollectionTask;

    @Autowired
    @Qualifier("mymongo")
    private MongoRepository mr;

    @Autowired
    StringRedisTemplate  redisClient;

    /**
     * 根据流水号获取匹配结果
     *
     * @param lsh
     * @return
     */
    public String getDriverResult(String lsh) {
        Bson filter = Filters.eq("_id", lsh);
        FindIterable findIterable = mongoCollectionTask.find(filter);

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        final List<Document> list = new ArrayList<Document>();

        findIterable.into(list, new SingleResultCallback<Void>() {
            @Override
            public void onResult(final Void result, final Throwable t) {
                System.out.println("执行完成");
                countDownLatch.countDown();

            }
        });


//        findIterable.forEach( new Block< Document>() {
//            @Override
//            public void apply( final Document document) {
//                System.out.println(document.toJson());
//                list. add(document);
//            }
//        },new SingleResultCallback<Void>() {
//            @Override
//            public void onResult(final Void result, final Throwable t) {
//                System.out.println("执行完成");
//                countDownLatch.countDown();
//            }
//        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        for (Document doc : list
                ) {
            sb.append(doc.toJson());
        }
                System.out.println(sb);

        return sb.toString();
    }

    /**
     * 接收乘客约车请求,登记任务,返回流水号
     * 乘客也是用tcp 链接,能够及时收到消息;用户体验好;
     *
     * 流水号:phone+time
     *
     * @param phone
     * @param name
     * @param circle @return
     */
    public String findByPositionWithin(final String phone, final String name, final Circle circle) {
        //每一步日志操作都保存单独实现

        final List<Location> drivers = locationRepository.findByPositionWithin(circle);
        if (drivers == null || drivers.isEmpty()) {
            System.out.println("附件没有出租车......!");
           return "{附近没有出租车}";

        } else {
            //通知预约正在处理
            //1.生成流水号 ;安卓端保存流水号
            Date date = new Date();
            final String lsh = phone + "::" + date.getTime();

            imclient.send("{预约正在处理,流水号:lsh}");

            //生成预约单存入数据库;
            //2.异步保存任务清单 登记任务 to mongodb or codis  // TODO: 16/5/20 有效期
            final String json = String.format(mr.mongoTasks, lsh, name, phone, date.getTime(), circle.getCenter().getX(), circle.getCenter().getY(),circle.getRadius());
            System.out.println("Insert:" + json);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //登记任务
                    Document parse = Document.parse(json);
                    //System.out.println("Inserted!" + parse);
                    mongoCollectionTask.insertOne(parse, new SingleResultCallback<Void>() {
                        @Override
                        public void onResult(final Void result, final Throwable t) {
                            System.out.println("Inserted!");
                        }
                    });

                }
            });
            //3.生成红包,保存预约单到redis
            redisClient.opsForValue().set(lsh,json);
            //保存预约单对应的司机到 redis , 过滤,不允许范围外的司机抢单;
            for (Location loc:drivers) {
                redisClient.opsForSet().add(lsh.getBytes()+"-driver",loc.getId());
            }

            //4.通知司机抢单
            imclient.send("{流水号:lsh,订单信息,drivers}");

        }

      /*  executorService.execute(new Runnable() {
            @Override
            public void run() {


                if (drivers == null || drivers.isEmpty()) {
                    System.out.println("附件没有出租车......!");
                } else {

                    //3.2.1 结果更新到任务清单


                    //3.2.2 结果更新到任务清单  生成红包



                    //3.2.3 结果消息派发到司机
                    //发送订单信息号,从数据库获取订单信息; // FIXME: 16/5/24 多线程锁计数,直接发送此信息,解决问题...
                    /*//* 乘客的约车信息{司机电话|乘客姓名|乘客电话|乘客位置[经度|纬度]}: [p::18610586586;张三;18610586586;1462870826752;106.72;26.57]

                    StringBuffer sb = new StringBuffer("p::");
                    sb.append(loc.getId()).append(";");
                    sb.append(name).append(";");
                    sb.append(phone).append(";");
                    sb.append(new Date().getTime()).append(";");
                    sb.append(loc.getPosition()[0]).append(";");
                    sb.append(loc.getPosition()[1]);

                    System.out.println(sb);

                    imclient.send(sb.toString());


//
//                    Date date = new Date();
//
//                    Bson filter = Filters.eq("_id", lsh);
//
//                    final String json = String.format(mr.mongoTasks1, loc.getId(), loc.getId(), date.getTime(), loc.getPosition()[0], loc.getPosition()[1]);
//                    Document jsondata = Document.parse(json);
//                    Document data = new Document("$set", jsondata);
//
//                    mongoCollectionTask.findOneAndUpdate(filter, data, new SingleResultCallback<Void>() {
//                        @Override
//                        public void onResult(final Void result, final Throwable t) {
//                            t.printStackTrace();
//                            System.out.println("更新完成!");
//                        }
//                    });


                }


            }
        });*/


        return "";
    }

    /**
     * @param point
     * @param distance
     * @return
     */
    public List<Location> findByPositionNear(Point point, Distance distance) {
        List<Location> drivers = locationRepository.findByPositionNear(point, distance);
        return drivers;
    }


    /**
     * 司机与乘客匹配规则
     *
     * @return
     */
    @Deprecated
    private Location matchrule(Circle circle) {
        // FIXME: 16/5/20  此驱动非异步
        List<Location> drivers = locationRepository.findByPositionWithin(circle);
        //随即匹配
        if (drivers.size() >= 1) {
            return drivers.get(0);
        } else {
            return null;
        }

    }


}
