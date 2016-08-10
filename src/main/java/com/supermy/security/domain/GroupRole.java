package com.supermy.security.domain;

/**
 * Created by moyong on 15/1/9.
 */

import com.supermy.domain.BaseObj;

import javax.persistence.*;

/**
 * 用户太多,逐个授权麻烦;直接将权限给某个小组;
 */
@Entity
@Table(name = "my_group_roles",  uniqueConstraints = @UniqueConstraint( columnNames = { "role", "group_id" }))
public class GroupRole extends BaseObj{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group  group;

    @Column(name = "role", nullable = false, length = 45)
    private String role;

    public GroupRole() {
    }

    public GroupRole(Group group, String role) {
        this.group = group;
        this.role = role;
    }



}
