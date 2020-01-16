package ws18.services;

import ws18.exception.NotEnoughMoneyException;
import ws18.exception.TokenValidationException;
import ws18.model.DTUPayTransaction;
import ws18.model.Token;


import java.math.BigDecimal;

public interface IPaymentService {

    boolean performPayment(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws dtu.ws.fastmoney.BankServiceException_Exception, TokenValidationException, NotEnoughMoneyException;
    boolean performRefund(DTUPayTransaction transaction) throws dtu.ws.fastmoney.BankServiceException_Exception;

}
