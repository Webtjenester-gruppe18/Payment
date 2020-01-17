package dtu.ws.rabbitmq;

import dtu.ws.PaymentApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
/* <<< from https://github.com/spring-guides/gs-messaging-rabbitmq>>>*/

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(PaymentApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
    }

}
