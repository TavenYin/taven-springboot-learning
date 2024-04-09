package com.github.taven.springboot3mqttv5;

import org.eclipse.paho.mqttv5.client.IMqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MqttDefaultFilePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.ClientManager;
import org.springframework.integration.mqtt.core.Mqttv5ClientManager;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.Mqttv5PahoMessageHandler;

import java.nio.charset.StandardCharsets;

@Configuration
public class MqttConfiguration {

    @Bean
    public ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager() {
        MqttConnectionOptions connectionOptions = new MqttConnectionOptions();
        connectionOptions.setServerURIs(new String[]{ "tcp://localhost:1883" });
        connectionOptions.setConnectionTimeout(30000);
        connectionOptions.setMaxReconnectDelay(1000);
        connectionOptions.setAutomaticReconnect(true);
        connectionOptions.setUserName("abc");
        connectionOptions.setPassword(JwtGenerator.generateJwt("abc").getBytes(StandardCharsets.UTF_8));
        Mqttv5ClientManager clientManager = new Mqttv5ClientManager(connectionOptions, "client-manager-client-id-v5");
        clientManager.setPersistence(new MqttDefaultFilePersistence());
        return clientManager;
    }

    @Bean
    public IntegrationFlow mqttInFlow(
            ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager) {

        Mqttv5PahoMessageDrivenChannelAdapter messageProducer =
                new Mqttv5PahoMessageDrivenChannelAdapter(clientManager, "testtopic/5");
        return IntegrationFlow.from(messageProducer)
                .handle(m -> System.out.println(new String((byte[]) m.getPayload()) ))
                .get();
    }

    @Bean
    public IntegrationFlow mqttOutFlow(
            ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager) {

        return f -> f.handle(new Mqttv5PahoMessageHandler(clientManager));
    }

}
