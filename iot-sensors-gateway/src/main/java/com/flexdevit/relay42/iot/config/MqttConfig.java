package com.flexdevit.relay42.iot.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.UUID;

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
    public String mqttClientId() {
        return "relay42-" + UUID.randomUUID().toString().replace("-", "");
    }


    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound(MqttPahoClientFactory mqttClientFactory, MqttProperties mqttProperties, @Qualifier("mqttClientId") String mqttClientId) {
        var messageHandler =
                new MqttPahoMessageHandler(mqttClientId, mqttClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttProperties.getTopic());
        return messageHandler;
    }
}
