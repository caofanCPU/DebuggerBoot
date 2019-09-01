package com.xyz.caofancpu.util.result;

import lombok.Getter;

public enum GlobalErrorInfoRuntimeEnum implements ErrorInfoInterface {
    NullPointerException("RE_NPE01", "空指针错误"),
    ClassCastException("RE_CCE02", "类型转换失败"),
    IllegalArgumentException("RE_IAE03", "入参非法"),
    ArithmeticException("RE_AE04", "算术运算错误"),
    ArrayStoreException("RE_ASE05", "数组元素类型不兼容"),
    IndexOutOfBoundsException("RE_IOBE06", "数组索引越界"),
    NegativeArraySizeException("RE_NASE07", "初始容量大小非负"),
    NumberFormatException("RE_NFE08", "数字格式错误"),
    SecurityException("RE_SE09", "安全异常"),
    UnsupportedOperationException("RE_UOE10", "操作不被支持"),

    ;

    @Getter
    private String code;

    @Getter
    private String msg;

    GlobalErrorInfoRuntimeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public String getMessage() {
        return this.getMsg();
    }
}
