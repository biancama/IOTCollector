package com.flexdevit.relay42.iot.server.service;

import com.flexdevit.relay42.iot.server.mapper.mongo.MongoEntityMapper;
import com.flexdevit.relay42.iot.server.model.RelayMessageModel;
import com.flexdevit.relay42.iot.server.model.mongo.RelayMessageEventEntity;
import com.flexdevit.relay42.iot.server.repository.mongodb.MessageRelayRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRelayRepository messageRelayRepository;
    private final MongoEntityMapper MAPPER = Mappers.getMapper(MongoEntityMapper.class);
    public Page<RelayMessageModel> findAll(Integer size, Integer pageNumber, String sortField, String sortDirectionStr) {
        Sort.Direction sortDirection = Sort.Direction.valueOf(sortDirectionStr);
        PageRequest pageable = PageRequest.of(pageNumber, size).withSort(sortDirection, sortField);
        Page<RelayMessageEventEntity> singlePage = messageRelayRepository.findAll(pageable);
        return singlePage.map(MAPPER::toRelayMessageModel);
    }
}
