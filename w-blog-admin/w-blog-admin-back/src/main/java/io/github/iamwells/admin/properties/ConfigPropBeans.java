package io.github.iamwells.admin.properties;


import io.github.iamwells.admin.properties.jwt.JwtHeader;
import io.github.iamwells.admin.properties.jwt.JwtPayload;
import io.github.iamwells.admin.properties.jwt.JwtSignature;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigPropBeans {
    @Bean
    @ConfigurationProperties(prefix = "jwt.header")
    public JwtHeader jwtHeaderProperties() {
        return new JwtHeader();
    }

    @Bean
    @ConfigurationProperties(prefix = "jwt.payload")
    public JwtPayload jwtPayloadProperties() {
        return new JwtPayload();
    }

    @Bean
    @ConfigurationProperties(prefix = "jwt.signature")
    public JwtSignature jwtSignatureProperties() {
        return new JwtSignature();
    }

    @Bean
    @ConfigurationProperties(prefix = "auth")
    public AuthProperties authProperties() {
        return new AuthProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "mail")
    public MailProperties mailProperties() {
        return new MailProperties();
    }
}
