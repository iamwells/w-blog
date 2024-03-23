package io.github.iamwells.admin.properties.jwt;


import lombok.Data;
import org.jose4j.jws.AlgorithmIdentifiers;


@Data
public class JwtSignature {

    /**
     * 加密算法
     */
    private String alg = AlgorithmIdentifiers.HMAC_SHA256;

    /**
     * 密钥
     */
    private String secret;

}
