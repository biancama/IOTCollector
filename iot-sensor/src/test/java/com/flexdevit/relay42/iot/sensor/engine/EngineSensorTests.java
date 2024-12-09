package com.flexdevit.relay42.iot.sensor.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class EngineSensorTests {
    @Mock
    private IMqttClient client;

    @Mock
    private MqttMessage mqttMessage;

    @Test
    @DisplayName("When Engine sensor is running then read value method is called")
    void whenRunShouldCallReadValueMethod() throws MqttException {
        var objectUnderTest = new EngineSensor(client, "serial", "topic", 30) {
            @Override
            public MqttMessage readValue() throws JsonProcessingException {
                return mqttMessage;
            }
        };
        when(client.isConnected()).thenReturn(true);
        objectUnderTest.run();
        verify(client).publish("topic", mqttMessage);
    }
}
