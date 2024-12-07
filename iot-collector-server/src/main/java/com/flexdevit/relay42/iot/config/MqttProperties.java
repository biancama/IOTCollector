package com.flexdevit.relay42.iot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties(prefix = "mqtt-broker")
@Data
public class MqttProperties {
    private String hostname;
    private int port;
    private String username;
    private String password;
    private String topic;
}
