DROP TABLE IF EXISTS `wx_group_chat`;
CREATE TABLE wx_group_chat
(
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `msg_type`    INT(4)              NULL     DEFAULT 0 COMMENT '消息类型',
    `content`     VARCHAR(1024)       NULL     DEFAULT '' COMMENT '消息类容',
    `sender_id`   VARCHAR(128)        NOT NULL COMMENT '发送人ID',
    `sender_name` VARCHAR(64)         NOT NULL COMMENT '发送人微信昵称',
    `group_id`    VARCHAR(128)        NOT NULL COMMENT '群ID',
    `group_name`  VARCHAR(64)         NOT NULL COMMENT '群名称',
    `time_stamp`  BIGINT(20)          NOT NULL DEFAULT 1234567890 COMMENT '时间戳',
    `create_time` DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_sender_id (`sender_id`),
    INDEX idx_group_id (`group_id`),
    INDEX idx_time_stamp (`time_stamp`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '微信群聊消息表';


