package com.producer.kafka_producer.service;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaChangesHandler implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaChangesHandler.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public KafkaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() {
        LOGGER.info("Connection to Event Source opened.");
    }

    @Override
    public void onClosed() {
        LOGGER.info("Connection to Event Source closed.");
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        LOGGER.info("Sending event to Kafka: {}", messageEvent.getData());
        kafkaTemplate.send(topic, messageEvent.getData());
    }

    @Override
    public void onComment(String comment) {
        LOGGER.debug("Comment received -> {}", comment);
    }

    @Override
    public void onError(Throwable t) {
        LOGGER.error("Error in Event Source stream", t);
    }
}
