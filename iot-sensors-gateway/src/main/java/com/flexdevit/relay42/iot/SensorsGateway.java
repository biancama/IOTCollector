package com.flexdevit.relay42.iot;

import com.flexdevit.relay42.iot.message.RelayMessage;
import com.flexdevit.relay42.iot.message.SENSOR_TYPE;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

import static com.flexdevit.relay42.iot.message.SENSOR_TYPE.THERMOSTAT;
import static com.flexdevit.relay42.iot.message.UNIT_OF_MEASURE.CELSIUS;

@SpringBootApplication
@IntegrationComponentScan
public class SensorsGateway {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(SensorsGateway.class)
                        .web(WebApplicationType.NONE)
                        .run(args);
        var gateway = context.getBean(MyGateway.class);
        Jackson2JsonObjectMapper mapper = new Jackson2JsonObjectMapper();
        var valueRandom = ThreadLocalRandom.current().nextDouble(70.0, 72.0);
        var value = new BigDecimal(Double.valueOf(valueRandom).toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();

        var message = new RelayMessage("serial1", THERMOSTAT, CELSIUS, value);
        try {
            var data = mapper.toJson(message);
            gateway.sendToMqtt(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }




    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MyGateway {

        void sendToMqtt(String data);

    }
}
