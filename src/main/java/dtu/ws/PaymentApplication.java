package dtu.ws;

import dtu.ws.rabbitmq.RabbitMQValues;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class PaymentApplication {
    private RabbitTemplate rabbitTemplate;
    private static final String queueName = "pay-queue";

    @Autowired
    public PaymentApplication(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = {queueName})
    public void receiveMessageFromPaymentQueue(String message) {
        System.out.println("Pay manager received message: " + message);
        this.rabbitTemplate.convertAndSend(RabbitMQValues.topicExchangeName,"token",message);

    }

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);

    }
}
