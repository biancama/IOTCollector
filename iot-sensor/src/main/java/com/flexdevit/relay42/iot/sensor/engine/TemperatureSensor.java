package com.flexdevit.relay42.iot.sensor.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flexdevit.relay42.iot.message.RelayMessage;
import com.flexdevit.relay42.iot.message.SENSOR_TYPE;
import com.flexdevit.relay42.iot.message.UNIT_OF_MEASURE;
import com.flexdevit.relay42.iot.sensor.util.RandomDataGenerator;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TemperatureSensor extends EngineSensor {
    public TemperatureSensor(IMqttClient client, String serialNumber, String topic, long period) {
        super(client, serialNumber, topic, period);
    }

    @Override
    public MqttMessage readValue() throws JsonProcessingException {
        var valueRandom = RandomDataGenerator.nextDouble(10.0, 35.0);

        var messageRelay = RelayMessage.builder()
                .sensorType(SENSOR_TYPE.THERMOSTAT)
                .unitOfMeasure(UNIT_OF_MEASURE.CELSIUS)
                .serialNumber(serialNumber)
                .value(valueRandom)
                .build();
        var payload = mapper.writeValueAsBytes(messageRelay);

        MqttMessage msg = new MqttMessage(payload);
        return msg;
    }
}
