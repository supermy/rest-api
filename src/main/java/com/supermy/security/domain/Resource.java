package com.supermy.security.domain;

/**
 *
 */

import com.supermy.domain.BaseObj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//, catalog = "hibnatedb"

/**
 * 资源表,菜单的子节点, ? 或者模块名称
 */
@Entity
@Table(name = "my_resources")
public class Resource extends BaseObj{

    /**
     * res_login res_XXX res_welcome
     */
    @Column(length = 45,nullable = false)
    private String name;
    /**
     * 资源类型:菜单项-menu[静态页面:StaticPage],功能模块-operation,文件修改权限-file,页面元素可见-element,
     * Api-RestApi
     */
    @Column(length = 45,nullable = false)
    private String type;
    /**
     * 登陆页面 设置页面 欢迎页面
     */
    @Column(length = 200)
    private String remark;
    /**
     * 路径:/home.html /login.html /logout
     */
    @Column(length = 230,nullable = false)
    private String uri;
    /**
     * 两级子系统编码 1001 1002 1101 1102
     */
    @Column(length = 10,nullable = false)
    private String module;


    public Resource() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
