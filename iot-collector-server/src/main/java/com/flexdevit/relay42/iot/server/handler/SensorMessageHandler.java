package com.flexdevit.relay42.iot.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexdevit.relay42.iot.server.mapper.mongo.MongoEntityMapper;
import com.flexdevit.relay42.iot.message.RelayMessage;
import com.flexdevit.relay42.iot.server.repository.mongodb.MessageRelayRepository;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class SensorMessageHandler implements MessageHandler  {
    private static final Logger LOG = LoggerFactory.getLogger(SensorMessageHandler.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final MessageRelayRepository messageRelayRepository;

    private final MongoEntityMapper MAPPER = Mappers.getMapper(MongoEntityMapper.class);
    public SensorMessageHandler(MessageRelayRepository messageRelayRepository) {
        this.messageRelayRepository = messageRelayRepository;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            LOG.debug("Received Message: {}", message.getPayload());
            RelayMessage sensorMessage = mapper.readerFor(RelayMessage.class).readValue(message.getPayload().toString());
            var relayMessageEntity = MAPPER.toRelayMessageEventEntity(sensorMessage);
            relayMessageEntity.setTimestamp(LocalDateTime.now());
            messageRelayRepository.insert(relayMessageEntity);
            LOG.debug("Message Inserted: {}", relayMessageEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
