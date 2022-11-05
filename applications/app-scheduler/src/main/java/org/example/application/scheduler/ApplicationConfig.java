package org.example.application.scheduler;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.example.generic.GsonEventSerializer;
import org.example.generic.business.EventPublisher;
import org.example.generic.business.EventSerializer;
import org.example.generic.business.EventStoreRepository;
import org.example.generic.business.IntegrationHandle;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

import javax.annotation.PostConstruct;


@Configuration
@ComponentScan(value = "org.example.business",
        useDefaultFilters = false, includeFilters = @ComponentScan.Filter
        (type = FilterType.REGEX, pattern = ".*ROIUseCase")
)
public class ApplicationConfig {

    private final AmqpAdmin amqpAdmin;
    private final ConfigProperties configProperties;

    public ApplicationConfig(AmqpAdmin amqpAdmin, ConfigProperties configProperties) {
        this.amqpAdmin = amqpAdmin;
        this.configProperties = configProperties;
    }

    @PostConstruct
    public void init() {
        var exchange = new TopicExchange(configProperties.getExchange());
        var queue = new Queue(configProperties.getQueue(), false, false, true);
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(configProperties.getRoutingKey()));
    }

    @Bean
    public Mono<Connection> connectionMono(@Value("spring.application.name") String name) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.useNio();
        return Mono.fromCallable(() -> connectionFactory.newConnection(name)).cache();
    }

    @Bean
    public SenderOptions senderOptions(Mono<Connection> connectionMono) {
        return new SenderOptions()
                .connectionMono(connectionMono)
                .resourceManagementScheduler(Schedulers.boundedElastic());
    }

    @Bean
    public Sender sender(SenderOptions senderOptions) {
        return RabbitFlux.createSender(senderOptions);
    }

    @Bean
    public IntegrationHandle integrationHandle(EventStoreRepository repository, EventPublisher eventPublisher, EventSerializer eventSerializer) {
        return new IntegrationHandle(configProperties.getStoreName(), repository, eventPublisher, eventSerializer);
    }

    @Bean
    public GsonEventSerializer gsonEventSerializer() {
        return new GsonEventSerializer();
    }

}

