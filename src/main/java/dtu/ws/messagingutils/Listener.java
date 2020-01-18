package dtu.ws.messagingutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws.model.Event;
import dtu.ws.model.EventType;
import dtu.ws.model.Payment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Listener {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper mapper;

    @Autowired
    public Listener(RabbitTemplate rabbitTemplate, ObjectMapper mapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
    }

    @RabbitListener(queues = {RabbitMQValues.PAYMENT_SERVICE_QUEUE_NAME})
    public void receiveEvent(Event event) {
        if (event.getType().equals(EventType.PAYMENT_REQUEST)) {
            Payment p = mapper.convertValue(event.getObject(), Payment.class);
            System.out.println("Pay manager recieved " + event.getType()
                    + "\nWith the following values:"
                    + "\nFrom: " + p.getFromAccountNumber()
                    + "\nTo: " + p.getToAccountNumber()
                    + "\nAmount: " + p.getAmount()
                    + "\nDescription: " + p.getDescription());

        }
    }

}
