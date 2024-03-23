package io.github.iamwells.admin.properties.jwt;


import lombok.Data;


@Data
public class JwtHeader {

    /**
     * 类型
     */
    private String typ = "JWT";

}
