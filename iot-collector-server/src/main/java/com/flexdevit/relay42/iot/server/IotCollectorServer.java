package com.flexdevit.relay42.iot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
public class IotCollectorServer {
    public static void main( String[] args ) {
        SpringApplication.run(IotCollectorServer.class, args);
    }
}
