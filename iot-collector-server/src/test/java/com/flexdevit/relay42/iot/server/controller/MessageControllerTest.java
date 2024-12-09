package com.flexdevit.relay42.iot.server.controller;

import com.flexdevit.relay42.iot.server.BaseTest;
import com.flexdevit.relay42.iot.server.service.MessageService;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = MessageController.class, useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = MessageController.class
        )
})
@AutoConfigureMockMvc(addFilters = false)  // disable security filters
public class MessageControllerTest {
    @MockBean
    private MessageService messageService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("When all messages are requested and there are no results Should return empty list")
    public void whenGetAllRequest_emptyListResponse() throws Exception {
        Mockito.when(messageService.findAll(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/messages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(0)));
    }


    @Test
    @DisplayName("When all messages are requested and there are stored messages Should return list")
    public void whenGetAllRequest_resultListResponse() throws Exception {
        var messages = BaseTest.getRelayMessages();
        Mockito.when(messageService.findAll(null, null, 100, 3, "serialNumber", "DESC"))
                .thenReturn(new PageImpl<>(messages));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/messages?size=100&page=3&sort=serialNumber&direction=DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is(Matchers.not(Matchers.emptyArray()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(messages.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(messages.size())));
    }

    @Test
    @DisplayName("When all messages are requested with timeframe and there are stored messages Should return list")
    public void whenGetAllRequestWithTimeframe_resultListResponse() throws Exception {
        var messages = BaseTest.getRelayMessages();
        Mockito.when(messageService.findAll(LocalDateTime.parse("2024-12-07T22:29:42"), LocalDateTime.parse("2024-12-08T22:29:42"), 100, 3, "serialNumber", "DESC"))
                .thenReturn(new PageImpl<>(messages));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/messages?start=2024-12-07T22:29:42&end=2024-12-08T22:29:42&size=100&page=3&sort=serialNumber&direction=DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is(Matchers.not(Matchers.emptyArray()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(messages.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(messages.size())));
    }

    @Test
    @DisplayName("When all messages by serial number are requested and there are stored messages Should return list")
    public void whenGetAllRequestBySerialNumber_resultListResponse() throws Exception {
        var messages = BaseTest.getRelayMessages();
        Mockito.when(messageService.findAllBySerialNumber("serial1", null, null, 100, 3, "serialNumber", "DESC"))
                .thenReturn(new PageImpl<>(messages));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/messages/serial1?size=100&page=3&sort=serialNumber&direction=DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is(Matchers.not(Matchers.emptyArray()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(messages.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(messages.size())));
    }

    @Test
    @DisplayName("When all messages by serial number with timeframe are requested and there are stored messages Should return list")
    public void whenGetAllRequestBySerialNumberWithTimeframe_resultListResponse() throws Exception {
        var messages = BaseTest.getRelayMessages();
        Mockito.when(messageService.findAllBySerialNumber("serial1", LocalDateTime.parse("2024-12-07T22:29:42"), LocalDateTime.parse("2024-12-08T22:29:42"), 100, 3, "serialNumber", "DESC"))
                .thenReturn(new PageImpl<>(messages));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/messages/serial1?start=2024-12-07T22:29:42&end=2024-12-08T22:29:42&size=100&page=3&sort=serialNumber&direction=DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is(Matchers.not(Matchers.emptyArray()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(messages.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(messages.size())));
    }

    @Test
    @DisplayName("When all statistics are requested Should return list")
    public void whenGetAllStatistics_resultListResponse() throws Exception {
        var statistics = BaseTest.getStatisticsResponse();
        Mockito.when( messageService.findAllStatistics())
                .thenReturn(statistics);

        var resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/messages/statistics")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        var result = resultActions.andReturn().getResponse().getContentAsString();
        for (int i= 0; i < statistics.size(); i++) {
            var statisticSerial = JsonPath.parse(result).read(format("$[%d].serialNumber", i), String.class);
            var statisticAverage = JsonPath.parse(result).read(format("$[%d].average", i), Double.class);
            assertThat(statisticSerial).isEqualTo(statistics.get(i).serialNumber());
            assertThat(statisticAverage).isEqualTo(statistics.get(i).average());
        }


    }
}
