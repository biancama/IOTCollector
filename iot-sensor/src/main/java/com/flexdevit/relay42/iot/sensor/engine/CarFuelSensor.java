package com.flexdevit.relay42.iot.sensor.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flexdevit.relay42.iot.sensor.message.RelayMessage;
import com.flexdevit.relay42.iot.sensor.message.SENSOR_TYPE;
import com.flexdevit.relay42.iot.sensor.message.UNIT_OF_MEASURE;
import com.flexdevit.relay42.iot.sensor.util.RandomDataGenerator;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class CarFuelSensor extends EngineSensor {
    public CarFuelSensor(IMqttClient client, String serialNumber, String topic, long period) {
        super(client, serialNumber, topic, period);
    }

    @Override
    public MqttMessage readValue() throws JsonProcessingException {
        var valueRandom = RandomDataGenerator.nextDouble(0.0, 60.0);

        var messageRelay = RelayMessage.builder()
                .sensorType(SENSOR_TYPE.CAR_FUEL_SENSOR)
                .unitOfMeasure(UNIT_OF_MEASURE.LITER)
                .serialNumber(serialNumber)
                .value(valueRandom)
                .build();
        var payload = mapper.writeValueAsBytes(messageRelay);

        MqttMessage msg = new MqttMessage(payload);
        return msg;
    }
}
