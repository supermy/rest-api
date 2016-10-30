package com.supermy.logviewer.service;

/**
 * Created by moyong on 16/10/28.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


import com.supermy.logviewer.domain.FileNode;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class LogViewerService {

    protected final Logger log = LogManager.getLogger(getClass());
    private String logDir;//="/Users/moyong/docker-share/logs";
    private String logDirPathName;
    @Resource
    private Environment env;
    //@Autowired private MyProperties myProperties;

    /**
     * If the log directory changes, restart app in order to get the new property
     * into this service.
     */
    @PostConstruct  //通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    public void init() {

        System.out.println(env.getRequiredProperty("logviewer.path"));

        logDir = env.getRequiredProperty("logviewer.path");
//        logDir = myProperties.getProperty(PropertyConstants.TOMCAT_LOG_DIR);
    }

    /**
     * Get a list of all the files, including files in subdirectories.
     * @return List of all files found in the log directory, including subdirectories
     */
    public List<FileNode> getFiles()  {
        List<FileNode> fileList = new ArrayList<FileNode>();
        File dir = new File(logDir);
        logDirPathName = dir.getPath();
        listFilesInDirectory(dir, fileList);
        return fileList;
    }

    /**
     * This builds up the list of the files, including in subdirectories.  This
     * is recursive for each subdirectory found
     * @param dir The directory currently finding files in
     * @param currentDir The list to add all files found in the current directory
     */
    protected void listFilesInDirectory(File dir, List<FileNode> currentDir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    List<FileNode> dirTree = new ArrayList<FileNode>();
                    FileNode node = addDirNode(f);
                    currentDir.add(node);
                    listFilesInDirectory(f, dirTree);
                    node.setChildren(dirTree);
                } else {
                    FileNode node = addFileNode(f);
                    currentDir.add(node);
                }
            }
        }
    }

    /**
     * Create a file node.  File nodes indicate they are a leaf
     * and include the file path.
     * @param diskFile The file on disk
     * @return FileNode, populated as a file node
     */
    protected FileNode addFileNode(File diskFile) {
        FileNode node = new FileNode();
        String diskFileName = diskFile.getName();
        node.setFileName(diskFileName);
        node.setFileDate(new Date(diskFile.lastModified()));
        node.setLeaf(true);
        node.setFileSize(new Long(diskFile.length()));

        String parentName = diskFile.getPath();
        String filePath = parentName.substring(logDirPathName.length() + 1);
        node.setFilePath(filePath);

        return node;
    }

    /**
     * Create a directory node.  Directory nodes do not have a file path and
     * are <i>not</i> leafs.  Their file size is set to "dir"
     * @param diskFile
     * @return FileNode, populated as a directory node
     */
    protected FileNode addDirNode(File diskFile) {
        FileNode node = new FileNode();
        String diskFileName = diskFile.getName();
        node.setFileName(diskFileName);
        node.setFileDate(new Date(diskFile.lastModified()));
        node.setFileSize("<dir>");
        return node;
    }


    /**
     * Get the file from the log directory.
     * @param fileName FileName includes path information if file is not in the root
     * log directory.
     * @return file found
     */
    public File getFile(String fileName) {
        File file = new File(logDir + "\\" + fileName);
        return file;
    }

}
