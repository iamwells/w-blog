package io.github.iamwells.admin.properties;


import lombok.Data;

@Data
public class JasyptProperties {
    /**
     * 同jasypt.encryptor.password
     */
    private String password;

    /**
     * 同jasypt.encryptor.iv-generator-classname
     */
    private String ivGeneratorClassname;

    /**
     * 同jasypt.encryptor.algorithm
     */
    private String algorithm;
}
