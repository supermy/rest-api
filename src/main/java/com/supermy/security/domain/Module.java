package com.supermy.security.domain;

/**
 *
 */

import com.supermy.domain.BaseObj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 模块列表
 * 1001/1002
 * 1101/1102
 */
@Entity
@Table(name = "my_modules")
public class Module extends BaseObj{

    @Column(name = "code", unique = true,
            nullable = false, length = 45)
    private String code;

    @Column(length = 45)
    private String name;

    @Column(length = 200)
    private String remark;


    public Module() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
