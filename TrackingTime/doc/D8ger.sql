
-- ----------------------------
-- D8ger-Sql-Auto-Generated
-- Table structure for `d8ger`
-- @author 帝八哥
-- ----------------------------
-- DROP TABLE IF EXISTS `d8ger`;
CREATE TABLE `d8ger`
(
    id bigint(20) unsigned auto_increment comment 'ID' primary key,
    name varchar(32) default '' null comment '姓名',
    age int(11) default 0 null comment '年龄',
    job varchar(32) default '' null comment '工作',
    active_status int(4) default 0 null comment '激活状态',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment 'D8ger表' charset = utf8mb4;
