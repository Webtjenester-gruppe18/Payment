package dtu.ws.messagingutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws.exception.NotEnoughMoneyException;
import dtu.ws.exception.TokenValidationException;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.model.*;
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
        if (event.getType().equals(EventType.MONEY_TRANSFER_REQUEST)) {
            PaymentRequest paymentRequest = mapper.convertValue(event.getObject(), PaymentRequest.class);

            try {
                this.paymentService.performPayment(
                        paymentRequest.getFromAccountNumber(),
                        paymentRequest.getToAccountNumber(),
                        paymentRequest.getAmount(),
                        paymentRequest.getDescription(),
                        paymentRequest.getToken());
            } catch (BankServiceException_Exception e) {
                e.printStackTrace();
            } catch (TokenValidationException e) {
                e.printStackTrace();
            } catch (NotEnoughMoneyException e) {
                e.printStackTrace();
            }
        }
    }

}
