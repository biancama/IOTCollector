package com.flexdevit.relay42.iot.server.service;

import com.flexdevit.relay42.iot.server.BaseTest;
import com.flexdevit.relay42.iot.server.mapper.dto.StatisticsDTOMapper;
import com.flexdevit.relay42.iot.server.model.RelayMessageModel;
import com.flexdevit.relay42.iot.server.model.dto.StatisticsResponse;
import com.flexdevit.relay42.iot.server.model.mongo.Statistics;
import com.flexdevit.relay42.iot.server.repository.mongodb.MessageRelayRepository;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    @Mock
    private MessageRelayRepository messageRelayRepository;
    @InjectMocks
    private MessageService messageService;

    private final StatisticsDTOMapper STATISTICS_MAPPER = Mappers.getMapper(StatisticsDTOMapper.class);

    @Nested
    class FindAllTests {
        @Test
        @DisplayName("When no messages are stored, should return empty list")
        void findAllMessages_emptyList() {
            Mockito.when(messageRelayRepository.findAll(Mockito.any(PageRequest.class)))
                    .thenReturn(new PageImpl(new ArrayList<>()));
            Page<RelayMessageModel> result = messageService.findAll(null, null, 10, 0, "title", "ASC");
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("When there are message stored, should return list")
        void findAllMessages_resultList() {
            // given
            var messages = BaseTest.getRelayMessageEvents();

            var sort = Sort.by(Sort.Direction.ASC, "timestamp");
            var pageRequest = PageRequest.of(0, 10, sort);
            Mockito.when(messageRelayRepository.findAll(pageRequest))
                    .thenReturn(new PageImpl(messages));
            // when
            Page<RelayMessageModel> result = messageService.findAll(null, null, 10, 0, "timestamp", "ASC");
            // then
            assertFalse(result.isEmpty());
            assertEquals(messages.size(), result.getTotalElements());
        }
    }
    @Nested
    class FindStatistics {
        @Test
        @DisplayName("When there are message stored, then statistics should be returned")
        void findAllStatistics_resultList() {
            // given
            var statistics = BaseTest.getStatistics();
            AggregationResults<Statistics> aggregationResult = new AggregationResults<>(statistics, new Document());
            Mockito.when(messageRelayRepository.getStatistics())
                    .thenReturn(aggregationResult);
            // when
            List<StatisticsResponse> result = messageService.findAllStatistics();

            // then
            assertFalse(result.isEmpty());
            assertEquals(statistics.size(), result.size());
            assertThat(result).containsAll(statistics.stream().map(STATISTICS_MAPPER::toStatisticsResponse).toList());
        }
    }
}
