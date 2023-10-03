package com.intuit.demo.vehiclerealtimedatareceiver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.demo.vehiclerealtimedatareceiver.service.NotificationService;
import com.intuit.demo.vehiclerealtimedatareceiver.service.RegisteredVehicleService;
import com.intuit.demo.vehiclerealtimedatareceiver.service.dto.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

@Service
@Slf4j
public class MqttNotificationService implements NotificationService<Void> {

    @Value("${mqtt.topic}")
    private String topic;
    private final MqttClient mqttClient;
    private final RegisteredVehicleService registeredVehicleService;

    public MqttNotificationService(MqttClient mqttClient, RegisteredVehicleService registeredVehicleService) {
        this.mqttClient = mqttClient;
        this.registeredVehicleService = registeredVehicleService;
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

        log.info("initializing the mqtt consumer ... ");

        mqttClient.subscribe(topic, new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                var v = new ObjectMapper().readValue(message.getPayload(), Vehicle.class);
                log.info("vehicle event received {}", v);
                registeredVehicleService.save(v);
            }
        });

        return null;
    }
}
