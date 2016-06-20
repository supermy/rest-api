/*
 * Copyright 2013-2014 the original author or authors.
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
package com.supermy.geo.web;

import com.supermy.geo.mongo.Location;
import com.supermy.geo.service.WebGeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Geo 查询 接口；
 *
 */
@RestController
class WebGeoController {

    private static final String BASE_MAPPING = "/webgeo";

    @Autowired
    private WebGeoService wgs;



    /**
     *
     * 乘客提交约车请求,登记任务,返回流水号.
     *
     * http://127.0.0.1:9006/form/rest/webgeo/circle?phone=15510325588&name=莫勇&x1=0&y1=0&bj=0.6
     *
     *
     * @param phone
     * @param name
     * @param x1
     * @param y1
     * @param bj
     * @return
     * @throws ResourceNotFoundException
     * @throws HttpRequestMethodNotSupportedException
     */
    @ResponseBody
    @RequestMapping(value = BASE_MAPPING+"/circle", method = RequestMethod.GET)
    public  String findByPositionWithin(String phone,String name ,Double x1,Double y1,Double bj)
            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {

        //System.out.println(String.format("********************:%f %f %f %f",x1,y1,bj));
        System.out.println(String.format("********************:"+x1+"-"+y1+"-"+bj));

        //String lsh = phone+"::"+new Date().getTime();
        String lsh= wgs.findByPositionWithin(phone, name, new Circle(x1, y1, bj));

        return lsh;
    }

    /**
     * 根据流水号查询匹配结果
     *
     * http://127.0.0.1:9006/form/rest/webgeo/result?lsh=15510325588::1463988889205
     *
     * @param lsh
     * @return
     * @throws ResourceNotFoundException
     * @throws HttpRequestMethodNotSupportedException
     */
    @ResponseBody
    @RequestMapping(value = BASE_MAPPING+"/result", method = RequestMethod.GET)
    public  String getDriverResult(String lsh)
            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {

        //System.out.println(String.format("********************:%f %f %f %f",x1,y1,bj));

        String driver=wgs.getDriverResult(lsh);

        return driver;
    }


    /**
     * http://127.0.0.1:9006/form/rest/webgeo/near?x1=0&y1=0&bj=0.6
     *
     * @param x1
     * @param y1
     * @param bj
     * @return
     * @throws ResourceNotFoundException
     * @throws HttpRequestMethodNotSupportedException
     */
    @ResponseBody
    @RequestMapping(value = BASE_MAPPING+"/near", method = RequestMethod.GET)
    public  List<Location> findByPositionNear(Double x1,Double y1,Double bj)
            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {

        //System.out.println(String.format("********************:%s %s %s %s",x1,y1,bj));
        Point point = new Point(x1, y1 );

        List<Location> locations = wgs.findByPositionNear(point , new Distance(bj, Metrics.KILOMETERS) );
//        List<Location> locations = repo.findByPositionNear(point , new Distance(bj) );


        return locations;
    }




}
