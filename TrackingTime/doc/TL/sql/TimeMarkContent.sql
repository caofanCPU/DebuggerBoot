-- auto Generated on 2019-08-14 17:22:10 
-- DROP TABLE IF EXISTS `time_mark_content`; 
CREATE TABLE time_mark_content(
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `time_block_id` BIGINT NOT NULL DEFAULT -1 COMMENT '时间区块ID',
    `title` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '标题',
    `author_id` BIGINT NOT NULL DEFAULT -1 COMMENT '创作者ID',
    `author_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '创作者笔名',
    `keyword` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '摘要关键字, 使用","分隔',
    `sign_code_url` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '签名二维码',
    `status` INTEGER(12) NOT NULL DEFAULT -1 COMMENT '状态：0-创作;1-发布;-1-删除;9-上链',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'time_mark_content';
