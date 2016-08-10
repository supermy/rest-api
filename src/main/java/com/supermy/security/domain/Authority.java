package com.supermy.security.domain;

/**
 *
 */

import com.supermy.domain.BaseObj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 权限表
 */
@Entity
@Table(name = "my_auths")
public class Authority extends BaseObj{

    /**
     * auth_login auth_XXX auth_welcome
     */
    @Column(unique = true,
            nullable = false, length = 45)
    private String name;

    /**
     * 登陆页面 设置页面 欢迎页面
     */
    @Column(length = 220)
    private String remark;

    /**
     * 两级子系统编码 1001 1002 1101 1102
     */
    @Column(nullable = false, length = 10)
    private String module;


    public Authority() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
