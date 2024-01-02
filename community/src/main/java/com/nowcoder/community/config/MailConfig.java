package com.nowcoder.community.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailConfig {
    private String username;
    private String password;
    private String protocol;
    private String host;
    private Integer port;

}
