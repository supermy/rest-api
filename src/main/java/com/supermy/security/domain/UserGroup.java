package com.supermy.security.domain;

/**
 * Created by moyong on 15/1/9.
 */

import com.supermy.domain.BaseObj;

import javax.persistence.*;

/**
 * 用户角色表
 * 只保留角色名称,不与角色表做关联;
 * 角色表当做码表使用;
 */
@Entity
@Table(name = "my_user_groups",  uniqueConstraints = @UniqueConstraint( columnNames = { "group_", "user_id" }))
public class UserGroup extends BaseObj{

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "user_group_id",
//            unique = true, nullable = false)
//    private Integer userGroupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "group_", nullable = false, length = 45)
    private String group;

    public UserGroup() {
    }

    public UserGroup(User user, String group) {
        this.user = user;
        this.group = group;
    }

//    public Integer getUserGroupId() {
//        return userGroupId;
//    }
//
//    public void setUserGroupId(Integer userGroupId) {
//        this.userGroupId = userGroupId;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
