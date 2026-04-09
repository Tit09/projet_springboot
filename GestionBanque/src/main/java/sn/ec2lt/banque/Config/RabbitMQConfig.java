package sn.ec2lt.banque.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.transaction}")
    private String queueName;

    @Value("${rabbitmq.exchange.transaction}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key.transaction}")
    private String routingKey;

    /**
     * Créer la queue
     * durable = true : la queue survit au redémarrage de RabbitMQ
     */
    @Bean
    public Queue transactionQueue() {
        return new Queue(queueName, true);
    }

    /**
     * Créer l'exchange de type Topic
     * Topic permet le routage avec patterns (ex: transaction.*)
     */
    @Bean
    public TopicExchange transactionExchange() {
        return new TopicExchange(exchangeName);
    }

    /**
     * Lier la queue à l'exchange avec une routing key
     */
    @Bean
    public Binding binding(Queue transactionQueue, TopicExchange transactionExchange) {
        return BindingBuilder
                .bind(transactionQueue)
                .to(transactionExchange)
                .with(routingKey);
    }

    /**
     * Convertir les objets Java en JSON pour l'envoi
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Template RabbitMQ pour envoyer des messages
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
