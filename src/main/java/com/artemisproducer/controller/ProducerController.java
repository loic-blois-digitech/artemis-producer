package com.artemisproducer.controller;

import com.artemisproducer.service.ArtemisProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ProducerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerController.class);
    private ArtemisProducer artemisProducer;

    public ProducerController(ArtemisProducer artemisProducer) {
        this.artemisProducer = artemisProducer;
    }

    @PostMapping("/send-message")
    public ResponseEntity<String> send(@RequestBody String message) {
        LOGGER.info("Récupération du message pour envoie au broker Artemis.");
        this.artemisProducer.sendMessage(message);

        LOGGER.info("Message envoyé: " + message +".");
        return new ResponseEntity<>("Message envoyé: " + message + ".", HttpStatus.OK);
    }
}
