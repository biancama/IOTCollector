package com.flexdevit.relay42.iot.server.model.dto;

import com.flexdevit.relay42.iot.server.message.SENSOR_TYPE;
import com.flexdevit.relay42.iot.server.message.UNIT_OF_MEASURE;

import java.time.LocalDateTime;

public record MessageResponse(String serialNumber, LocalDateTime timestamp, SENSOR_TYPE sensorType,
                              UNIT_OF_MEASURE unitOfMeasure, double value) {
}
