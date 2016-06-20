package com.supermy.security.domain;



import com.supermy.domain.BaseObj;

import javax.persistence.*;

/**
 * 角色权限表
 *
 */
@Entity
@Table(name = "my_role_auths",  uniqueConstraints = @UniqueConstraint( columnNames = { "role_id", "auth" }))
public class RoleAuth extends BaseObj{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    /**
     * 角色可建立关联
     */
    @Column(name = "auth", nullable = false, length = 45)
    private String auth;

    public RoleAuth() {
    }

    public RoleAuth(Role role, String auth) {
        this.role = role;
        this.auth = auth;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
