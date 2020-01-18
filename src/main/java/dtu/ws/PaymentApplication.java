package dtu.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws.model.Event;
import dtu.ws.model.EventType;
import dtu.ws.model.Payment;
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
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public PaymentApplication(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = {queueName})
    public void receiveMessageFromPaymentQueue(Event event) {
        if (event.getType().equals(EventType.PAYMENT_REQUEST)) {
            Payment p = mapper.convertValue(event.getObject(), Payment.class);
            System.out.println("Pay manager recieved " + event.getType()
                    + "\nWith the following values:"
                    + "\nFrom: " + p.getFromAccountNumber()
                    + "\nTo: " + p.getToAccountNumber()
                    + "\nAmount: " + p.getAmount()
                    + "\nDescription: " + p.getDescription());
        }

        //  this.rabbitTemplate.convertAndSend(RabbitMQValues.topicExchangeName,"token",message);

    }

    public static void main(String[] args) {

        SpringApplication.run(PaymentApplication.class, args);

    }
}
