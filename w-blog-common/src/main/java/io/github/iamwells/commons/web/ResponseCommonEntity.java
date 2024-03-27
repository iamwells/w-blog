package io.github.iamwells.commons.web;


import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * 统一返回实体
 *
 * @param <T> data类型
 */
public class ResponseCommonEntity<T> {
//    OK(HttpStatus.OK, null, null),
//    BAD_REQUEST(HttpStatus.BAD_REQUEST, null, null),
//    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, null, null),
//    NOT_FOUND(HttpStatus.NOT_FOUND, null, null),
//    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, null, null);
    /**
     * 时间戳
     */
    private OffsetDateTime timestamp;
    /**
     * 原生Http状态码
     */
    private Integer status;
    /**
     * 原生状态短语
     */
    private String phrase;

    /**
     * 自定义错误代码
     */
    private String errCode;
    /**
     * 自定义错误消息
     */
    private String errMsg;

    /**
     * 数据
     */
    private T data;

    /**
     * 认证成功或刷新时返回token
     */
    private String token;


    public ResponseCommonEntity(Integer status, String phrase, String errCode, String errMsg, T data, String token) {
        this.timestamp = OffsetDateTime.now();
        this.status = status;
        this.phrase = phrase;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.data = data;
        this.token = token;
    }

    public ResponseCommonEntity(HttpStatus status, String errCode, String errMsg, T data) {
        this.timestamp = OffsetDateTime.now();
        this.status = status.value();
        this.phrase = status.getReasonPhrase();
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.data = data;
    }

    public static <T> ResponseCommonEntity<T> OK(T data) {
        return new ResponseCommonEntity<>(HttpStatus.OK, null, null, data);
    }

    public static <T> ResponseCommonEntity<T> SERVER_ERROR(String errCode, String errMsg) {
        return new ResponseCommonEntity<>(HttpStatus.INTERNAL_SERVER_ERROR, errCode, errMsg, null);
    }

    public static <T> ResponseCommonEntity<T> CLIENT_ERROR(HttpStatus status, String errCode, String errMsg) {
        return new ResponseCommonEntity<>(status, errCode, errMsg, null);
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseCommonEntity<?> that = (ResponseCommonEntity<?>) o;
        return Objects.equals(timestamp, that.timestamp) && Objects.equals(status, that.status) && Objects.equals(phrase, that.phrase) && Objects.equals(errCode, that.errCode) && Objects.equals(errMsg, that.errMsg) && Objects.equals(data, that.data) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, status, phrase, errCode, errMsg, data, token);
    }

    @Override
    public String toString() {
        return "ResponseCommonEntity{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", phrase='" + phrase + '\'' +
                ", errCode='" + errCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                ", token='" + token + '\'' +
                '}';
    }
}
