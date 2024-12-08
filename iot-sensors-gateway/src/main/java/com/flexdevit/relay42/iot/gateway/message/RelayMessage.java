package com.flexdevit.relay42.iot.gateway.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RelayMessage {
    private String serialNumber;
    private SENSOR_TYPE sensorType;
    private UNIT_OF_MEASURE unitOfMeasure;
    private double value;
}
