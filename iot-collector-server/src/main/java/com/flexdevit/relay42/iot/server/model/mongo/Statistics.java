package com.flexdevit.relay42.iot.server.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Statistics {
    private String _id;
    private Double average;
    private Double median;
    private Double max;
    private Double min;
}
