package com.xyz.caofancpu.util.studywaitingutils;

import com.xyz.caofancpu.util.commonoperateutils.NormalUseUtil;
import com.xyz.caofancpu.util.commonoperateutils.SymbolConstantUtil;
import com.xyz.caofancpu.util.streamoperateutils.CollectionUtil;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import ru.lanwen.verbalregex.VerbalExpression;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName: VerbalExpressionUtil
 *
 * @author caofanCPU
 */

public class VerbalExpressionUtil {
    /**
     * Uppercase regular expression
     */
    public static final Pattern HUMP_TO_UNDERLINE = Pattern.compile("[A-Z]");

    /**
     * No '_' and begin with [A-Z] regex, it will trigger to execute CamelToUnderline when regex detect is true
     */
    public static final Pattern CAMEL_UNDERLINE_1_NO_UNDERLINE_CAPITALIZE = Pattern.compile("^(?!_)(?:[A-Z])[a-zA-Z0-9\\W]+$");

    /**
     * No '_' and begin with [a-z] regex, it will trigger to execute CamelToUnderline when regex detect is true
     */
    public static final Pattern CAMEL_UNDERLINE_2_NO_UNDERLINE_UNCAPITALIZE = Pattern.compile("^(?!_)(?:[a-z])[a-zA-Z0-9\\W]+$");

    /**
     * No upper case regex, it will trigger to execute LowerCaseToUpperCase when regex detect is true
     */
    public static final Pattern CAMEL_UNDERLINE_3_NO_UPPER_CASE = Pattern.compile("^(?![A-Z])[a-z0-9\\W_]+$");

    /**
     * No lower case, it will trigger to execute UpperCaseToCamel when regex detect is true
     */
    public static final Pattern CAMEL_UNDERLINE_4_NO_LOWER_CASE = Pattern.compile("^(?![a-z])[A-Z0-9\\W_]+$");

    /**
     * Swagger field position order regular match expression
     */
    public static final Pattern SWAGGER_POSITION_PATTERN = Pattern.compile("((?:position)(?:\\s)*(?:\\=)(?:\\s)*(?:\\d)*)");
    /**
     * Phone regex
     */
    public static Pattern PHONE_REGEX = Pattern.compile("^1[0-9]{10}$");
    /**
     * Email regex
     */
    public static Pattern EMAIL_REGEX = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    /**
     * Password validate regex, rule for C4_3 | C4_4
     */
    public static Pattern PWD_REGEX = Pattern.compile("^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,30}$");

    /**
     * Swagger field position order regular replacement
     *
     * @param originString
     * @param replaceString
     * @return
     */
    public static String regexHandlePositionProperty(String originString, final String replaceString) {
        Matcher matcher = SWAGGER_POSITION_PATTERN.matcher(originString);
        return matcher.replaceAll(replaceString);
    }

    /**
     * Create a regular expression object
     *
     * @param matchKeyWord
     * @return
     */
    public static VerbalExpression buildRegex(String matchKeyWord) {
        return VerbalExpression.regex().capt().find(matchKeyWord).endCapt().build();
    }

    /**
     * Camel convert to underline
     * For example:
     * CaoFAn --> cao_f_an --> CAO_F_AN --> CaoFAn
     *
     * @param originName
     * @return
     */
    public static String camelToUnderLineName(String originName) {
        return StringUtils.lowerCase(StringUtils.uncapitalize(originName).replaceAll(HUMP_TO_UNDERLINE.pattern(), "_$0"));
    }


    /**
     * CaoFAn -->(Uncapitalize) caoFAn -->(CamelToUnderline) cao_f_an -->(LowerCaseToUpperCase) CAO_F_AN -->(UpperCaseToCamel) CaoFAn
     *
     * @param originName
     * @return
     */
    public static String camelUnderLineNameConverter(@NonNull String originName) {
        int matchNo = 0;
        if (CAMEL_UNDERLINE_1_NO_UNDERLINE_CAPITALIZE.matcher(originName).matches()) {
            matchNo = 1;
        }
        if (CAMEL_UNDERLINE_2_NO_UNDERLINE_UNCAPITALIZE.matcher(originName).matches()) {
            matchNo = 2;
        }
        if (CAMEL_UNDERLINE_3_NO_UPPER_CASE.matcher(originName).matches()) {
            matchNo = 3;
        }
        if (CAMEL_UNDERLINE_4_NO_LOWER_CASE.matcher(originName).matches()) {
            matchNo = 4;
        }
        if (matchNo == 0) {
            return originName;
        }
        String result = originName;
        switch (matchNo) {
            case 1:
                // Uncapitalize
                result = StringUtils.uncapitalize(originName);
                break;
            case 2:
                // CamelToUnderline
                result = StringUtils.lowerCase(originName.replaceAll(HUMP_TO_UNDERLINE.pattern(), "_$0"));
                break;
            case 3:
                // LowerCaseToUpperCase
                result = StringUtils.upperCase(originName);
                break;
            case 4:
                // UpperCaseToCamel
                String[] words = originName.split("_");
                List<String> resultItemWordList = new ArrayList<>(words.length);
                for (String word : words) {
                    resultItemWordList.add(StringUtils.capitalize(StringUtils.lowerCase(word)));
                }
                result = CollectionUtil.join(resultItemWordList, SymbolConstantUtil.EMPTY);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * Convert path string to package,
     * for example: src/main/java/com/xyz/caofancpu/d8ger/test --> com.xyz.caofancpu.d8ger.test
     *
     * @param originPath
     * @return
     */
    public static String convertPathToPackage(String originPath) {
        VerbalExpression regex1 = VerbalExpression.regex()
                .capt()
                .startOfLine()
                .anything()
                .find(File.separator).zeroOrMore()
                .then("src").then(File.separator).zeroOrMore()
                .then("main").then(File.separator).zeroOrMore()
                .then("java")
                .endCapt()
                .build();
        String first = executePatternRex(regex1, originPath, SymbolConstantUtil.EMPTY);
        VerbalExpression regex2 = VerbalExpression.regex()
                .startOfLine()
                .then(File.separator).oneOrMore()
                .build();
        String second = executePatternRex(regex2, first, SymbolConstantUtil.EMPTY);
        return second.replaceAll(File.separator, SymbolConstantUtil.ENGLISH_FULL_STOP);
    }

    public static void main(String[] args)
            throws Exception {
//        VerbalExpression regex = VerbalExpression.regex()
//                .startOfLine()
//                .then("\\[A-Z\\]")
//                .build();
//        NormalUseUtil.out(regex.toString());
//        NormalUseUtil.out(convertPathToPackage("//src//mainjava//com/xyz/caofancpu/d8ger/test"));
        NormalUseUtil.out("cao_fan");
        NormalUseUtil.out(camelUnderLineNameConverter("cao_fan"));
        NormalUseUtil.out(camelUnderLineNameConverter(camelUnderLineNameConverter("cao_fan")));
        NormalUseUtil.out(camelUnderLineNameConverter(camelUnderLineNameConverter(camelUnderLineNameConverter("cao_fan"))));
        NormalUseUtil.out(camelUnderLineNameConverter(camelUnderLineNameConverter(camelUnderLineNameConverter(camelUnderLineNameConverter("cao_fan")))));
    }

    public static String executePatternRex(VerbalExpression regexExpression, String originText, String replacer) {
        Pattern pattern = Pattern.compile(regexExpression.toString());
        Matcher matcher = pattern.matcher(originText);
        return matcher.replaceAll(replacer);
    }

    /**
     * 美化多个换行符
     * (?:\\n|(?:\\r\\n))+
     *
     * @param source
     * @return
     */
    public static String beautyNextLine(@NonNull String source) {
        VerbalExpression regex = VerbalExpression.regex()
                .lineBreak().oneOrMore()
                .build();
        return executePatternRex(regex, source, SymbolConstantUtil.NEXT_LINE);
    }

    /**
     * 清除空白字符
     * ((?:\s)+)
     *
     * @param source
     * @return
     */
    public static String cleanWhiteChar(@NonNull String source) {
        VerbalExpression regex = VerbalExpression.regex()
                .capt()
                .space().oneOrMore()
                .endCapt()
                .build();
        return executePatternRex(regex, source, SymbolConstantUtil.EMPTY);
    }
}
