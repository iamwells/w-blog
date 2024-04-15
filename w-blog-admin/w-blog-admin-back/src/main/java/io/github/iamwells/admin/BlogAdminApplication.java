package io.github.iamwells.admin;


import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
@Slf4j
public class BlogAdminApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(BlogAdminApplication.class, args);
        } catch (Exception e) {

        }
    }
}
