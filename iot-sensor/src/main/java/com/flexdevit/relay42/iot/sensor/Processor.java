package com.flexdevit.relay42.iot.sensor;

import com.flexdevit.relay42.iot.sensor.engine.EngineSensor;
import com.flexdevit.relay42.iot.sensor.engine.TemperatureSensor;
import com.flexdevit.relay42.iot.sensor.mqtt.SensorMqttClient;
import com.flexdevit.relay42.iot.sensor.util.Function4;
import org.eclipse.paho.client.mqttv3.MqttClient;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "iot-sensor", mixinStandardHelpOptions = true, version = "1.0",
        description = "Run a iot sensor")
public class Processor implements Callable<Optional<EngineSensor>> {

    @CommandLine.Parameters(index = "0", description = "Device serial number")
    private String serialNumber;

    @CommandLine.Parameters(index = "1", description = "Publishing mqtt topic")
    private String topic;

    @CommandLine.Option(names = {"-t", "--type"}, description = "TEMP, HEART_RATE, FUEL_READER")
    private String type = "TEMP";

    @CommandLine.Option(names = {"-s", "--server-hostname"}, description = "mqtt broker hostname")
    private String hostname = "localhost";

    @CommandLine.Option(names = {"-p", "--server-port"}, description = "mqtt broker port")
    private int port = 1883;

    @CommandLine.Option(names = {"-f", "--period"}, description = "period in milliseconds")
    private int period = 10_000;

    private Map<String, Function4<MqttClient, String, String, Integer, EngineSensor>> sensors = new HashMap<>() {{
       put("TEMP", (publisher, serialNumber, topic, period) -> new TemperatureSensor(publisher, serialNumber, topic, period));
    }};

    @Override
    public Optional<EngineSensor> call() throws Exception {
        var publisherId = UUID.randomUUID().toString();
        var publisher = new SensorMqttClient(hostname, port, publisherId);
        publisher.connect();
        var sensorF = sensors.get(type);
        var sensor = sensorF.apply(publisher, serialNumber, topic, period);
        if (sensor == null) {
            return Optional.empty();
        }
        return Optional.of(sensor);
    }
}
