package com.xyz.caofancpu.trackingtime.model;

import com.xyz.caofancpu.trackingtime.zcy.datamodel.enums.CoinTypeEnum;
import com.xyz.caofancpu.trackingtime.zcy.datamodel.enums.ExchangePlaceEnum;
import com.xyz.caofancpu.trackingtime.zcy.datamodel.enums.OperateTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 虚拟货币交易记录
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class VirtualCoinTransactionRecord {

    @ApiModelProperty(value = "主键ID", example = "1", position = 1)
    private Integer id;

    @ApiModelProperty(value = "用户ID", example = "-2147483648", position = 2)
    private Integer operatorId;

    @ApiModelProperty(value = "创建时间", example = "2019-11-23 00:00:00", position = 3)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "交易所", example = "1", position = 4)
    private ExchangePlaceEnum exchangePlace;

    @ApiModelProperty(value = "币种", example = "1", position = 5)
    private CoinTypeEnum coinType;

    @ApiModelProperty(value = "价格", example = "79999.99", position = 6)
    private BigDecimal price;

    @ApiModelProperty(value = "个数", example = "0.99", position = 7)
    private BigDecimal num;

    @ApiModelProperty(value = "交易金额", example = "79.99", position = 8)
    private BigDecimal transactionAmount;

    @ApiModelProperty(value = "当前累计市值", example = "79.99", position = 9)
    private BigDecimal coinTypeMarketAmount;

    @ApiModelProperty(value = "操作", example = "1", position = 10)
    private OperateTypeEnum operateType;

    @ApiModelProperty(value = "批量操作", example = "[1, 2]", position = 11)
    private List<OperateTypeEnum> operateTypeList;

}
