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

import com.supermy.service.WebGitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Web 组件Response 接口；
 *
 */
@RestController
class WebGitController {

    private static final String BASE_MAPPING = "/webgits";

    @Autowired
    private WebGitService webGitService;

    /**
     * http://127.0.0.1:9006/form/rest/webgits/user/dept/style1?uId=1
     *
     * @param uId
     * @return
     * @throws ResourceNotFoundException
     * @throws HttpRequestMethodNotSupportedException
     */
    @ResponseBody
    @RequestMapping(value = BASE_MAPPING+"/user/dept/style1", method = RequestMethod.GET)
    public String getUserDeptCollectionResource(Long uId)
            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
        System.out.println("********************uId:"+uId);
        return webGitService.getUserDeptCollectionResource(uId);
    }

    /**
     * http://127.0.0.1:9006/form/rest/webgits/user/dept/style2?uId=1
     *
     * @param uId
     * @return
     * @throws ResourceNotFoundException
     * @throws HttpRequestMethodNotSupportedException
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = BASE_MAPPING+"/user/dept/style2", method = RequestMethod.GET)
    public String getUserDeptSelected(Long uId)
            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
        System.out.println("********************uId:"+uId);
        return webGitService.getUserDeptSelected(uId);
    }


}
