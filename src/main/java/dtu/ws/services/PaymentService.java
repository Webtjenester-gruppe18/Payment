package dtu.ws.services;

import dtu.ws.HTTPClients.TokenManagerHTTPClient;
import dtu.ws.HTTPClients.UserManagerHTTPClient;
import dtu.ws.PaymentApplication;
import dtu.ws.database.ITransactionDatabase;
import dtu.ws.exception.NotEnoughMoneyException;
import dtu.ws.exception.TokenValidationException;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.model.Customer;
import dtu.ws.model.DTUPayTransaction;
import dtu.ws.model.Merchant;
import dtu.ws.model.Token;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Service
public class PaymentService implements IPaymentService {

    private BankService bankService; // = ControlReg.getFastMoneyBankService();
    private ITransactionDatabase transactionDatabase; // = ControlReg.getTransactionDatabase();
    private TokenManagerHTTPClient tokenManagerHTTPClient; // = ControlReg.getTokenManagerHTTPClient();
    private UserManagerHTTPClient userManagerHTTPClient; // = ControlReg.getUserManagerHTTPClient();

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void performPayment(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws BankServiceException_Exception, TokenValidationException, NotEnoughMoneyException {
        try {


            rabbitTemplate.convertAndSend(PaymentApplication.queueName,
                    "emil");


        }
        catch (AmqpConnectException e) {
            // ignore - rabbit is not running
        }
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

        this.userManagerHTTPClient.addTransactionToUserByAccountId(transaction.getDebtor(), transaction.getTransactionId());
        this.userManagerHTTPClient.addTransactionToUserByAccountId(transaction.getCreditor(), transaction.getTransactionId());

        return true;
    }

    @Override
    public DTUPayTransaction getTransactionById(String transactionId) {
        return this.transactionDatabase.getTransactionById(transactionId);
    }

    @Override
    public ArrayList<DTUPayTransaction> getTransactionsByCustomerCpr(String cpr) {

        ArrayList<DTUPayTransaction> result = new ArrayList<>();

        Customer customer = this.userManagerHTTPClient.getCustomerByCpr(cpr);

        for (String transactionId : customer.getTransactionIds()) {
            result.add(this.getTransactionById(transactionId));
        }

        return result;
    }

    @Override
    public ArrayList<DTUPayTransaction> getTransactionsByMerchantCpr(String cpr) {

        ArrayList<DTUPayTransaction> result = new ArrayList<>();

        Merchant merchant = this.userManagerHTTPClient.getMerchantByCpr(cpr);

        for (String transactionId : merchant.getTransactionIds()) {
            result.add(this.getTransactionById(transactionId));
        }

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

        throw new NotEnoughMoneyException("You have not enough money.");
    }
}
