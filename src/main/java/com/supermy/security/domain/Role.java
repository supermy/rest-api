package com.supermy.security.domain;

/**
 *
 */

import com.supermy.domain.BaseObj;

import javax.persistence.*;



/**
 * 角色表
 * admin
 * user
 */
@Entity
@Table(name = "my_roles")
public class Role extends BaseObj{

    @Column(name = "role", unique = true,
            nullable = false, length = 45)
    private String role;

    @Column(length = 45)
    private String name;

    @Column(length = 200)
    private String remark;


    public Role() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
}
