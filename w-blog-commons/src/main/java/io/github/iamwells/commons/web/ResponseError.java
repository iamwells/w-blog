package io.github.iamwells.commons.web;

public enum ResponseError {

    PARAMETER_ERROR("PR100", "参数错误", null),
    PARAMETER_MISSING("PR101", "参数缺失", null),
    PARAMETER_INVALID_TYPE("PR102", "无效参数类型", null),
    RESULT_ERROR("RS100", "结果集错误", null),
    ;

    private String code;
    private String msg;
    private String paramName;

    ResponseError(String code, String msg, String paramName) {
        this.code = code;
        this.paramName = paramName;
        this.msg = paramName + msg;
    }

    public ResponseError setCode(String code) {
        this.code = code;
        return this;
    }

    public ResponseError setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public ResponseError setParamName(String paramName) {
        this.paramName = paramName;
        return this;
    }
}
