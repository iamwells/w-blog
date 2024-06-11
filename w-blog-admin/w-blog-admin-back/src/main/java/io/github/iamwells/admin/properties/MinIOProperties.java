package io.github.iamwells.admin.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@Data
public class MinIOProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;
}
