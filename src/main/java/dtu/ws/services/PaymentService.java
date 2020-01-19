package dtu.ws.services;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class PaymentService implements IPaymentService {

    private BankService bankService;
    private RabbitTemplate rabbitTemplate;
    private ITransactionService transactionService;

    @Autowired
    public PaymentService (RabbitTemplate rabbitTemplate, ITransactionService transactionService){
        this.bankService = new BankServiceService().getBankServicePort();
        this.rabbitTemplate = rabbitTemplate;
        this.transactionService = transactionService;
    }

    @Override
    public void performPayment(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws BankServiceException_Exception {
        this.bankService.transferMoneyFromTo(fromAccountNumber, toAccountNumber, amount, description);

        // saving transaction at DTUPay
        DTUPayTransaction transaction = new DTUPayTransaction(amount, fromAccountNumber, toAccountNumber, description, new Date().getTime(), token);
        this.transactionService.saveTransaction(transaction);
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

        this.transactionService.saveTransaction(dtuPayTransaction);
        return true;
    }

    private boolean isPaymentPossible(Account account, BigDecimal requestedAmount) throws NotEnoughMoneyException {
        if (account.getBalance().compareTo(requestedAmount) != -1) {
            return true;
        }

        throw new NotEnoughMoneyException("You do not have enough money");
    }
}
