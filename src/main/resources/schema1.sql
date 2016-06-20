CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(60) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

  CREATE TABLE channel_auth
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,

    `createBy` varchar(20) DEFAULT NULL,
    `createDate` date DEFAULT NULL,
    `deleted` bit(1) DEFAULT NULL,
    `updateBy` varchar(20) DEFAULT NULL,
    `updateDate` datetime DEFAULT NULL,

  name VARCHAR(250) NOT NULL comment '渠道名称',
  code VARCHAR(250) NOT NULL comment '认证-渠道编码',
  pwd VARCHAR(10) NOT NULL comment 'secretkey 渠道秘钥，也是盐值',

  token VARCHAR(32) NOT NULL comment '废弃，认证-渠道令牌生成机制;md5(code+ip+pwd)',
  token_expire INT NOT NULL comment '渠道有效时间,默认90天',

  iplist VARCHAR(250) NOT NULL comment '渠道服务器ip 地址列表',

  ip_bind_time INT NOT NULL comment '封禁IP时间,默认300秒',
  ip_time_out INT NOT NULL comment '指定ip访问频率时间段,默认60秒',
  connect_count INT NOT NULL comment '指定ip访问频率计数最大值,默认100次/分钟',

  limit_bandwidth INT NOT NULL comment '渠道分配带宽',

  status  INT DEFAULT 0 NOT NULL COMMENT '是否生效,0/1  未生效/生效'

) comment '渠道认证表';

