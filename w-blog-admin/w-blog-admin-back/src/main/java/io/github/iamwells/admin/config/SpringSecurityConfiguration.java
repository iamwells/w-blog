package io.github.iamwells.admin.config;


import io.github.iamwells.admin.filter.JsonWebTokenFilter;
import io.github.iamwells.admin.properties.AuthProperties;
import io.github.iamwells.admin.properties.JwtProperties;
import lombok.SneakyThrows;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SpringSecurityConfiguration {

//    @Bean
//    public WebSecurityCustomizer webSecurityConfigurer() {
//        return web -> web.ignoring().requestMatchers(
//                "/",
//                "/sign/on",
//                "/sign/up",
//                "/sign/out",
//                "/img/**"
//        );
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AccessDeniedHandler accessDeniedHandler,
                                           AuthenticationEntryPoint authenticationEntryPoint,
                                           JwtProperties jwtProperties,
                                           AuthProperties authProperties,
                                           RedissonClient redissonClient,
                                           Environment env
    ) throws Exception {

        http.authorizeHttpRequests(
                authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(
                                        "/",
                                        "/sign/in",
                                        "/sign/up",
                                        "/sign/out",
                                        "/favicon.ico",
                                        "/img/**"
                                ).permitAll()
                                .anyRequest().authenticated()
//                                .anyRequest().permitAll()
        );

        http.exceptionHandling(
                httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer
                            .accessDeniedHandler(accessDeniedHandler)
                            .authenticationEntryPoint(authenticationEntryPoint);
                }
        );

        http.sessionManagement(
                httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );


        if (Arrays.stream(env.getActiveProfiles()).toList().contains("dev")) {
            http.cors(httpSecurityCorsConfigurer -> {
                httpSecurityCorsConfigurer.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOrigin("http://localhost:5173");
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.addAllowedMethod("*");
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                });
            });
        }else {
            http.cors(Customizer.withDefaults());
        }

        http.csrf(AbstractHttpConfigurer::disable);

        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        JsonWebTokenFilter jsonWebTokenFilter = new JsonWebTokenFilter(authProperties, jwtProperties, redissonClient);
        http.addFilterBefore(jsonWebTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }
}
