package dtu.ws.messagingutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.model.*;
import dtu.ws.services.IPaymentService;
import dtu.ws.services.ITransactionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Listener {
    private IPaymentService paymentService;
    private ITransactionService transactionService;
    private ObjectMapper mapper;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public Listener(IPaymentService paymentService,
                    ObjectMapper mapper,
                    ITransactionService transactionService,
                    RabbitTemplate rabbitTemplate) {
        this.mapper = mapper;
        this.paymentService = paymentService;
        this.transactionService = transactionService;
        this.rabbitTemplate = rabbitTemplate;
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
                Event failureResponse = new Event(EventType.MONEY_TRANSFER_FAILED, e);
                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, failureResponse);
                return;
            }

            Event successResponse = new Event(EventType.MONEY_TRANSFER_SUCCEED, "Money transfer succeed");
            this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, successResponse);
        }
        else if (event.getType().equals(EventType.REQUEST_TRANSACTIONS)) {

            String accountId = mapper.convertValue(event.getObject(), String.class);

            ArrayList<DTUPayTransaction> transactions = this.transactionService.getTransactionsByAccountId(accountId);

            Event responseEvent = new Event(EventType.REQUEST_TRANSACTIONS_RESPONSE, transactions);

            this.rabbitTemplate.convertAndSend(
                    RabbitMQValues.TOPIC_EXCHANGE_NAME,
                    RabbitMQValues.REPORTING_SERVICE_ROUTING_KEY,
                    responseEvent);
        }
    }

}
