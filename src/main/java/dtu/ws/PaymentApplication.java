package dtu.ws;

import dtu.ws.exception.NotEnoughMoneyException;
import dtu.ws.exception.TokenValidationException;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.model.Token;
import dtu.ws.services.PaymentService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.math.BigDecimal;

@EnableSwagger2
@SpringBootApplication
public class PaymentApplication {
    private static RabbitTemplate rabbitTemplate;
    public PaymentApplication(RabbitTemplate rabbitTemplate ){
        PaymentApplication.rabbitTemplate = rabbitTemplate;

    }
    public static final String topicExchangeName = "dtupay-eventsExchange";

    public static final String queueName = "dtupay-queue";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }


    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("token");
    }

/*    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }*/

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
        PaymentService p = new PaymentService(rabbitTemplate);
        try {
            p.performPayment("2","23", BigDecimal.ONE,"D", new Token());
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        } catch (TokenValidationException e) {
            e.printStackTrace();
        } catch (NotEnoughMoneyException e) {
            e.printStackTrace();
        }
    }
}
