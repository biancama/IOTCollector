package com.flexdevit.relay42.iot.server.controller;

import com.flexdevit.relay42.iot.server.BaseTest;
import com.flexdevit.relay42.iot.server.service.MessageService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)  // disable security filters
public class MessageControllerTest {
    @MockBean
    private MessageService messageService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("When all messages are requested and there are no results Should return empty list")
    public void whenGetAllRequest_emptyListResponse() throws Exception {
        Mockito.when(messageService.findAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
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
        Mockito.when(messageService.findAll(100, 3, "serialNumber", "DESC"))
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
}
