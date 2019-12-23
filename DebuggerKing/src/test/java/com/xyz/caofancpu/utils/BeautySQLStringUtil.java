package com.xyz.caofancpu.utils;

import com.xyz.caofancpu.util.commonoperateutils.FileUtil;
import com.xyz.caofancpu.util.streamoperateutils.CollectionUtil;
import com.xyz.caofancpu.util.studywaitingutils.VerbalExpressionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lanwen.verbalregex.VerbalExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * FileName: StringTemplateUtil
 */
public class BeautySQLStringUtil {

    /**
     * LOG
     */
    private static final Logger logger = LoggerFactory.getLogger(BeautySQLStringUtil.class);

    public static final String FILE_BASE_PATH = "/Users/htuser-085/Desktop/CAOFAN/IDEA-WORK/DebuggerBoot/DebuggerKingUtil/src/main/java/com/xyz/caofancpu/util/dataoperateutils/SQLString";

    public static void main(String[] args)
            throws Exception {
        VerbalExpression splitRegex = VerbalExpression.regex()
                .capt()
                .find("position")
                .space().zeroOrMore()
                .find("=")
                .space().zeroOrMore()
                .digit().zeroOrMore()
                .endCapt()
                .build();
        System.out.println(splitRegex.toString());
        String result = VerbalExpressionUtil.executePatternRex(splitRegex, "position=1, position =2, position =  300, position   = 400", StringUtils.EMPTY);
        System.out.println(result);
    }

    public static void handle()
            throws Exception {
        VerbalExpression splitRegex = VerbalExpression.regex()
                .capt().find("-").oneOrMore()
                .endCapt().then("-")
                .build();
        VerbalExpression mappingRegex = VerbalExpression.regex()
                .capt().digit().oneOrMore().then(StringUtils.SPACE).count(2).anything().endCapt().then("=").count(2).then(">").lineBreak()
                .build();

        String srcFilePath = FILE_BASE_PATH + "/sql.txt";
        String desFilePath = FILE_BASE_PATH + "/sql-beauty.txt";
        String sourceSql = FileUtil.readFileToString(srcFilePath);
        List<String> splitSQL = splitByRegex(sourceSql, splitRegex);

        List<String> formattedSQL = splitSQL.stream()
                .map(item -> formatSQL(item, mappingRegex, StringUtils.EMPTY).toUpperCase())
                .collect(Collectors.toList());
        FileUtil.writeStringToFile(CollectionUtil.join(formattedSQL, StringUtils.CR + StringUtils.LF), desFilePath);

        System.out.println(splitRegex.toString() + "\n" + mappingRegex);
    }

    public static List<String> splitByRegex(String srcContent, VerbalExpression splitRegex) {
        if (StringUtils.isEmpty(srcContent) || Objects.isNull(splitRegex)) {
            return new ArrayList<>();
        }
        return Arrays.asList(srcContent.split(splitRegex.toString()));
    }

    public static String formatSQL(String srcSQL, VerbalExpression mappingRegex, String replacer) {
        if (StringUtils.isEmpty(srcSQL) || Objects.isNull(mappingRegex)) {
            return StringUtils.EMPTY;
        }
        return VerbalExpressionUtil.executePatternRex(mappingRegex, srcSQL, replacer);
    }

}
