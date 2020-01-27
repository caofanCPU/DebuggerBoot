-- ----------------------------
-- D8ger-Sql-Auto-Generated
-- Table structure for `user_info`
-- @author Power+
-- ----------------------------
-- DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`
(
    id          bigint(20) unsigned auto_increment comment 'ID 用户唯一编码' primary key,
    name        varchar(32) default ''                null comment 'name 姓名',
    age         int(11)     default 0                 null comment 'age 年龄',
    job         varchar(32) default ''                null comment 'job 工作',
    create_time datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment 'UserInfo表' charset = utf8mb4;
