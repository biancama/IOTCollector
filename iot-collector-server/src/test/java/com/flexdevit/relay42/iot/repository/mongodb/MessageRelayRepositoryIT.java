package com.flexdevit.relay42.iot.repository.mongodb;

import com.flexdevit.relay42.iot.BaseTest;
import com.flexdevit.relay42.iot.model.mongo.RelayMessageEventEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class MessageRelayRepositoryIT extends AbstractBaseIntegrationTest {
    @Autowired
    private MessageRelayRepository messageRelayRepository;

    @Test
    @DisplayName("When there are message stored, should return list")
    void listMessages() {
        var messages = BaseTest.getRelayMessageEventsWithoutId();
        messages.forEach(m -> messageRelayRepository.save(m));

        var sort = Sort.by(Sort.Direction.ASC, "timestamp");
        var pageRequest = PageRequest.of(0, 10, sort);
        var messagesResult = messageRelayRepository.findAll(pageRequest);
        var modifiableMessages = new ArrayList<>(messages);
        modifiableMessages.sort(Comparator.comparing(RelayMessageEventEntity::getTimestamp));
        assertThat(messagesResult).usingElementComparatorIgnoringFields("id").containsAll(modifiableMessages);


    }

}
