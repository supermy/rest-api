package com.supermy.domain;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 渠道
 *
 //
 //	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
 //	name VARCHAR(250) NOT NULL comment '渠道名称',
 //	code VARCHAR(250) NOT NULL comment '认证-渠道编码',
 //	pwd VARCHAR(10) NOT NULL comment 'secretkey 渠道秘钥，也是盐值',
 //
 //	token VARCHAR(32) NOT NULL comment '废弃，认证-渠道令牌生成机制;md5(code+ip+pwd)',
 //	token_expire INT NOT NULL comment '渠道有效时间,默认90天',
 //
 //	iplist VARCHAR(250) NOT NULL comment '渠道服务器ip 地址列表',
 //
 //	ip_bind_time INT NOT NULL comment '封禁IP时间,默认300秒',
 //	ip_time_out INT NOT NULL comment '指定ip访问频率时间段,默认60秒',
 //	connect_count INT NOT NULL comment '指定ip访问频率计数最大值,默认100次/分钟',
 //
 //	limit_bandwidth INT NOT NULL comment '渠道分配带宽',
 //
 //	status  INT DEFAULT 0 NOT NULL COMMENT '是否生效,0/1  未生效/生效'
 *
 */
@Entity
@Table(name = "channel_auth")
public class Channel extends BaseObj{


	@Column(length = 200)
	private String name;
	@Column(length = 20)
	private String code;
	@Column(length = 32)
	private String pwd;
	@Column(length = 32)
	private String token;
	@Column(name = "token_expire")
	private Integer tokenExpire;
	private String iplist;
	@Column(name = "ip_bind_time")
	private Integer ipBindtime = 300;
	@Column(name = "ip_time_out")
	private Integer ipTimeout = 60;
	@Column(name = "connect_count")
	private Integer connectCount = 100;
	@Column(name = "limit_bandwidth")
	private String limitBandwidth = "100M";

	private Boolean status = false;

	public Channel() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getTokenExpire() {
		return tokenExpire;
	}

	public void setTokenExpire(Integer tokenExpire) {
		this.tokenExpire = tokenExpire;
	}

	public String getIplist() {
		return iplist;
	}

	public void setIplist(String iplist) {
		this.iplist = iplist;
	}

	public Integer getIpBindtime() {
		return ipBindtime;
	}

	public void setIpBindtime(Integer ipBindtime) {
		this.ipBindtime = ipBindtime;
	}

	public Integer getIpTimeout() {
		return ipTimeout;
	}

	public void setIpTimeout(Integer ipTimeout) {
		this.ipTimeout = ipTimeout;
	}

	public Integer getConnectCount() {
		return connectCount;
	}

	public void setConnectCount(Integer connectCount) {
		this.connectCount = connectCount;
	}

	public String getLimitBandwidth() {
		return limitBandwidth;
	}

	public void setLimitBandwidth(String limitBandwidth) {
		this.limitBandwidth = limitBandwidth;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
