drop table if exists `user`;
create table `user` (
    `id` BIGINT(20) not null auto_increment,
    `username` VARCHAR(64) default null,
    `password` varchar(64) DEFAULT null,
    `email` varchar(100) default null COMMENT '邮箱',
    `note` varchar(500) DEFAULT NULL COMMENT '备注信息',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
    `status` int(1) DEFAULT '1' COMMENT '账号启用状态：0-禁用；1-启用',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB auto_increment=5 DEFAULT CHARSET=utf8 comment='用户表';