package dtu.ws.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.messagingutils.IEventReceiver;
import dtu.ws.messagingutils.IEventSender;
import dtu.ws.messagingutils.RabbitMQValues;
import dtu.ws.model.DTUPayTransaction;
import dtu.ws.model.Event;
import dtu.ws.model.EventType;
import dtu.ws.model.PaymentRequest;

import java.util.ArrayList;

public class EventManager implements IEventReceiver {

    private IEventSender eventSender;
    private IPaymentService paymentService;
    private ITransactionService transactionService;
    private ObjectMapper mapper;

    public EventManager(IEventSender eventSender, IPaymentService paymentService, ITransactionService transactionService) {
        this.eventSender = eventSender;
        this.paymentService = paymentService;
        this.transactionService = transactionService;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void receiveEvent(Event event) throws Exception {

        System.out.println("Jeg modtog evenet: " + event.getType());

        switch (event.getType()) {
            case REQUEST_TRANSACTIONS:
                String accountId = mapper.convertValue(event.getObject(), String.class);

                ArrayList<DTUPayTransaction> transactions = this.transactionService.getTransactionsByAccountId(accountId);

                Event responseEvent = new Event(EventType.REQUEST_TRANSACTIONS_RESPONSE, transactions, RabbitMQValues.REPORTING_SERVICE_ROUTING_KEY);

                this.eventSender.sendEvent(responseEvent);
                break;

            case MONEY_TRANSFER_REQUEST:
                PaymentRequest paymentRequest = mapper.convertValue(event.getObject(), PaymentRequest.class);

                try {
                    this.paymentService.performPayment(paymentRequest);
                } catch (BankServiceException_Exception e) {
                    Event failureResponse = new Event(EventType.MONEY_TRANSFER_FAILED, e.getMessage(), RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                    this.eventSender.sendEvent(failureResponse);
                    return;
                }

                Event successResponse = new Event(EventType.MONEY_TRANSFER_SUCCEED, "Money transfer succeed", RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                this.eventSender.sendEvent(successResponse);
                break;

            case REFUND_REQUEST:
                DTUPayTransaction transaction = mapper.convertValue(event.getObject(), DTUPayTransaction.class);

                try {
                    this.paymentService.performRefund(transaction);
                } catch (BankServiceException_Exception e) {
                    Event failureResponse = new Event(EventType.REFUND_FAILED, e.getMessage(), RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                    this.eventSender.sendEvent(failureResponse);
                    return;
                }

                Event success = new Event(EventType.REFUND_SUCCEED, "The refund succeed", RabbitMQValues.DTU_SERVICE_ROUTING_KEY);
                this.eventSender.sendEvent(success);
                break;
        }
    }
}
