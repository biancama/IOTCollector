package com.flexdevit.relay42.iot.sensor.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class SensorMqttClient extends MqttClient {

    public SensorMqttClient(String serverURI, String clientId) throws MqttException {
        super(serverURI, clientId);
    }

    public SensorMqttClient(String hostname, int port, String clientId) throws MqttException {
        this(String.format("tcp://%s:%s", hostname, port), clientId);
    }

}
