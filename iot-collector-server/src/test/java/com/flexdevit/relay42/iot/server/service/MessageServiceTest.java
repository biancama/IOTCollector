package com.flexdevit.relay42.iot.server.service;

import com.flexdevit.relay42.iot.server.BaseTest;
import com.flexdevit.relay42.iot.server.model.RelayMessageModel;
import com.flexdevit.relay42.iot.server.repository.mongodb.MessageRelayRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    @Mock
    private MessageRelayRepository messageRelayRepository;
    @InjectMocks
    private MessageService messageService;

    @Nested
    class FindAllTests {
        @Test
        @DisplayName("When no messages are stored, should return empty list")
        void findAllMessages_emptyList() {
            Mockito.when(messageRelayRepository.findAll(Mockito.any(PageRequest.class)))
                    .thenReturn(new PageImpl(new ArrayList<>()));
            Page<RelayMessageModel> result = messageService.findAll(10, 0, "title", "ASC");
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
            Page<RelayMessageModel> result = messageService.findAll(10, 0, "timestamp", "ASC");
            // then
            assertFalse(result.isEmpty());
            assertEquals(messages.size(), result.getTotalElements());
        }
    }
}
