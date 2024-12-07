package com.flexdevit.relay42.iot.repository.mongodb;

import com.flexdevit.relay42.iot.model.mongo.RelayMessageEventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRelayRepository extends MongoRepository<RelayMessageEventEntity, String> {
}
