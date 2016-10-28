package com.supermy.logviewer.domain;

/**
 * Created by moyong on 16/10/28.
 */
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FileNode {

    private String text;
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    private boolean leaf;
    public boolean isLeaf() { return leaf; }
    public void setLeaf(boolean leaf) { this.leaf = leaf; }

    private String fileName;
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    private Object fileSize;
    public Object getFileSize() { return fileSize; }
    public void setFileSize(Object fileSize) { this.fileSize = fileSize; }

    private Date fileDate;
    public Date getFileDate() { return fileDate; }
    public void setFileDate(Date fileDate) { this.fileDate = fileDate; }

    private String filePath;
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    private List<FileNode> children;
    public List<FileNode> getChildren() { return children; }
    public void setChildren(List<FileNode> children) { this.children = children; }

    @Override
    public String toString() {
        return "FileNode [leaf=" + leaf + ", fileName=" + fileName
                + ", fileSize=" + fileSize + ", filePath=" + filePath + "]";
    }
}