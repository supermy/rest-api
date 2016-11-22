package com.supermy.security.web;

/**
 * Created by moyong on 16/10/28.
 */

import com.supermy.security.domain.Resource;
import com.supermy.security.service.ResourceService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    protected final Logger log = LogManager.getLogger(getClass());
    @Autowired
    private ResourceService menusService;

    /**
     * Build list of all files in the log directory
     *
     * @return JSON response with objects expected by a tree grid.
     */
    @RequestMapping(value = "/menus")
    public
    @ResponseBody
    Object getMenus(String  rootId ) {
        try {
            List<Resource> menus = menusService.getMenus(rootId);
            return menus;
        } catch (Exception e) {
            log.error("Error loading files ", e);
            // This just returns a Map that will result in JSON {"success":"false", "message":"Unexpected Error"}
//            Map<String, Object> errorMap = WebUtil.getModelMapMessage(false, ControllerConstants.DEFAULT_ERROR_MSG, null);
            Map<String, Object> errorMap = new HashMap();
            errorMap.put("error", e.getMessage());
            return errorMap;
        }
    }


}
