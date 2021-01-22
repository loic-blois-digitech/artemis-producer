package com.artemisproducer.config;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.JMSException;

/**
 * Classe permettant la connexion à un broker
 */
@Component
public class ConnectionBrokerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionBrokerConfig.class);

    private Connection connection;

    @Value("${broker.protocol}")
    private String protocol;

    @Value("${broker.host}")
    private String host;

    @Value("${broker.port}")
    private String port;

    @Value("${broker.user}")
    private String user;

    @Value("${broker.password}")
    private String password;

    /**
     * Connexion au broker d'Artémis avec en params:
     * - URL
     * - USER
     * - PASSWORD
     *
     * @return connection
     */
    @PostConstruct
    public Connection connectionBroker() {
        if(this.connection == null) {
            LOGGER.info("Producer non connecté.");
            LOGGER.info("Création de l'url pour se connecter au broker.");
            String uri = protocol + host + port;

            try {
                LOGGER.info("Connexion au broker en cours...");
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                connectionFactory.setBrokerURL(uri);
                connectionFactory.setUser(this.user);
                connectionFactory.setPassword(this.password);
                this.connection = connectionFactory.createConnection();

            } catch (JMSException e) {
                LOGGER.error(e.getErrorCode() + ": " + e.getMessage());
            }
            LOGGER.info("Producer connecté au broker.");
            return this.connection;
        }

        LOGGER.info("Producer déjà connecté au broker.");
        return connection;
    }
}
