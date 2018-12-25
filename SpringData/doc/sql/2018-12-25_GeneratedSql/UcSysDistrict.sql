-- auto Generated on 2018-12-25 20:26:01 
-- DROP TABLE IF EXISTS `uc_sys_district`; 
CREATE TABLE uc_sys_district(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '区域名称',
    `pid` INTEGER(12) NOT NULL DEFAULT 0 COMMENT '区域PID',
    `initial` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '区域名称全拼首字母',
    `initials` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '区域首字名称拼音的首字母',
    `pinyin` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '区域名称全拼',
    `code` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '区域编码',
    `area_code` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '(弃用)区域名称全拼',
    `sort` INTEGER(12) NOT NULL DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'uc_sys_district';
