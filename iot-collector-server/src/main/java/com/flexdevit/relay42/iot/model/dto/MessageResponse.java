package com.flexdevit.relay42.iot.model.dto;

import com.flexdevit.relay42.iot.message.SENSOR_TYPE;
import com.flexdevit.relay42.iot.message.UNIT_OF_MEASURE;

import java.time.LocalDateTime;

public record MessageResponse(String serialNumber, LocalDateTime timestamp, SENSOR_TYPE sensorType,
                              UNIT_OF_MEASURE unitOfMeasure, double value) {
}
