package com.flexdevit.relay42.iot.gateway;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface IoTGateway {
    void sendToMqtt(String data);
}
