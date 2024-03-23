package io.github.iamwells.admin.properties.jwt;


import lombok.Data;


@Data
public class JwtPayload {

    /**
     * 发布者
     */
    private String issuer;

    /**
     * 过期时间（小时）
     */
    private float exp = 2F;

    /**
     * 主题
     */
    private String sub;

    /**
     * 目标对象
     */
    private String aud = "user";

}
