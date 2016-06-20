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
package com.supermy.web;

import com.supermy.domain.Channel;
import com.supermy.repository.ChannelRepository;
import com.supermy.service.ChannelService;
import com.supermy.utils.MyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.*;
import org.springframework.hateoas.*;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 常用增删改查走Response 接口；
 * 复杂逻辑单独定义接口；
 *
 */
@RestController
class ChannelEntityController {

    private static final String BASE_MAPPING = "/filter";

    @Autowired
    private ChannelService channelService;

    @ResponseBody
    @RequestMapping(value = BASE_MAPPING+"/channel/spec", method = RequestMethod.GET)
    public Page<Channel> getCollectionResource(Pageable pageable, Sort sort, MyFilter<Channel> filter)
            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
        System.out.println("********************sort:"+sort);
        System.out.println("********************filter:"+filter);
        return channelService.getCollectionResource(pageable);
    }

}
