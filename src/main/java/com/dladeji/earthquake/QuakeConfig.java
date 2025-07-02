package com.dladeji.earthquake;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.constants")
public class QuakeConfig {
    private String baseUrl;
    private String smallQueryUri;
    private String largeQueryUri;
}
