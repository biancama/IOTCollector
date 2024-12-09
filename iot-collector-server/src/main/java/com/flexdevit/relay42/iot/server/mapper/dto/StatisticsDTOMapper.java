package com.flexdevit.relay42.iot.server.mapper.dto;

import com.flexdevit.relay42.iot.server.model.dto.StatisticsResponse;
import com.flexdevit.relay42.iot.server.model.mongo.Statistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StatisticsDTOMapper {

    @Mapping(source = "_id", target = "serialNumber")
    StatisticsResponse toStatisticsResponse(Statistics statistics);

}
