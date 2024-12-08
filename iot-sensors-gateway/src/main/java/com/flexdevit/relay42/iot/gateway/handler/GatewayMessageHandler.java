package com.flexdevit.relay42.iot.gateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexdevit.relay42.iot.gateway.IoTGateway;
import com.flexdevit.relay42.iot.gateway.message.RelayMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GatewayMessageHandler implements MessageHandler  {
    private static final Logger LOG = LoggerFactory.getLogger(GatewayMessageHandler.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final IoTGateway gateway;

    public GatewayMessageHandler(IoTGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            LOG.debug("Gateway Received Message from sensor: {}", message.getPayload());
            RelayMessage sensorMessage = mapper.readerFor(RelayMessage.class).readValue(message.getPayload().toString());
            gateway.sendToMqtt(message.getPayload().toString());
            LOG.debug("Message Parsed: {}", sensorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
