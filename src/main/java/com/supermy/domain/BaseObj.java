package com.supermy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 基础类，通用属性的管理
 * 逻辑删除，可以将deleted 置为true;
 * 查询可以排除deleted is true 的数据；
 *
 */
@MappedSuperclass
public class BaseObj implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	/**
	 * 逻辑删除
	 */
	private Boolean deleted=false;
	/**
	 * 记录启用
	 */
	private Boolean enabled=true;

	/**
	 * 解决日期查询问题
	 */
	//@Temporal(TemporalType.DATE)
	@JsonFormat(timezone = "GMT+8")
	private Date createDate=new Date();


	//@Comment("系统审计表")
	@Column(length = 20)
	private String createBy="system";

	/**
	 * 默认DateTime is ok
	 */
	@JsonFormat(timezone = "GMT+8")
	private Date updateDate=new Date();
	@Column(length = 20)
	private String updateBy="system";

	public long getPkId() {
		return id;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
