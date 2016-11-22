package com.supermy.security.domain;

/**
 *
 */

import com.supermy.base.domain.BaseObj;

import javax.persistence.*;
import java.util.List;

//, catalog = "hibnatedb"

/**
 * 资源表,菜单的子节点, ? 或者模块名称
 * tree resoure 就是 menu;
 * 菜单树可视化设计前端采用zTree
 *
 */
@Entity
@Table(name = "my_resources")
public class Resource extends BaseObj{

    /*
        资源树
     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pid", nullable = false)
//    public Resource parent;

    @Column(length = 10,nullable = false)
    private String pid;
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
    private String url;
    /**
     * 两级子系统编码 1001 1002 1101 1102 相当于code
     */
    @Column(length = 50,nullable = false,unique = true)
    private String module;

    @Transient
    private boolean leaf;

    @Transient
    private List<Resource> children;

//    public boolean getLeaf() {
//        return false;
//    }
//
//    public String getChildren() {
//        return "[{}]";
//    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
//    public Resource getParent() {
//        return parent;
//    }
//
//    public void setParent(Resource parent) {
//        this.parent = parent;
//    }


    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public List<Resource> getChildren() {
        return children;
    }

    public void setChildren(List<Resource> children) {
        this.children = children;
    }
}
