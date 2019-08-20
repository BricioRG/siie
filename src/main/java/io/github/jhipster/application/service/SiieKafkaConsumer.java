package io.github.jhipster.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SiieKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(SiieKafkaConsumer.class);
    private static final String TOPIC = "topic_siie";

    @KafkaListener(topics = "topic_siie", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
