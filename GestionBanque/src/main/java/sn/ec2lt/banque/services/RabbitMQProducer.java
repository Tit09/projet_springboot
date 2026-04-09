package sn.ec2lt.banque.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sn.ec2lt.banque.DTO.TransacMessage;

@Service
public class RabbitMQProducer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.transaction}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key.transaction}")
    private String routingKey;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Envoyer un message de transaction à RabbitMQ
     */
    public void sendTransactionMessage(TransacMessage message) {
        logger.info("Envoi message à RabbitMQ: {}", message);

        rabbitTemplate.convertAndSend(
                exchangeName,
                routingKey,
                message
        );

        logger.info("Message envoyé avec succès");
    }

}
