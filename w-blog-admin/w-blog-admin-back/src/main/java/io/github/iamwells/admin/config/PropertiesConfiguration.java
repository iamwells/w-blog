package io.github.iamwells.admin.config;

import io.github.iamwells.admin.properties.AuthProperties;
import io.github.iamwells.admin.properties.JasyptProperties;
import io.github.iamwells.admin.properties.JwtProperties;
import io.github.iamwells.admin.properties.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
