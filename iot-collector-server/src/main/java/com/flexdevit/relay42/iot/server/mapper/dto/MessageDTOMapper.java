package com.flexdevit.relay42.iot.server.mapper.dto;

import com.flexdevit.relay42.iot.server.model.RelayMessageModel;
import com.flexdevit.relay42.iot.server.model.dto.MessageResponse;
import org.mapstruct.Mapper;

@Mapper
public interface MessageDTOMapper {

    MessageResponse toRelayResponse(RelayMessageModel relayMessageModel);

}
