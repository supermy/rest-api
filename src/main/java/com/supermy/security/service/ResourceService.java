package com.supermy.security.service;

/**
 * Created by moyong on 16/10/28.
 */

import com.supermy.security.domain.Resource;
import com.supermy.security.repository.ResourceRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {

    protected final Logger log = LogManager.getLogger(getClass());

    @javax.annotation.Resource
    private Environment env;
    @Autowired
    private ResourceRepository resourceRepository;





    /**
     *
     * @param rootNodes
     * @param treeMenus
     */
    protected void listFilesInDirectory( List<Resource> rootNodes, List<Resource> treeMenus) {
        if (rootNodes != null) {
            for (Resource f : rootNodes) {
                List<Resource> children = resourceRepository.findAllByPidOrderByModuleAsc(f.getModule());
                if (children.size()>=1) {

                    List<Resource> dirTree = new ArrayList<Resource>();
                    f.setLeaf(false);
                    treeMenus.add(f);
                    listFilesInDirectory(children, dirTree);

                    f.setChildren(children);
                } else {
                    f.setLeaf(true);
                    treeMenus.add(f);
                }
            }
        }
    }



    public List<Resource> getMenus(String rootId) {
        List<Resource> resources = resourceRepository.findAllByPidOrderByModuleAsc(rootId);
        List<Resource> treeMenus = new ArrayList<Resource>();

        listFilesInDirectory(resources, treeMenus);
        return treeMenus;
    }
}
