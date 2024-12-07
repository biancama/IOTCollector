package com.flexdevit.relay42.iot.model.mongo;

import com.flexdevit.relay42.iot.message.RelayMessageEvent;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "relay_messages")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
public class RelayMessageEventEntity extends RelayMessageEvent {
    @Id
    private String id;
}
