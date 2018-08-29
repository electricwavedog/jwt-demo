package com.example.jwtdemo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyiqian
 */
@Configuration
@Getter
public class AppConfig {

    @Value("${rsa.key.private}")
    private String privateKey;

    @Value("${rsa.key.public}")
    private String publicKey;

}
