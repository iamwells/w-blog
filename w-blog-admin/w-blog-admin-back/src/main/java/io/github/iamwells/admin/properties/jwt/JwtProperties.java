package io.github.iamwells.admin.properties.jwt;


import io.github.iamwells.admin.properties.ConfigPropBeans;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;


@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Data
@Component
@ConditionalOnBean({ConfigPropBeans.class})
public class JwtProperties {
    @Autowired
    private JwtHeader header;
    @Autowired
    private JwtPayload payload;
    @Autowired
    private JwtSignature signature;
}
