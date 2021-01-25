package com.artemisproducer.service;

import com.artemisproducer.config.ConnectionBrokerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class ArtemisProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtemisProducer.class);

    // déclaration des variables qu'on a besoin
    @Value("${jms.topic.2}")
    private String topic2;
    private ConnectionBrokerConfig connectionBrokerConfig;

    // constructeur
    public ArtemisProducer(ConnectionBrokerConfig connectionBrokerConfig) {
        this.connectionBrokerConfig = connectionBrokerConfig;
    }

    /**
     * Envoie d'un message vers le broker Artémis par le producer
     * @param message
     */
    public void sendMessage(String message) {
        try {
            LOGGER.info("Recherche si le producer est déjà connecté au broker...");
            Connection connection = this.connectionBrokerConfig.connectionBroker();

            LOGGER.info("Création d'une session.");
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            LOGGER.info("Création d'une destination (Topic ou Queue).");
            Topic topic = session.createTopic(this.topic2);

            LOGGER.info("Démarrage de la connexion.");
            connection.start();

            LOGGER.info("Création d'un producer.");
            MessageProducer producer1 = session.createProducer(topic);

            LOGGER.info("Création d'un message.");
            TextMessage text = session.createTextMessage(message);

            LOGGER.info("Envoie du message par le producer: " + text.getText() + ".");
            producer1.send(text, DeliveryMode.PERSISTENT, Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);

        } catch (JMSException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
