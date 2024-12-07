package com.flexdevit.relay42.iot.model;

import com.flexdevit.relay42.iot.message.SENSOR_TYPE;
import com.flexdevit.relay42.iot.message.UNIT_OF_MEASURE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelayMessageModel {
    private LocalDateTime timestamp;
    private String serialNumber;
    private SENSOR_TYPE sensorType;
    private UNIT_OF_MEASURE unitOfMeasure;
    private double value;

}
