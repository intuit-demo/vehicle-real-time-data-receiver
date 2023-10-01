package com.intuit.demo.vehiclerealtimedatareceiver.common.configs;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Slf4j
public class MqttConfigs {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.topic}")
    private String topic;

    @Bean("mqttClient")
    public MqttClient mqttClient() throws MqttException {
        MqttClient client = new MqttClient(brokerUrl, "test-2");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setMaxInflight(1000);
        client.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable throwable) {
                try {
                    client.connect(options);
                } catch (MqttException e) {
                    log.error("exception at connectionLost",e);
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                log.info("message messageArrived {}", s);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        client.connect(options);
        return client;
    }
}
