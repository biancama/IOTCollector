package com.flexdevit.relay42.iot.server.service;

import com.flexdevit.relay42.iot.server.mapper.dto.StatisticsDTOMapper;
import com.flexdevit.relay42.iot.server.mapper.mongo.MongoEntityMapper;
import com.flexdevit.relay42.iot.server.model.RelayMessageModel;
import com.flexdevit.relay42.iot.server.model.dto.StatisticsResponse;
import com.flexdevit.relay42.iot.server.model.mongo.RelayMessageEventEntity;
import com.flexdevit.relay42.iot.server.repository.mongodb.MessageRelayRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRelayRepository messageRelayRepository;
    private final MongoEntityMapper MAPPER = Mappers.getMapper(MongoEntityMapper.class);
    private final StatisticsDTOMapper STATISTICS_MAPPER = Mappers.getMapper(StatisticsDTOMapper.class);

    public Page<RelayMessageModel> findAll(LocalDateTime startDate, LocalDateTime endDate, Integer size, Integer pageNumber, String sortField, String sortDirectionStr) {
        PageRequest pageable = getPageRequest(size, pageNumber, sortField, sortDirectionStr);
        Page<RelayMessageEventEntity> singlePage = (startDate != null && endDate != null) ? messageRelayRepository.findAllByTimestampBetween(startDate, endDate, pageable) : messageRelayRepository.findAll(pageable);
        return singlePage.map(MAPPER::toRelayMessageModel);
    }

    private static PageRequest getPageRequest(Integer size, Integer pageNumber, String sortField, String sortDirectionStr) {
        Sort.Direction sortDirection = Sort.Direction.valueOf(sortDirectionStr);
        PageRequest pageable = PageRequest.of(pageNumber, size).withSort(sortDirection, sortField);
        return pageable;
    }

    public Page<RelayMessageModel> findAllBySerialNumber(String serialNumber, LocalDateTime startDate, LocalDateTime endDate, Integer size, Integer pageNumber, String sortField, String sortDirectionStr) {
        PageRequest pageable = getPageRequest(size, pageNumber, sortField, sortDirectionStr);
        Page<RelayMessageEventEntity> singlePage = (startDate != null && endDate != null) ? messageRelayRepository.findAllBySerialNumberAndTimestampBetween(serialNumber, startDate, endDate, pageable) :  messageRelayRepository.findAllBySerialNumber(serialNumber, pageable);
        return singlePage.map(MAPPER::toRelayMessageModel);
    }

    public List<StatisticsResponse> findAllStatistics() {
        return messageRelayRepository.getStatistics().getMappedResults().stream().map(STATISTICS_MAPPER::toStatisticsResponse).toList();
    }
    public List<StatisticsResponse> findAllBySerialNumberStatistics(String serialNumber) {
        return messageRelayRepository.getStatistics(serialNumber).getMappedResults().stream().map(STATISTICS_MAPPER::toStatisticsResponse).toList();
    }

}
