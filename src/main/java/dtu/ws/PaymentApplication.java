package dtu.ws;

import dtu.ws.database.ITransactionDatabase;
import dtu.ws.database.InMemoryTransactionDatabase;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.Transaction;
import dtu.ws.messagingutils.EventReceiverImpl;
import dtu.ws.messagingutils.EventSenderImpl;
import dtu.ws.messagingutils.IEventReceiver;
import dtu.ws.messagingutils.IEventSender;
import dtu.ws.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
public class PaymentApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(PaymentApplication.class, args);

        new PaymentApplication().startUp();
    }

    private void startUp() throws Exception {
        IEventSender eventSender = new EventSenderImpl();

        ITransactionDatabase transactionDatabase = new InMemoryTransactionDatabase();
        ITransactionService transactionService = new TransactionService(transactionDatabase);

        BankService bankService = new BankServiceService().getBankServicePort();

        IPaymentService paymentService = new PaymentService(transactionService, bankService);

        IEventReceiver eventReceiver = new EventManager(eventSender, paymentService, transactionService);

        new EventReceiverImpl(eventReceiver).listen();
    }


//    @RabbitListener(queues = {RabbitMQValues.PAYMENT_SERVICE_QUEUE_NAME})
//    public void receiveEvent(Event event) {
//        if (event.getType().equals(EventType.MONEY_TRANSFER_REQUEST)) {
//            PaymentRequest paymentRequest = mapper.convertValue(event.getObject(), PaymentRequest.class);
//
//            try {
//                this.paymentService.performPayment(
//                        paymentRequest.getFromAccountNumber(),
//                        paymentRequest.getToAccountNumber(),
//                        paymentRequest.getAmount(),
//                        paymentRequest.getDescription(),
//                        paymentRequest.getToken());
//            } catch (BankServiceException_Exception e) {
//                Event failureResponse = new Event(EventType.MONEY_TRANSFER_FAILED, e.getMessage());
//                this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, failureResponse);
//                return;
//            }
//
//            Event successResponse = new Event(EventType.MONEY_TRANSFER_SUCCEED, "Money transfer succeed");
//            this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, successResponse);
//        }
//        else if (event.getType().equals(EventType.REQUEST_TRANSACTIONS)) {
//
//            String accountId = mapper.convertValue(event.getObject(), String.class);
//
//            ArrayList<DTUPayTransaction> transactions = this.transactionService.getTransactionsByAccountId(accountId);
//
//            Event responseEvent = new Event(EventType.REQUEST_TRANSACTIONS_RESPONSE, transactions);
//
//            this.rabbitTemplate.convertAndSend(
//                    RabbitMQValues.TOPIC_EXCHANGE_NAME,
//                    RabbitMQValues.REPORTING_SERVICE_ROUTING_KEY,
//                    responseEvent);
//        }
//    }
}
