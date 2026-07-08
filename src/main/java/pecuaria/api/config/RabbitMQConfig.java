package pecuaria.api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "sensor_exchange";
    public static final String QUEUE_NAME = "fila_pesagem_core";
    
    public static final String ROUTING_KEY_PATTERN = "sensor.pesagem.#";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        // É aqui que definimos o interesse (Topic) desta fila
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_PATTERN);
    }
}