package com.flexdevit.relay42.iot.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class SensorsGateway {
    @Autowired
    private static IoTGateway gateway;
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(SensorsGateway.class)
                        .web(WebApplicationType.NONE)
                        .run(args);

    }
}
