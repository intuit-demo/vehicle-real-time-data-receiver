package com.intuit.demo.vehiclerealtimedatareceiver.service;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface NotificationService<T> {

    T consume() throws MqttException, InterruptedException;
}
