package ws18.Payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtu.ws.database.ITransactionDatabase;
import dtu.ws.database.InMemoryTransactionDatabase;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.messagingutils.IEventReceiver;
import dtu.ws.messagingutils.IEventSender;
import dtu.ws.model.*;
import dtu.ws.services.*;
import io.cucumber.java.en.*;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PaymentSteps {

    private IEventSender eventSender = mock(IEventSender.class);
    private BankService bankService = mock(BankService.class);
    private ITransactionDatabase database = new InMemoryTransactionDatabase();
    private ITransactionService transactionService = new TransactionService(database);
    private IPaymentService paymentService = new PaymentService(transactionService, bankService);
    private IEventReceiver eventReceiver = new EventManager(eventSender, paymentService, transactionService);

    private PaymentRequest paymentRequest;
    private DTUPayTransaction transaction;

    @When("the service receives the {string} event")
    public void theServiceReceivesTheEvent(String eventType) throws Exception {
        Event event = new Event();
        event.setType(EventType.valueOf(eventType));

        switch (event.getType()) {
            case MONEY_TRANSFER_REQUEST:
                paymentRequest = new PaymentRequest();
                paymentRequest.setAmount(BigDecimal.valueOf(100));
                paymentRequest.setCpr("1234");
                paymentRequest.setDescription("Test");
                paymentRequest.setFromAccountNumber("9876");
                paymentRequest.setToAccountNumber("4567");

                Token token = new Token("1234");
                paymentRequest.setToken(token);

                event.setObject(paymentRequest);
                break;
            case REQUEST_TRANSACTIONS:
                event.setObject("123456789");
                break;
            case REFUND_REQUEST:
                transaction = new DTUPayTransaction(BigDecimal.ONE, "9876", "4567", "TEST", new Date().getTime(), new Token());
                event.setObject(transaction);
                break;
        }

        this.eventReceiver.receiveEvent(event);
    }

    @Then("the money is transferred")
    public void theMoneyIsTransferred() throws BankServiceException_Exception {
        verify(bankService).transferMoneyFromTo(
                paymentRequest.getFromAccountNumber(),
                paymentRequest.getToAccountNumber(),
                paymentRequest.getAmount(),
                paymentRequest.getDescription());
    }

    @Then("the money is refunded")
    public void theMoneyIsRefunded() throws BankServiceException_Exception {
        verify(bankService).transferMoneyFromTo(
                transaction.getDebtor(),
                transaction.getCreditor(),
                transaction.getAmount(),
                transaction.getDescription());
    }

    @Then("the {string} is broadcast")
    public void theIsBroadcast(String eventType) throws Exception {
        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventSender, atLeastOnce()).sendEvent(argumentCaptor.capture());
        assertEquals(EventType.valueOf(eventType), argumentCaptor.getValue().getType());
    }

    @Then("the transactions are retrieved")
    public void theTransactionsAreRetrieved() {
        ArrayList<DTUPayTransaction> transactions = this.transactionService.getTransactionsByAccountId("123456789");
        assertEquals(0 , transactions.size());
    }

}
