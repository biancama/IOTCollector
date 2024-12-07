package com.flexdevit.relay42.iot.config;

import com.flexdevit.relay42.iot.handler.SensorMessageHandler;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

@Configuration
@EnableConfigurationProperties({
        MqttProperties.class
})
public class MqttConfig {

    @Bean
    public MqttPahoClientFactory mqttClientFactory(MqttProperties mqttProperties) {
        var options = new MqttConnectOptions();
        options.setServerURIs(new String[] {
                String.format("tcp://%s:%s", mqttProperties.getHostname(), mqttProperties.getPort())
        });

        if (mqttProperties.getUsername() != null && !mqttProperties.getUsername().isEmpty()) {
            options.setUserName(mqttProperties.getUsername());
        }

        if (mqttProperties.getPassword() != null && !mqttProperties.getPassword().isEmpty()) {
            options.setPassword(mqttProperties.getPassword().toCharArray());
        }

        var factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(options);

        return factory;
    }

    @Bean
    public IntegrationFlow mqttInbound(MqttPahoClientFactory mqttClientFactory, MqttProperties mqttProperties, SensorMessageHandler messageHandler) {
        return IntegrationFlow.from(
                        new MqttPahoMessageDrivenChannelAdapter("mqtt-service", mqttClientFactory, mqttProperties.getTopic()))
                .handle(messageHandler)
                .get();
    }
}
