package com.flexdevit.relay42.iot.controller;

import com.flexdevit.relay42.iot.mapper.dto.MessageDTOMapper;
import com.flexdevit.relay42.iot.model.dto.MessageResponse;
import com.flexdevit.relay42.iot.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageDTOMapper MAPPER = Mappers.getMapper(MessageDTOMapper.class);

    @Operation( summary = "Find messages", description = "Get stored messages" )
    @GetMapping
    public ResponseEntity<List<MessageResponse>> getMessages(
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "sort", required = false, defaultValue = "timestamp") String sortField,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") String sortDirection
    ) {
        var messages = messageService.findAll(size, pageNumber, sortField, sortDirection);
        return ResponseEntity.ok(messages.stream().map(MAPPER::toRelayResponse).toList());
    }
}
