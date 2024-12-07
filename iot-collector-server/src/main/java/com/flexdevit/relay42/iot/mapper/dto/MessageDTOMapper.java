package com.flexdevit.relay42.iot.mapper.dto;

import com.flexdevit.relay42.iot.model.RelayMessageModel;
import com.flexdevit.relay42.iot.model.dto.MessageResponse;
import org.mapstruct.Mapper;

@Mapper
public interface MessageDTOMapper {

    MessageResponse toRelayResponse(RelayMessageModel relayMessageModel);

}
