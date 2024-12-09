package com.flexdevit.relay42.iot.server.repository.mongodb;

import com.flexdevit.relay42.iot.server.BaseTest;
import com.flexdevit.relay42.iot.server.model.mongo.RelayMessageEventEntity;
import com.flexdevit.relay42.iot.server.model.mongo.Statistics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class MessageRelayRepositoryIT extends AbstractBaseIntegrationTest {
    private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Autowired
    private MessageRelayRepository messageRelayRepository;

    @Test
    @DisplayName("When there are message stored, should return list")
    void listMessages() {
        messageRelayRepository.deleteAll();
        var messages = BaseTest.getRelayMessageEventsWithoutId();
        messages.forEach(m -> messageRelayRepository.save(m));

        var sort = Sort.by(Sort.Direction.ASC, "timestamp");
        var pageRequest = PageRequest.of(0, 10, sort);
        var messagesResult = messageRelayRepository.findAll(pageRequest);
        var modifiableMessages = new ArrayList<>(messages);
        modifiableMessages.sort(Comparator.comparing(RelayMessageEventEntity::getTimestamp));
        assertThat(messagesResult).usingElementComparatorIgnoringFields("id").containsAll(modifiableMessages);
    }

    @Test
    @DisplayName("When there are message stored, and timeframe is set then should return list")
    void listMessagesWithTimeframe() {
        messageRelayRepository.deleteAll();
        var messages = BaseTest.getRelayMessageEventsWithoutId();
        messages.forEach(m -> messageRelayRepository.save(m));

        var sort = Sort.by(Sort.Direction.ASC, "timestamp");
        var pageRequest = PageRequest.of(0, 10, sort);
        var messagesResult = messageRelayRepository.findAllByTimestampBetween(LocalDateTime.parse("2022-12-07T15:11:16", format), LocalDateTime.parse("2025-12-07T15:11:16", format), pageRequest);
        var modifiableMessages = new ArrayList<>(messages);
        modifiableMessages.sort(Comparator.comparing(RelayMessageEventEntity::getTimestamp));
        assertThat(messagesResult).usingElementComparatorIgnoringFields("id").containsAll(modifiableMessages);
    }

    @Test
    @DisplayName("When there are message stored, and timeframe and serial number is set then should return list")
    void listMessagesBySerialWithTimeframe() {
        messageRelayRepository.deleteAll();
        var messages = BaseTest.getRelayMessageEventsWithoutId();
        messages.forEach(m -> messageRelayRepository.save(m));

        var sort = Sort.by(Sort.Direction.ASC, "timestamp");
        var pageRequest = PageRequest.of(0, 10, sort);
        var messagesResult = messageRelayRepository.findAllBySerialNumberAndTimestampBetween("serial1", LocalDateTime.parse("2022-12-07T15:11:16", format), LocalDateTime.parse("2025-12-07T15:11:16", format), pageRequest);
        var modifiableMessages = new ArrayList<>(messages.stream().filter(m -> m.getSerialNumber().equals("serial1")).toList());
        modifiableMessages.sort(Comparator.comparing(RelayMessageEventEntity::getTimestamp));
        assertThat(messagesResult).usingElementComparatorIgnoringFields("id").containsAll(modifiableMessages);
    }

    @Test
    @DisplayName("When there are message stored, should return list")
    void listStatistics() {
        messageRelayRepository.deleteAll();
        var messages = BaseTest.getRelayMessageEventsWithoutId();
        messages.forEach(m -> messageRelayRepository.save(m));

        var statisticsResult = messageRelayRepository.getStatistics().getMappedResults();
        var expectedStatistics = List.of(
                Statistics.builder()._id("serial1").average(13.93).median(12.43).min(12.43).max(15.43).build(),
                Statistics.builder()._id("serial2").average(85.0).median(80.0).min(80.0).max(90.0).build(),
                Statistics.builder()._id("serial3").average(17.54).median(12.54).min(12.54).max(22.54).build()
        );
        assertThat(statisticsResult).containsAll(expectedStatistics);
    }


    @Test
    @DisplayName("When there are message stored, should return list by serial number")
    void listStatisticsBySerialNumber() {
        messageRelayRepository.deleteAll();
        var messages = BaseTest.getRelayMessageEventsWithoutId();
        messages.forEach(m -> messageRelayRepository.save(m));

        var statisticsResult = messageRelayRepository.getStatistics("serial1").getMappedResults();
        var expectedStatistics = List.of(
                Statistics.builder()._id("serial1").average(13.93).median(12.43).min(12.43).max(15.43).build()
        );
        assertThat(statisticsResult).containsAll(expectedStatistics);
    }

}
