/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unideb.my_xbee_server;

import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.Future;

/**
 *
 * @author Jimmy
 */
public class XbeeServer {

    /**
     * @param args the command line arguments
     */
    public static MQTT mqtt = new MQTT();
    java.util.Date date= new java.util.Date();
    public static void main(String[] args) {                  

        try{
            mqtt.setHost("sensornetwork.inf.unideb.hu", 1883);
            mqtt.setClientId("Xbee");
            FutureConnection connection = mqtt.futureConnection();
            System.out.print("Connect Start.\n");

            connection.connect();
            System.out.print("Connect Done.\n");
            Future<byte[]> f2 = connection.subscribe(new Topic[]{new Topic("test", QoS.AT_LEAST_ONCE)});
            byte[] qoses = f2.await();
            Future<Message> receive = connection.receive();
            Future<Void> f3 = connection.publish("test", String.valueOf(System.currentTimeMillis()).getBytes(), QoS.AT_LEAST_ONCE, false);
            Message message = receive.await();
            message.ack();
            Future<Void> f4 = connection.disconnect();
            f4.await();
            System.out.print(message.getPayload() + " " + new String(message.getPayload()) + " " + message.getTopicBuffer() + " " + message.getTopic()  + "\n");


        }catch (Exception ex){
            System.out.print(ex);
        }


        //snMqtt.pubMqttDefaultTopic("Helloka".getBytes());
        
        //System.out.print("\n" + "Helloka".getBytes().toString() + "\n");
    
    }
}
