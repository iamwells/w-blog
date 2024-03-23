package io.github.iamwells.admin.properties;


import lombok.Data;

import java.util.List;

@Data
public class AuthProperties {

    /**
     * 保持登录;
     * 为true时仅开放功能，需要前端发送rememberMe参数确认才可保持登录
     */
    private Boolean rememberMe;

    /**
     * 保持登录时间
     */
    private Integer rememberDays;

    /**
     * token请求头头名称
     */
    private String tokenHeaderName = "wToken";

    /**
     * token认证白名单
     */
    private List<String> tokenWhiteList;
}
