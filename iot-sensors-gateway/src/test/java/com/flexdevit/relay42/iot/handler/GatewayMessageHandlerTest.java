package com.flexdevit.relay42.iot.handler;

import com.flexdevit.relay42.iot.gateway.IoTGateway;
import com.flexdevit.relay42.iot.gateway.handler.GatewayMessageHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GatewayMessageHandlerTest {
    @Mock
    private IoTGateway gateway;

    @InjectMocks
    private GatewayMessageHandler handler;

    @Mock
    Message<String> message;
    @Test
    @DisplayName("When receive a message from a sensor then it is dispatched to the server")
    public void whenReceiveAMessageFromASensorThenItIsDispatchedToTheServer() {
        var messagePayload = """
                {"serialNumber":"serial3","sensorType":"CAR_FUEL_SENSOR","unitOfMeasure":"LITER","value":2.54}
                """;

        when(message.getPayload()).thenReturn(messagePayload);
        handler.handleMessage(message);

        verify(gateway).sendToMqtt(messagePayload);
    }
}
