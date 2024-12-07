package com.flexdevit.relay42.iot.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class SensorMessageHandler implements MessageHandler  {
    private static final Logger LOG = LoggerFactory.getLogger(SensorMessageHandler.class);
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
       // try {
            LOG.debug("Received Message: " + message.getPayload());


//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
