package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.service.SiieKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/siie-kafka")
public class SiieKafkaResource {

    private final Logger log = LoggerFactory.getLogger(SiieKafkaResource.class);

    private SiieKafkaProducer kafkaProducer;

    public SiieKafkaResource(SiieKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
