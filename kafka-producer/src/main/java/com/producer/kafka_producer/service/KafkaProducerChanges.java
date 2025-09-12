package com.producer.kafka_producer.service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;

@Service
public class KafkaProducerChanges {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerChanges.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName = "wikimedia_recentchange";

    public KafkaProducerChanges(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        EventHandler eventHandler = new KafkaChangesHandler(kafkaTemplate, topicName);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        this.kafkaTemplate.send(topicName, "studentData", "Pankaj shah");
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));

        EventSource eventSource = builder.build();
        eventSource.start();

        TimeUnit.MINUTES.sleep(10);
    }
}
