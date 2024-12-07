package com.flexdevit.relay42.iot.mapper.mongo;

import com.flexdevit.relay42.iot.message.RelayMessage;
import com.flexdevit.relay42.iot.model.RelayMessageModel;
import com.flexdevit.relay42.iot.model.mongo.RelayMessageEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MongoEntityMapper {
    @Mapping(target = "timestamp", ignore = true)
    RelayMessageEventEntity toRelayMessageEventEntity(RelayMessage relayMessage);

    RelayMessageModel toRelayMessageModel(RelayMessageEventEntity eventEntity);
}
