package ws18.Payment;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.messagingutils.IEventReceiver;
import dtu.ws.messagingutils.IEventSender;
import dtu.ws.model.Event;
import dtu.ws.model.EventType;
import dtu.ws.model.PaymentRequest;
import dtu.ws.model.Token;
import dtu.ws.services.*;
import io.cucumber.java.en.*;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PaymentSteps {

    private IEventSender eventSender = mock(IEventSender.class);
    private BankService bankService = mock(BankService.class);
    private IPaymentService paymentService = mock(PaymentService.class);
    private ITransactionService transactionService = mock(TransactionService.class);
    private IEventReceiver eventReceiver = new EventManager(eventSender, paymentService, transactionService);

    private PaymentRequest paymentRequest;

    @When("the service receives the {string} event")
    public void theServiceReceivesTheEvent(String eventType) throws Exception {
        Event event = new Event();
        event.setType(EventType.valueOf(eventType));

        paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(BigDecimal.valueOf(100));
        paymentRequest.setCpr("1234");
        paymentRequest.setDescription("Test");
        paymentRequest.setFromAccountNumber("9876");
        paymentRequest.setToAccountNumber("4567");

        Token token = new Token("1234");
        paymentRequest.setToken(token);

        event.setObject(paymentRequest);

        this.eventReceiver.receiveEvent(event);
    }

    @Then("the money is transferred")
    public void theMoneyIsTransferred() throws BankServiceException_Exception {
//        verify(paymentService).performPayment(
//                paymentRequest.getFromAccountNumber(),
//                paymentRequest.getToAccountNumber(),
//                paymentRequest.getAmount(),
//                paymentRequest.getDescription(),
//                paymentRequest.getToken());

//        verify(bankService).transferMoneyFromTo(
//                paymentRequest.getFromAccountNumber(),
//                paymentRequest.getToAccountNumber(),
//                paymentRequest.getAmount(),
//                paymentRequest.getDescription());
    }

    @Then("the {string} is broadcast")
    public void theIsBroadcast(String eventType) throws Exception {
        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventSender, atLeastOnce()).sendEvent(argumentCaptor.capture());
        assertEquals(EventType.valueOf(eventType), argumentCaptor.getValue().getType());
    }

}
