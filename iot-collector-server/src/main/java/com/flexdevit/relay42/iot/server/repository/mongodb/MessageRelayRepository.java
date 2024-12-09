package com.flexdevit.relay42.iot.server.repository.mongodb;

import com.flexdevit.relay42.iot.server.model.mongo.RelayMessageEventEntity;
import com.flexdevit.relay42.iot.server.model.mongo.Statistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface MessageRelayRepository extends MongoRepository<RelayMessageEventEntity, String> {
    Page<RelayMessageEventEntity> findAllBySerialNumber(String messageId, Pageable pageable);

    Page<RelayMessageEventEntity> findAllByTimestampBetween(LocalDateTime timestampAfter, LocalDateTime timestampBefore, Pageable pageable);

    Page<RelayMessageEventEntity> findAllBySerialNumberAndTimestampBetween(LocalDateTime startDate, LocalDateTime endDate, PageRequest pageable);


    @Aggregation(pipeline = {"""
        {
            $group:
                {
                    _id: "$serialNumber",
                    average: {  $avg: "$value" },
                    median: {
                        $median: {
                            input: "$value",
                            method: 'approximate'
                        }
                    },
                    min: {  $min: "$value" },
                    max: {  $max: "$value" }
                }
        }
       """})
    AggregationResults<Statistics> getStatistics();


    @Aggregation(pipeline = {
            "{'$match':{'serialNumber':?0 }}",
            """
            {
                    $group:
                        {
                            _id: '$serialNumber',
                            average: {  $avg: '$value' },
                            median: {
                                $median: {
                                    input: '$value',
                                    method: 'approximate'
                                }
                            },
                            min: {  $min: '$value' },
                            max: {  $max: '$value' }
                        }
                }
        """
    })
    AggregationResults<Statistics> getStatistics(String serialNumber);

}
