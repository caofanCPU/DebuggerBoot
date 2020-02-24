-- ----------------------------
-- D8ger-Sql-Auto-Generated
-- Table structure for `d8_smart_coding`
-- @author Power+
-- ----------------------------
-- DROP TABLE IF EXISTS `d8_smart_coding`;
CREATE TABLE `d8_smart_coding`
(
    id                  int(11) unsigned auto_increment comment 'ID' primary key,
    score               bigint(20)   default 0                 null comment '累计得分',
    nick_name           varchar(32)  default ''                null comment '昵称',
    deleted             tinyint(1)   default 0                 null comment '是否删除',
    register_date       datetime     default CURRENT_TIMESTAMP null comment '注册时间',
    pre_start_time      datetime     default CURRENT_TIMESTAMP null comment '试玩开始时间',
    exact_money         double(8, 2) default 0.00              null comment '金币',
    english_money       double(8, 2) default 0.00              null comment '英币',
    french_money        double(8, 2) default 0.00              null comment '法币',
    korean_money        int(4)       default 0                 null comment '韩币',
    encrypt_type        int(4)       default 0                 null comment '加密类型',
    mark_content_status int(4)       default 0                 null comment '状态',
    create_time         datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    update_time         datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment 'D8SmartCoding表' charset = utf8mb4;
