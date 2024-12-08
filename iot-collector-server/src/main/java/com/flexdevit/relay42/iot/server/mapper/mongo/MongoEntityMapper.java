package com.flexdevit.relay42.iot.server.mapper.mongo;

import com.flexdevit.relay42.iot.server.message.RelayMessage;
import com.flexdevit.relay42.iot.server.model.RelayMessageModel;
import com.flexdevit.relay42.iot.server.model.mongo.RelayMessageEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MongoEntityMapper {
    @Mapping(target = "timestamp", ignore = true)
    @Mapping(target = "id", ignore = true)
    RelayMessageEventEntity toRelayMessageEventEntity(RelayMessage relayMessage);

    RelayMessageModel toRelayMessageModel(RelayMessageEventEntity eventEntity);
}
