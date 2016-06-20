package com.supermy.security.domain;

/**
 *
 */

import com.supermy.domain.BaseObj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 用户组或者部门
 */
@Entity
@Table(name = "my_groups")
public class Group extends BaseObj{

    @Column(name="group_",unique = true,
            nullable = false, length = 45)
    private String group;

    @Column( nullable = false, length = 45)
    private String name;

    /**
     * 小组长
     */
    @Column(length = 45)
    private String principal;

    @Column(length = 220)
    private String remark;


    public Group() {
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
