package com.nowcoder.community.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@ConfigurationProperties(prefix = "qiniu")
@Configuration
public class QiniuConfig {
    private Key key;
    private Map<String, Bucket> bucket;

    public static class Key {
        private String access;
        private String secret;

        public void setAccess(String access) {
            this.access = access;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getAccess() {
            return access;
        }

        public String getSecret() {
            return secret;
        }
    }

    public static class Bucket {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Map<String, Bucket> getBucket() {
        return bucket;
    }

    public void setBucket(Map<String, Bucket> bucket) {
        this.bucket = bucket;
    }
}
