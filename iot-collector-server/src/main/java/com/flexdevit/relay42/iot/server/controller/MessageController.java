package com.flexdevit.relay42.iot.server.controller;

import com.flexdevit.relay42.iot.server.mapper.dto.MessageDTOMapper;
import com.flexdevit.relay42.iot.server.model.dto.MessageResponse;
import com.flexdevit.relay42.iot.server.model.dto.StatisticsResponse;
import com.flexdevit.relay42.iot.server.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageDTOMapper MAPPER = Mappers.getMapper(MessageDTOMapper.class);

    @Operation( summary = "Find messages", description = "Get stored messages" )
    @GetMapping
    @RolesAllowed( {"ADMIN","USER"} )
    public ResponseEntity<Page<MessageResponse>> getMessages(
            @RequestParam(value = "start", required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "end", required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "sort", required = false, defaultValue = "timestamp") String sortField,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") String sortDirection
    ) {
        return ResponseEntity.ok(
                messageService.findAll(startDate, endDate, size, pageNumber, sortField, sortDirection).map(MAPPER::toRelayResponse));
    }

    @Operation( summary = "Find statistics by serial number", description = "statistics" )
    @GetMapping("/statistics")
    @RolesAllowed( {"ADMIN"} )
    public ResponseEntity<List<StatisticsResponse>> getStatistics(
    ) {
        return ResponseEntity.ok(
                messageService.findAllStatistics());
    }

    @Operation( summary = "Find messages by serial number", description = "Get stored messages by serial number" )
    @GetMapping("/{serialNumber}")
    @RolesAllowed( {"ADMIN","USER"} )
    public ResponseEntity<Page<MessageResponse>> getMessagesBySerialNumber(
            @PathVariable("serialNumber") String serialNumber,
            @RequestParam(value = "start", required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "end", required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "sort", required = false, defaultValue = "timestamp") String sortField,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") String sortDirection
    ) {
        return ResponseEntity.ok(
                messageService.findAllBySerialNumber(serialNumber, startDate, endDate, size, pageNumber, sortField, sortDirection).map(MAPPER::toRelayResponse));
    }


    @Operation( summary = "Find statistics by serial number", description = "statistics" )
    @GetMapping("/{serialNumber}/statistics")
    @RolesAllowed( {"ADMIN"} )
    public ResponseEntity<List<StatisticsResponse>> getStatisticsBySerialNumber(
            @PathVariable("serialNumber") String serialNumber
    ) {
        return ResponseEntity.ok(
                messageService.findAllBySerialNumberStatistics(serialNumber));
    }
}
