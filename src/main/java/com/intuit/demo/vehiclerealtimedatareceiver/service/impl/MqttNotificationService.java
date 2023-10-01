package com.intuit.demo.vehiclerealtimedatareceiver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.demo.vehiclerealtimedatareceiver.service.NotificationService;
import com.intuit.demo.vehiclerealtimedatareceiver.service.dto.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MqttNotificationService implements NotificationService<Void> {

    @Value("${mqtt.topic}")
    private String topic;
    private final MqttClient mqttClient;

    public MqttNotificationService(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    private byte[] getBytes(Vehicle vehicle) {
        byte[] ret = new byte[0];
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(vehicle);
            ret =  byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("exception on converting to bytes",e);
        }
        return ret;
    }

    @Override
    @Scheduled(initialDelay = 10, fixedDelay = 999999999L)
    public Void consume() throws MqttException, InterruptedException {

        log.info("initialized the consumer ... ");

        //CountDownLatch receivedSignal = new CountDownLatch(10);

        mqttClient.subscribe(topic, new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                var v = new ObjectMapper().readValue(message.getPayload(), Vehicle.class);
                log.info("vehicle event received {}", v);
                //receivedSignal.countDown();
            }
        });

       // receivedSignal.await(1, TimeUnit.MINUTES);

        return null;
    }
}
