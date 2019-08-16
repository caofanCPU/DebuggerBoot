package com.xyz.caofancpu.trackingtime.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.xyz.caofancpu.trackingtime.annotion.Check;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;
import com.xyz.caofancpu.util.dataOperateUtils.ReflectionUtil;
import com.xyz.caofancpu.util.result.CustomerErrorInfo;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.result.ResultBody;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

@Aspect
@Component
@Order(2)
public class CheckParamAspect {

    private static final Logger logger = LoggerFactory.getLogger(CheckParamAspect.class);

    // -====================== 常量 =========================

    private static final String SEPARATOR = ":";

    /**
     * 是否不为空
     *
     * @param value       字段值
     * @param operatorNum 操作数，这里不需要，只是为了参数统一
     * @return 是否不为空
     */
    private static Boolean isNotNull(Object value, String operatorNum) {
        Boolean isNotNull = Boolean.TRUE;
        Boolean isStringNull = (value instanceof String) && StringUtils.isEmpty((String) value);
        Boolean isCollectionNull = (value instanceof Collection) && CollectionUtils.isEmpty((Collection) value);
        if (value == null) {
            isNotNull = Boolean.FALSE;
        } else if (isStringNull || isCollectionNull) {
            isNotNull = Boolean.FALSE;
        }
        return isNotNull;
    }

    /**
     * 是否为整数
     *
     * @param value       字段值
     * @param operatorNum 操作数，这里不需要，只是为了参数统一
     * @return 是否不为空
     */
    private static Boolean isNumber(Object value, String operatorNum) {
        Boolean isNumber = Boolean.FALSE;

        if (value != null && (value instanceof Integer || value instanceof Long)) {
            isNumber = Boolean.TRUE;
        }
        return isNumber;
    }

    @Deprecated
    private static Boolean isBoolStr(Object value, String operatorNum) {
        if (value != null && value instanceof Boolean) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 是否为金额
     *
     * @param value       字段值
     * @param operatorNum 操作数，这里不需要，只是为了参数统一
     * @return 是否不为空
     */
    private static Boolean isMoney(Object value, String operatorNum) {
        Boolean isMoney = Boolean.FALSE;
        //表示金额的正则表达式
        String moneyReg = "^\\d+(\\.\\d{1,2})?$";
        Pattern moneyPattern = Pattern.compile(moneyReg);

        if (value != null && moneyPattern.matcher(value.toString()).matches()) {
            isMoney = Boolean.TRUE;
        }

        return isMoney;
    }

    // -=================== 对不同类型的值进行校验 起 =======================

    /**
     * 是否为邮箱
     *
     * @param value       字段值
     * @param operatorNum 操作数，这里不需要，只是为了参数统一
     * @return 是否不为空
     */
    private static Boolean isMail(Object value, String operatorNum) {
        Boolean isMail = Boolean.FALSE;
        String mailReg = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";//表示金额的正则表达式
        Pattern mailPattern = Pattern.compile(mailReg);

        if (value != null && mailPattern.matcher(value.toString()).matches()) {
            isMail = Boolean.TRUE;
        }

        return isMail;
    }

    /**
     * 是否大于
     *
     * @param value       字段值
     * @param operatorNum 操作数
     * @return 是否大于
     */
    private static Boolean isGreaterThan(Object value, String operatorNum) {
        Boolean isGreaterThan = Boolean.FALSE;
        if (value == null) {
            return Boolean.FALSE;
        }
        Boolean isStringGreaterThen = (value instanceof String) && ((String) value).length() > Integer.valueOf(operatorNum);
        Boolean isLongGreaterThen = (value instanceof Long) && ((Long) value) > Long.valueOf(operatorNum);
        Boolean isIntegerGreaterThen = (value instanceof Integer) && ((Integer) value) > Integer.valueOf(operatorNum);
        Boolean isShortGreaterThen = (value instanceof Short) && ((Short) value) > Short.valueOf(operatorNum);
        Boolean isFloatGreaterThen = (value instanceof Float) && ((Float) value) > Float.valueOf(operatorNum);
        Boolean isDoubleGreaterThen = (value instanceof Double) && ((Double) value) > Double.valueOf(operatorNum);
        Boolean isCollectionGreaterThen = (value instanceof Collection) && ((Collection) value).size() > Integer.valueOf(operatorNum);
        if (isStringGreaterThen || isLongGreaterThen || isIntegerGreaterThen ||
                isShortGreaterThen || isFloatGreaterThen || isDoubleGreaterThen || isCollectionGreaterThen) {
            isGreaterThan = Boolean.TRUE;
        }
        return isGreaterThan;
    }

    /**
     * 是否大于等于
     *
     * @param value       字段值
     * @param operatorNum 操作数
     * @return 是否大于等于
     */
    private static Boolean isGreaterThanEqual(Object value, String operatorNum) {
        Boolean isGreaterThanEqual = Boolean.FALSE;
        if (value == null) {
            return Boolean.FALSE;
        }
        Boolean isStringGreaterThenEqual = (value instanceof String) && ((String) value).length() >= Integer.valueOf(operatorNum);
        Boolean isLongGreaterThenEqual = (value instanceof Long) && ((Long) value) >= Long.valueOf(operatorNum);
        Boolean isIntegerGreaterThenEqual = (value instanceof Integer) && ((Integer) value) >= Integer.valueOf(operatorNum);
        Boolean isShortGreaterThenEqual = (value instanceof Short) && ((Short) value) >= Short.valueOf(operatorNum);
        Boolean isFloatGreaterThenEqual = (value instanceof Float) && ((Float) value) >= Float.valueOf(operatorNum);
        Boolean isDoubleGreaterThenEqual = (value instanceof Double) && ((Double) value) >= Double.valueOf(operatorNum);
        Boolean isCollectionGreaterThenEqual = (value instanceof Collection) && ((Collection) value).size() >= Integer.valueOf(operatorNum);
        if (isStringGreaterThenEqual || isLongGreaterThenEqual || isIntegerGreaterThenEqual ||
                isShortGreaterThenEqual || isFloatGreaterThenEqual || isDoubleGreaterThenEqual || isCollectionGreaterThenEqual) {
            isGreaterThanEqual = Boolean.TRUE;
        }
        return isGreaterThanEqual;
    }

    /**
     * 是否少于
     *
     * @param value       字段值
     * @param operatorNum 操作数
     * @return 是否少于
     */
    private static Boolean isLessThan(Object value, String operatorNum) {
        Boolean isLessThan = Boolean.FALSE;
        if (value == null) {
            return Boolean.FALSE;
        }
        Boolean isStringLessThen = (value instanceof String) && ((String) value).length() < Integer.valueOf(operatorNum);
        Boolean isLongLessThen = (value instanceof Long) && ((Long) value) < Long.valueOf(operatorNum);
        Boolean isIntegerLessThen = (value instanceof Integer) && ((Integer) value) < Integer.valueOf(operatorNum);
        Boolean isShortLessThen = (value instanceof Short) && ((Short) value) < Short.valueOf(operatorNum);
        Boolean isFloatLessThen = (value instanceof Float) && ((Float) value) < Float.valueOf(operatorNum);
        Boolean isDoubleLessThen = (value instanceof Double) && ((Double) value) < Double.valueOf(operatorNum);
        Boolean isCollectionLessThen = (value instanceof Collection) && ((Collection) value).size() < Integer.valueOf(operatorNum);
        if (isStringLessThen || isLongLessThen || isIntegerLessThen ||
                isShortLessThen || isFloatLessThen || isDoubleLessThen || isCollectionLessThen) {
            isLessThan = Boolean.TRUE;
        }
        return isLessThan;
    }

    /**
     * 是否少于等于
     *
     * @param value       字段值
     * @param operatorNum 操作数
     * @return 是否少于等于
     */
    private static Boolean isLessThanEqual(Object value, String operatorNum) {
        Boolean isLessThanEqual = Boolean.FALSE;
        if (value == null) {
            return Boolean.FALSE;
        }
        Boolean isStringLessThenEqual = (value instanceof String) && ((String) value).length() <= Integer.valueOf(operatorNum);
        Boolean isLongLessThenEqual = (value instanceof Long) && ((Long) value) <= Long.valueOf(operatorNum);
        Boolean isIntegerLessThenEqual = (value instanceof Integer) && ((Integer) value) <= Integer.valueOf(operatorNum);
        Boolean isShortLessThenEqual = (value instanceof Short) && ((Short) value) <= Short.valueOf(operatorNum);
        Boolean isFloatLessThenEqual = (value instanceof Float) && ((Float) value) <= Float.valueOf(operatorNum);
        Boolean isDoubleLessThenEqual = (value instanceof Double) && ((Double) value) <= Double.valueOf(operatorNum);
        Boolean isCollectionLessThenEqual = (value instanceof Collection) && ((Collection) value).size() <= Integer.valueOf(operatorNum);
        if (isStringLessThenEqual || isLongLessThenEqual || isIntegerLessThenEqual ||
                isShortLessThenEqual || isFloatLessThenEqual || isDoubleLessThenEqual || isCollectionLessThenEqual) {
            isLessThanEqual = Boolean.TRUE;
        }
        return isLessThanEqual;
    }

    /**
     * 是否不等于
     *
     * @param value       字段值
     * @param operatorNum 操作数
     * @return 是否不等于
     */
    private static Boolean isNotEqual(Object value, String operatorNum) {
        Boolean isNotEqual = Boolean.FALSE;
        if (value == null) {
            return Boolean.FALSE;
        }
        Boolean isStringNotEqual = (value instanceof String) && !value.equals(operatorNum);
        Boolean isLongNotEqual = (value instanceof Long) && !value.equals(Long.valueOf(operatorNum));
        Boolean isIntegerNotEqual = (value instanceof Integer) && !value.equals(Integer.valueOf(operatorNum));
        Boolean isShortNotEqual = (value instanceof Short) && !value.equals(Short.valueOf(operatorNum));
        Boolean isFloatNotEqual = (value instanceof Float) && !value.equals(Float.valueOf(operatorNum));
        Boolean isDoubleNotEqual = (value instanceof Double) && !value.equals(Double.valueOf(operatorNum));
        Boolean isCollectionNotEqual = (value instanceof Collection) && ((Collection) value).size() != Integer.valueOf(operatorNum);
        if (isStringNotEqual || isLongNotEqual || isIntegerNotEqual ||
                isShortNotEqual || isFloatNotEqual || isDoubleNotEqual || isCollectionNotEqual) {
            isNotEqual = Boolean.TRUE;
        }
        return isNotEqual;
    }

    @Around("execution(* com.xyz..*.service..*.*.*(..))")
    public Object check(ProceedingJoinPoint point)
            throws Throwable {
        Object obj;
        // 参数校验
        String msg = doCheck(point);
        if (StringUtils.isNotEmpty(msg)) {
            // 处理完请求，返回内容
            StringBuilder sb = new StringBuilder();
            sb.append("\n[后台响应结果]:\n"
                    + "响应耗时[0ms]" + "\n"
                    + "响应数据结果:\n"
                    + JSONUtil.formatStandardJSON(JSONObject.toJSONString(new ResultBody().fail(msg))));
            logger.info(sb.toString());
            // 这里可以返回自己封装的返回类
            CustomerErrorInfo errInfo = new CustomerErrorInfo("501", msg);
            throw new GlobalErrorInfoException(errInfo);
        }
        // 通过校验，继续执行原有方法
        obj = point.proceed();
        return obj;
    }

    /**
     * 参数校验
     *
     * @param point ProceedingJoinPoint
     * @return 错误信息
     */
    private String doCheck(ProceedingJoinPoint point) {
        // 获取方法参数值
        Object[] arguments = point.getArgs();
        // 获取方法
        Method method = getMethod(point);
        // 默认的错误信息
        String methodInfo = StringUtils.isBlank(method.getName()) ? "" : " 调用方法 " + method.getName();
        String msg = "";
        if (isCheck(method, arguments)) {
            Check annotation = method.getAnnotation(Check.class);
            String[] fields = annotation.value();
            // 只支持对第一个参数进行校验
            Object vo = arguments[0];
            if (vo == null) {
                msg = "入参不能为空!";
            } else {
                for (String field : fields) {
                    if (StringUtils.isNotEmpty(msg)) {
                        break;
                    }
                    // 解析字段
                    FieldInfo info = resolveField(field, methodInfo);
                    // 获取字段的值
                    Object value = ReflectionUtil.invokeGetter(vo, info.field);
                    // 执行校验规则
                    Boolean isValid = info.optEnum.fun.apply(value, info.operatorNum);
                    msg = isValid ? msg : info.innerMsg;
                }
            }
        }
        return msg;
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint
                        .getTarget()
                        .getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(),
                                method.getParameterTypes());
            } catch (SecurityException | NoSuchMethodException e) {
                logger.error("反射获取方法失败，{}" + e.getMessage());
            }
        }
        return method;
    }

    /**
     * 解析字段
     *
     * @param fieldStr   字段字符串
     * @param methodInfo 方法信息
     * @return 字段信息实体类
     */
    private FieldInfo resolveField(String fieldStr, String methodInfo) {
        FieldInfo fieldInfo = new FieldInfo();
        String innerMsg = "";
        // 解析提示信息
        if (fieldStr.contains(SEPARATOR)) {
            innerMsg = fieldStr.split(SEPARATOR)[1];
            fieldStr = fieldStr.split(SEPARATOR)[0];
        }
        // 解析操作符
        if (fieldStr.contains(Operator.GREATER_THAN_EQUAL.value)) {
            fieldInfo.optEnum = Operator.GREATER_THAN_EQUAL;
        } else if (fieldStr.contains(Operator.LESS_THAN_EQUAL.value)) {
            fieldInfo.optEnum = Operator.LESS_THAN_EQUAL;
        } else if (fieldStr.contains(Operator.GREATER_THAN.value)) {
            fieldInfo.optEnum = Operator.GREATER_THAN;
        } else if (fieldStr.contains(Operator.LESS_THAN.value)) {
            fieldInfo.optEnum = Operator.LESS_THAN;
        } else if (fieldStr.contains(Operator.NOT_EQUAL.value)) {
            fieldInfo.optEnum = Operator.NOT_EQUAL;
        } else if (fieldStr.contains(Operator.IS_NUMBER.value)) {
            fieldInfo.optEnum = Operator.IS_NUMBER;
        } else if (fieldStr.contains(Operator.IS_BOOL_STR.value)) {
            fieldInfo.optEnum = Operator.IS_BOOL_STR;
        } else if (fieldStr.contains(Operator.IS_MONEY.value)) {
            fieldInfo.optEnum = Operator.IS_MONEY;
        } else if (fieldStr.contains(Operator.IS_MAIL.value)) {
            fieldInfo.optEnum = Operator.IS_MAIL;
        } else {
            fieldInfo.optEnum = Operator.NOT_NULL;
        }
        // 不等于空，直接赋值字段
        if (fieldInfo.optEnum == Operator.NOT_NULL) {
            fieldInfo.field = fieldStr.substring(0, (fieldStr.indexOf(Operator.NOT_NULL.value) - 1));
            fieldInfo.operatorNum = "";
        } else if (fieldInfo.optEnum == Operator.IS_NUMBER) {
            fieldInfo.field = fieldStr.substring(0, (fieldStr.indexOf(Operator.IS_NUMBER.value) - 1));
            fieldInfo.operatorNum = "";
        } else if (fieldInfo.optEnum == Operator.IS_BOOL_STR) {
            fieldInfo.field = fieldStr.substring(0, (fieldStr.indexOf(Operator.IS_BOOL_STR.value) - 1));
            fieldInfo.operatorNum = "";
        } else if (fieldInfo.optEnum == Operator.IS_MONEY) {
            fieldInfo.field = fieldStr.substring(0, (fieldStr.indexOf(Operator.IS_MONEY.value) - 1));
            fieldInfo.operatorNum = "";
        } else if (fieldInfo.optEnum == Operator.IS_MAIL) {
            fieldInfo.field = fieldStr.substring(0, (fieldStr.indexOf(Operator.IS_MAIL.value) - 1));
            fieldInfo.operatorNum = "";
        }
        // 其他操作符，需要分离出字段和操作数
        else {
            fieldInfo.field = fieldStr.split(fieldInfo.optEnum.value)[0];
            fieldInfo.operatorNum = fieldStr.split(fieldInfo.optEnum.value)[1];
        }
        fieldInfo.operator = fieldInfo.optEnum.value;
        // 处理错误信息
        String defaultMsg = fieldInfo.field + " must " + fieldInfo.operator + " " + fieldInfo.operatorNum + methodInfo;
        fieldInfo.innerMsg = StringUtils.isBlank(innerMsg) ? defaultMsg : innerMsg;
        return fieldInfo;
    }


    // -=================== 对不同类型的值进行校验 止 =======================

    /**
     * 判断是否符合参数规则
     *
     * @param method    方法
     * @param arguments 方法参数
     * @return 是否符合
     */
    private Boolean isCheck(Method method, Object[] arguments) {
        Boolean isCheck = Boolean.TRUE;
        // 只允许有一个参数
        if (!method.isAnnotationPresent(Check.class)
                || arguments == null
                || arguments.length != 1) {
            isCheck = Boolean.FALSE;
        }
        return isCheck;
    }

    /**
     * 获取方法
     *
     * @param joinPoint ProceedingJoinPoint
     * @return 方法
     */


    /**
     * 操作枚举，封装操作符和对应的校验规则
     */
    enum Operator {
        /**
         * 大于
         */
        GREATER_THAN(">", CheckParamAspect::isGreaterThan),
        /**
         * 大于等于
         */
        GREATER_THAN_EQUAL(">=", CheckParamAspect::isGreaterThanEqual),
        /**
         * 小于
         */
        LESS_THAN("<", CheckParamAspect::isLessThan),
        /**
         * 小于等于
         */
        LESS_THAN_EQUAL("<=", CheckParamAspect::isLessThanEqual),
        /**
         * 不等于
         */
        NOT_EQUAL("!=", CheckParamAspect::isNotEqual),
        /**
         * 不为空
         */
        NOT_NULL("not null", CheckParamAspect::isNotNull),

        /**
         * 金额
         */
        IS_MONEY("is money", CheckParamAspect::isMoney),
        /**
         * 邮箱
         */
        IS_MAIL("is mail", CheckParamAspect::isMail),
        /**
         * 数字
         */
        IS_NUMBER("is number", CheckParamAspect::isNumber),

        /**
         * 布尔型字符串
         * Spring框架已对此做了参数验证
         */
        @Deprecated
        IS_BOOL_STR("is bool string", CheckParamAspect::isBoolStr);

        private String value;

        /**
         * BiFunction：接收字段值(Object)和操作数(String)，返回是否符合规则(Boolean)
         */
        private BiFunction<Object, String, Boolean> fun;

        Operator(String value, BiFunction<Object, String, Boolean> fun) {
            this.value = value;
            this.fun = fun;
        }
    }

    /**
     * 字段信息
     */
    class FieldInfo {
        /**
         * 字段
         */
        String field;
        /**
         * 提示信息
         */
        String innerMsg;
        /**
         * 操作符
         */
        String operator;
        /**
         * 操作数
         */
        String operatorNum;
        /**
         * 操作枚举
         */
        Operator optEnum;
    }


}
