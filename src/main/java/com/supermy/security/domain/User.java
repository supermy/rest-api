package com.supermy.security.domain;

/**
 * Created by moyong on 15/1/9.
 */

import com.supermy.base.domain.BaseObj;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

//, catalog = "hibnatedb"

/**
 * 用户权限=用户角色权限+用户所在组角色权限
 */
@Entity
@Table(name = "my_users")
public class User extends BaseObj{



    @RestResource(exported = false)
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
    //@JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "avatar_id",foreignKey = @ForeignKey(name="null"))
    private Avatar  avatar;

    /**
     * 登陆名称
     */
    //@Id
    @Column(name = "username", unique = true,
            nullable = false, length = 45)
    private String username;

    @Column(name = "password",
            nullable = false, length = 60)
    private String password;

//    @Column(name = "enabled", nullable = false)
//    private boolean enabled;

    //@RestResource(exported = false)
//    @JsonManagedReference
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pkId")
//    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<UserRole> userRole = new HashSet<UserRole>(0);

//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pkId")
//    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserGroup> userGroup = new HashSet<UserGroup>(0);

    public User() {
    }

    /**
     * 中文名称
     */
    private String namecn;
    private String tel;
    private String email;
    private String qq;
    private String weixin;



    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        //this.enabled = enabled;
        super.setEnabled(enabled);
    }

    public User(String username, String password,
                boolean enabled, Set<UserRole> userRole) {
        this.username = username;
        this.password = password;
//        this.enabled = enabled;
        super.setEnabled(enabled);

        this.userRole = userRole;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//
//    public boolean isEnabled() {
//        return this.enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }


    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Set<UserRole> getUserRole() {
        return this.userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    public String getNamecn() {
        return namecn;
    }

    public void setNamecn(String namecn) {
        this.namecn = namecn;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Set<UserGroup> getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(Set<UserGroup> userGroup) {
        this.userGroup = userGroup;
    }
}
