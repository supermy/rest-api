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

import com.supermy.service.WebMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Web hibernate 元数据信息 接口；
 *
 */
@RestController
@EnableAutoConfiguration
class WebMetaController {

    private static final String BASE_MAPPING = "/webmetas";

    @Autowired
    private WebMetaService webMetaService;

    /**
     * http://127.0.0.1:9006/form/rest/webmetas?domain=User
     * http://127.0.0.1:9006/form/rest/webmetas?domainName=com.supermy.security.domain.User
     *
     *
     * @param domainName
     * @return
     * @throws ResourceNotFoundException
     * @throws HttpRequestMethodNotSupportedException
     */
    @ResponseBody
    @RequestMapping(value = BASE_MAPPING+"", method = RequestMethod.GET)
    public Map getHibernateMetaJson(String domainName)
            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
        System.out.println("********************domain name:"+domainName);
        return webMetaService.getHibernateMetaJson(domainName);
    }



}
