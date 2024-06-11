package io.github.iamwells.admin.config;

import io.github.iamwells.admin.properties.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class PropertiesConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "blog.jwt")
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "blog.auth")
    public AuthProperties authProperties() {
        return new AuthProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "blog.mail")
    public MailProperties mailProperties() {
        return new MailProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "blog.jasypt")
    public JasyptProperties blogProperties() {
        return new JasyptProperties();
    }

    @Bean
    @ConfigurationProperties("blog.minio")
    public MinIOProperties minIOProperties() {
        return new MinIOProperties();
    }
}
