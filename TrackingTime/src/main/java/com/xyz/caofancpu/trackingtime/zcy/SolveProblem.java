package com.xyz.caofancpu.trackingtime.zcy;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.trackingtime.zcy.datamodel.TransactionRecord;
import com.xyz.caofancpu.trackingtime.zcy.datamodel.enums.CoinTypeEnum;
import com.xyz.caofancpu.trackingtime.zcy.datamodel.enums.ExchangePlaceEnum;
import com.xyz.caofancpu.trackingtime.zcy.datamodel.enums.OperateTypeEnum;
import com.xyz.caofancpu.trackingtime.zcy.datamodel.enums.OperatorIdEnum;
import com.xyz.caofancpu.util.commonoperateutils.NormalUseUtil;
import com.xyz.caofancpu.util.dataoperateutils.DateUtil;
import com.xyz.caofancpu.util.dataoperateutils.NumberUtil;
import com.xyz.caofancpu.util.streamoperateutils.CollectionUtil;

import java.util.List;

/**
 *
 */
public class SolveProblem {

    public static void main(String[] args) {
        // 1.创建记录
        TransactionRecord first = new TransactionRecord()
                .setId(1)
                .setOperatorId(OperatorIdEnum.CF)
                .setCreateTime(DateUtil.parseStandardDateTime("2019-08-03 00:00:00"))
                .setExchangePlace(ExchangePlaceEnum.MIXIN_EXIN)
                .setCoinType(CoinTypeEnum.BOX)
                .setPrice(NumberUtil.convertVirtualCoinPrice("12.86"))
                .setNum(NumberUtil.convertVirtualCoinPrice("15.529713"))
                .setTransactionAmount(NumberUtil.convertVirtualCoinPrice("200"))
                .setCoinTypeMarketAmount(NumberUtil.convertVirtualCoinPrice("200"))
                .setOperateType(OperateTypeEnum.IN);

        TransactionRecord second = new TransactionRecord()
                .setId(2)
                .setOperatorId(OperatorIdEnum.CF)
                .setCreateTime(DateUtil.parseStandardDateTime("2019-08-10 00:00:00"))
                .setExchangePlace(ExchangePlaceEnum.MIXIN_EXIN)
                .setCoinType(CoinTypeEnum.BOX)
                .setPrice(NumberUtil.convertVirtualCoinPrice("13.76"))
                .setNum(NumberUtil.convertVirtualCoinPrice("189.6822699"))
                .setTransactionAmount(NumberUtil.convertVirtualCoinPrice("2609.5"))
                .setCoinTypeMarketAmount(NumberUtil.convertVirtualCoinPrice("2851.49"))
                .setOperateType(OperateTypeEnum.IN);

        TransactionRecord last = new TransactionRecord(
                3,
                OperatorIdEnum.ZCY,
                DateUtil.parseStandardDateTime("2019-08-17 00:00:00"),
                ExchangePlaceEnum.MIXIN_EXIN,
                CoinTypeEnum.BOX,
                NumberUtil.convertVirtualCoinPrice("12.41"),
                NumberUtil.convertVirtualCoinPrice("16.10173"),
                NumberUtil.convertVirtualCoinPrice("200"),
                NumberUtil.convertVirtualCoinPrice("2981.17"),
                OperateTypeEnum.IN
        );

        // 2.收集记录
        List<TransactionRecord> recordList = Lists.newArrayList(first, second, last);

        NormalUseUtil.out("\n\n");
        // 3.输出打印记录
        recordList.forEach(record -> NormalUseUtil.out(record.toString()));

        List<String> recordContentList = CollectionUtil.transToList(recordList, Object::toString);
        recordContentList.forEach(NormalUseUtil::out);

        CollectionUtil.transToList(recordList, Object::toString)
                .forEach(NormalUseUtil::out);
    }

}
