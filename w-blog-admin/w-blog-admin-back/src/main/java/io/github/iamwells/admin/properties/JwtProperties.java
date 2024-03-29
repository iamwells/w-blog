package io.github.iamwells.admin.properties;


import io.github.iamwells.admin.properties.jwt.JwtHeader;
import io.github.iamwells.admin.properties.jwt.JwtPayload;
import io.github.iamwells.admin.properties.jwt.JwtSignature;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Data
public class JwtProperties {

    private JwtHeader header;
    private JwtPayload payload;
    private JwtSignature signature;

    @Bean
    @ConfigurationProperties(prefix = "blog.jwt.header")
    public JwtHeader jwtHeaderProperties() {
        this.header = new JwtHeader();
        return this.header;
    }

    @Bean
    @ConfigurationProperties(prefix = "blog.jwt.payload")
    public JwtPayload jwtPayloadProperties() {
        this.payload = new JwtPayload();
        return this.payload;
    }

    @Bean
    @ConfigurationProperties(prefix = "blog.jwt.signature")
    public JwtSignature jwtSignatureProperties() {
        this.signature = new JwtSignature();
        return this.signature;
    }
}
