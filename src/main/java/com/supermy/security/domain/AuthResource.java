package com.supermy.security.domain;



import com.supermy.domain.BaseObj;

import javax.persistence.*;

/**
 * 权限资源表
 *
 */
@Entity
@Table(name = "my_auth_resources",  uniqueConstraints = @UniqueConstraint( columnNames = { "auth_id", "res" }))
public class AuthResource extends BaseObj{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id", nullable = false)
    private Authorities auth;

    @Column(name = "res", nullable = false, length = 45)
    private String res;

    public AuthResource() {
    }

    public AuthResource(Authorities auth, String res) {
        this.auth = auth;
        this.res = res;
    }

    public Authorities getAuth() {
        return auth;
    }

    public void setAuth(Authorities auth) {
        this.auth = auth;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }
}
