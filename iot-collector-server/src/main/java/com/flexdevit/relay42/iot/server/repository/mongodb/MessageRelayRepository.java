package com.flexdevit.relay42.iot.server.repository.mongodb;

import com.flexdevit.relay42.iot.server.model.mongo.RelayMessageEventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRelayRepository extends MongoRepository<RelayMessageEventEntity, String> {
}
