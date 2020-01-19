package dtu.ws.services;

import dtu.ws.database.ITransactionDatabase;
import dtu.ws.exception.NotEnoughMoneyException;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.messagingutils.RabbitMQValues;
import dtu.ws.model.DTUPayTransaction;
import dtu.ws.model.Event;
import dtu.ws.model.EventType;
import dtu.ws.model.Token;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;


@Service
public class PaymentService implements IPaymentService {

    private BankService bankService; // = ControlReg.getFastMoneyBankService();
    private ITransactionDatabase transactionDatabase; // = ControlReg.getTransactionDatabase();
    private RabbitTemplate rabbitTemplate;

    public PaymentService (RabbitTemplate rabbitTemplate){
        this.bankService = new BankServiceService().getBankServicePort();
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void performPayment(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) {
        try {
            this.bankService.transferMoneyFromTo(fromAccountNumber, toAccountNumber, amount, description);

            // saving transaction at DTUPay
            DTUPayTransaction transaction = new DTUPayTransaction(amount, fromAccountNumber, toAccountNumber, description, new Date().getTime(), token);
            this.saveTransaction(transaction);

        } catch (BankServiceException_Exception e) {
            Event failureResponse = new Event(EventType.MONEY_TRANSFER_FAILED, e);
            this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, failureResponse);
        }

        Event successResponse = new Event(EventType.MONEY_TRANSFER_SUCCEED, "Money transfer succeed");
        this.rabbitTemplate.convertAndSend(RabbitMQValues.TOPIC_EXCHANGE_NAME, RabbitMQValues.DTU_SERVICE_ROUTING_KEY, successResponse);
    }

    @Override
    public boolean performRefund(DTUPayTransaction transaction) throws BankServiceException_Exception {

        this.bankService.transferMoneyFromTo(transaction.getDebtor(), transaction.getCreditor(), transaction.getAmount(), transaction.getDescription());

        DTUPayTransaction dtuPayTransaction =
                new DTUPayTransaction(
                        transaction.getAmount(),
                        transaction.getDebtor(),
                        transaction.getCreditor(),
                        transaction.getDescription() + "-> REFUND",
                        new Date().getTime(),
                        null);

        this.saveTransaction(dtuPayTransaction);
        return true;
    }

    @Override
    public DTUPayTransaction getTransactionById(String transactionId) {
        return this.transactionDatabase.getTransactionById(transactionId);
    }

    @Override
    public ArrayList<DTUPayTransaction> getTransactionsByCustomerCpr(String cpr) {

        ArrayList<DTUPayTransaction> result = new ArrayList<>();
/*
        Customer customer = this.userManagerHTTPClient.getCustomerByCpr(cpr);

        for (String transactionId : customer.getTransactionIds()) {
            result.add(this.getTransactionById(transactionId));
        }*/
        return result;
    }

    @Override
    public ArrayList<DTUPayTransaction> getTransactionsByMerchantCpr(String cpr) {

        ArrayList<DTUPayTransaction> result = new ArrayList<>();

     /*   Merchant merchant = this.userManagerHTTPClient.getMerchantByCpr(cpr);

        for (String transactionId : merchant.getTransactionIds()) {
            result.add(this.getTransactionById(transactionId));
        }*/

        return result;
    }

    @Override
    public String saveTransaction(DTUPayTransaction transaction) {
        return this.transactionDatabase.saveTransaction(transaction);
    }

    private boolean isPaymentPossible(Account account, BigDecimal requestedAmount) throws NotEnoughMoneyException {
        if (account.getBalance().compareTo(requestedAmount) != -1) {
            return true;
        }

        throw new NotEnoughMoneyException("You do not have enough money");
    }
}
