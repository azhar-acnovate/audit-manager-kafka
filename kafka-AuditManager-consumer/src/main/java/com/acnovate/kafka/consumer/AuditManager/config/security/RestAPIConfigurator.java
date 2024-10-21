package com.acnovate.kafka.consumer.AuditManager.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties to configure Qiwk-Webservice Rest Client.
 */
@Data
@Component
@ConfigurationProperties("config.rest")
public class RestAPIConfigurator {

    private String baseURL;

    private String username;

    private String password;
}
