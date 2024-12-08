package com.flexdevit.relay42.iot.sensor.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public abstract class EngineSensor extends TimerTask {

    private static final Logger LOG = LoggerFactory.getLogger(EngineSensor.class);

    protected final IMqttClient client;
    protected final ObjectMapper mapper = new ObjectMapper();
    protected final String serialNumber;
    private final String topic;

    public long getPeriod() {
        return period;
    }

    private final long period;


    public EngineSensor(IMqttClient client, String serialNumber, String topic, long period) {
        this.client = client;
        this.serialNumber = serialNumber;
        this.topic = topic;
        this.period = period;
    }
    /**
     * This method simulates reading the sensor meter
     * @return
     */
    public abstract MqttMessage readValue() throws JsonProcessingException;


    @Override
    public void run() {
        if ( !client.isConnected()) {
            LOG.info("[I01] Client not connected.");
            return;
        }

        MqttMessage msg = null;
        try {
            msg = readValue();
        } catch (JsonProcessingException e) {
            LOG.error("[E01] Message could not be read: ", e.getMessage() );
        }
        msg.setQos(0);
        msg.setRetained(true);
        try {
            client.publish(topic, msg);
        } catch (MqttException e) {
            LOG.error("[E02] Message could not be published: ", e.getMessage() );
        }
    }
}
