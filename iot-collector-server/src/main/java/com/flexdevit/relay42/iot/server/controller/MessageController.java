package com.flexdevit.relay42.iot.server.controller;

import com.flexdevit.relay42.iot.server.mapper.dto.MessageDTOMapper;
import com.flexdevit.relay42.iot.server.model.dto.MessageResponse;
import com.flexdevit.relay42.iot.server.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageDTOMapper MAPPER = Mappers.getMapper(MessageDTOMapper.class);

    @Operation( summary = "Find messages", description = "Get stored messages" )
    @GetMapping
    public ResponseEntity<Page<MessageResponse>> getMessages(
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "sort", required = false, defaultValue = "timestamp") String sortField,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") String sortDirection
    ) {
        return ResponseEntity.ok(
                messageService.findAll(size, pageNumber, sortField, sortDirection).map(MAPPER::toRelayResponse));

    }
}
