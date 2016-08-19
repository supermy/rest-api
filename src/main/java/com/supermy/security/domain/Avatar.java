package com.supermy.security.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.supermy.domain.BaseObj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户头像
 *
 */
@Entity
@Table(name = "my_avatar")
public class Avatar extends BaseObj{


    @Column(length = 100)
    private String filename;

    @Column(length = 240)
    private String webpath;

    @Column
    private Long filesize;

    @Column(length = 240)
    private String syspath;

    public Avatar() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getWebpath() {
        return webpath;
    }

    public void setWebpath(String webpath) {
        this.webpath = webpath;
    }


    public String getSyspath() {
        return syspath;
    }

    public void setSyspath(String syspath) {
        this.syspath = syspath;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }
}
