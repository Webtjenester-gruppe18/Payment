package dtu.ws;

import dtu.ws.database.ITransactionDatabase;
import dtu.ws.database.InMemoryTransactionDatabase;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.Transaction;
import dtu.ws.messagingutils.*;
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
        IEventSender eventSender = new RabbitMqSender();

        ITransactionDatabase transactionDatabase = new InMemoryTransactionDatabase();
        ITransactionService transactionService = new TransactionService(transactionDatabase);

        BankService bankService = new BankServiceService().getBankServicePort();

        IPaymentService paymentService = new PaymentService(transactionService, bankService);

        IEventReceiver eventReceiver = new EventManager(eventSender, paymentService, transactionService);

        new RabbitMqListener(eventReceiver).listen();
    }
}
