package com.flexdevit.relay42.iot.server.model.dto;

public record StatisticsResponse(String serialNumber, Double average, Double median, Double min, Double max) {
}
