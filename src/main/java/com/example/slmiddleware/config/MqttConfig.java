package com.example.slmiddleware.config;

import com.example.slmiddleware.service.CommunicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class MqttConfig {

    private final CommunicationService comunicationService;
    private static final String BROKER_URL = "tcp://118.41.132.222:1883";
    private static final String MQTT_CLIENT_ID = MqttAsyncClient.generateClientId();
    private static final String TOPIC_FILTER = "homenet/Sensor1/#";
//    private static final String BROKER_URL = "tcp://192.168.1.101:1883";
//    private static final String MQTT_CLIENT_ID = MqttAsyncClient.generateClientId();
//    private static final String TOPIC_FILTER = "every";

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    void messageArrived(String topic, MqttMessage message) throws Exception{

    };

    @Bean
    public MessageProducer inboundChannel() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(BROKER_URL, MQTT_CLIENT_ID, TOPIC_FILTER);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler inboundMessageHandler() {
        return message -> {
            String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
            log.info("Topic:" + topic);
            log.info("Payload:" + message.getPayload());
            try {
                comunicationService.parsing(message.getPayload());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
                    };
    }

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory(){

        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] {BROKER_URL});
        options.setUserName("username");
        options.setPassword("1234".toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound(){
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler("testClient",mqttPahoClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(1);
        messageHandler.setDefaultTopic("testTopic");
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel(){
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MyGateway {
        void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
    }
}
