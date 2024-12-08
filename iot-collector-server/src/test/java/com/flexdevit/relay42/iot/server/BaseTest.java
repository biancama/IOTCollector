package com.flexdevit.relay42.iot.server;

import com.flexdevit.relay42.iot.server.mapper.mongo.MongoEntityMapper;
import com.flexdevit.relay42.iot.server.model.RelayMessageModel;
import com.flexdevit.relay42.iot.server.model.mongo.RelayMessageEventEntity;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.flexdevit.relay42.iot.server.message.SENSOR_TYPE.*;
import static com.flexdevit.relay42.iot.server.message.UNIT_OF_MEASURE.*;

public class BaseTest {
    private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static MongoEntityMapper MAPPER = Mappers.getMapper(MongoEntityMapper.class);

    public static List<RelayMessageEventEntity> getRelayMessageEvents() {
        var messages = List.of(
                RelayMessageEventEntity.builder().id("1").serialNumber("serial1").sensorType(THERMOSTAT).unitOfMeasure(CELSIUS).timestamp(LocalDateTime.parse("2024-12-07T15:11:16", format)).value(12.43).build(),
                RelayMessageEventEntity.builder().id("2").serialNumber("serial2").sensorType(HEART_RATE_SENSOR).unitOfMeasure(BPM).timestamp(LocalDateTime.parse("2024-12-07T15:14:10", format)).value(80.0).build(),
                RelayMessageEventEntity.builder().id("3").serialNumber("serial3").sensorType(CAR_FUEL_SENSOR).unitOfMeasure(LITER).timestamp(LocalDateTime.parse("2024-12-07T15:15:16", format)).value(22.56).build(),
                RelayMessageEventEntity.builder().id("4").serialNumber("serial1").sensorType(THERMOSTAT).unitOfMeasure(CELSIUS).timestamp(LocalDateTime.parse("2024-12-07T15:11:17", format)).value(15.43).build(),
                RelayMessageEventEntity.builder().id("5").serialNumber("serial2").sensorType(HEART_RATE_SENSOR).unitOfMeasure(BPM).timestamp(LocalDateTime.parse("2024-12-07T15:20:44", format)).value(90.0).build(),
                RelayMessageEventEntity.builder().id("6").serialNumber("serial3").sensorType(CAR_FUEL_SENSOR).unitOfMeasure(LITER).timestamp(LocalDateTime.parse("2024-12-07T15:22:56", format)).value(12.54).build()
        );
        return new ArrayList<>(messages);
    }

    public static List<RelayMessageEventEntity> getRelayMessageEventsWithoutId() {
        return (List<RelayMessageEventEntity>) getRelayMessageEvents().stream().map(m -> // setId is not present
                RelayMessageEventEntity.builder().serialNumber(m.getSerialNumber())
                        .sensorType(m.getSensorType()).unitOfMeasure(m.getUnitOfMeasure())
                        .timestamp(m.getTimestamp()).value(m.getValue()).build()).toList();
    }

    public static List<RelayMessageModel> getRelayMessages() {
        return getRelayMessageEvents().stream().map(MAPPER::toRelayMessageModel).toList();
    }
}
