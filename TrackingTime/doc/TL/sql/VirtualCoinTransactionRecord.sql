-- auto Generated on 2019-11-24 20:00:22 
-- DROP TABLE IF EXISTS `virtual_coin_transaction_record`; 
CREATE TABLE virtual_coin_transaction_record
(
    `id`                      INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `operator_id`             INTEGER(20)          NOT NULL COMMENT '用户ID',
    `exchange_place`          INTEGER(4)           NOT NULL COMMENT '交易所',
    `coin_type`               INTEGER(4)           NOT NULL COMMENT '币种',
    `price`                   DECIMAL(14, 8)       NOT NULL COMMENT '单价',
    `num`                     DECIMAL(14, 8)       NULL     DEFAULT 0 COMMENT '数量',
    `transaction_amount`      DECIMAL(14, 8)       NOT NULL COMMENT '交易价格',
    `coin_type_market_amount` DECIMAL(14, 8)       NULL     DEFAULT 0 COMMENT '该币种当前市值总额',
    `operate_type`            INTEGER(4)           NOT NULL COMMENT '操作类型',
    `create_time`             DATETIME             NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT 'virtual_coin_transaction_record';
