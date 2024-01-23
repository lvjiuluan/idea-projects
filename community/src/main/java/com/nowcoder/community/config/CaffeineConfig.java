package com.nowcoder.community.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "caffeine")
@Data
public class CaffeineConfig {

    private Post post;

    @Data
    public static class Post {
        private Integer maxSize;
        private Integer expireSeconds;
    }
}

