package com.flexdevit.relay42.iot.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RelayMessage {
    private String serialNumber;
    private double value;
}
