package dtu.ws.messagingutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws.exception.NotEnoughMoneyException;
import dtu.ws.exception.TokenValidationException;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.model.Event;
import dtu.ws.model.EventType;
import dtu.ws.model.Payment;
import dtu.ws.model.Token;
import dtu.ws.services.IPaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Listener {
    private IPaymentService paymentService;
    private ObjectMapper mapper;

    @Autowired
    public Listener(IPaymentService paymentService, ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @RabbitListener(queues = {RabbitMQValues.PAYMENT_SERVICE_QUEUE_NAME})
    public void receiveEvent(Event event) {
        if (event.getType().equals(EventType.PAYMENT_REQUEST)) {
            Payment payment = mapper.convertValue(event.getObject(), Payment.class);
            System.out.println("Pay manager recieved " + event.getType()
                    + "\nWith the following values:"
                    + "\nFrom: " + payment.getFromAccountNumber()
                    + "\nTo: " + payment.getToAccountNumber()
                    + "\nAmount: " + payment.getAmount()
                    + "\nDescription: " + payment.getDescription());
            try {
                paymentService.performPayment(payment.getFromAccountNumber(),payment.getToAccountNumber(), payment.getAmount(),payment.getDescription(), new Token());
            } catch (BankServiceException_Exception e) {
                e.printStackTrace();
            } catch (TokenValidationException e) {
                e.printStackTrace();
            } catch (NotEnoughMoneyException e) {
                e.printStackTrace();
            }
        }
        if (event.getType().equals(EventType.TOKEN_VALIDATED)) {

        }
    }

}
