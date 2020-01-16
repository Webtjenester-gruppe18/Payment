package dtu.ws.services;


import dtu.ws.exception.NotEnoughMoneyException;
import dtu.ws.exception.TokenValidationException;
import dtu.ws.model.DTUPayTransaction;
import dtu.ws.model.Token;

import java.math.BigDecimal;

public interface IPaymentService {

    boolean performPayment(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws dtu.ws.fastmoney.BankServiceException_Exception, TokenValidationException, NotEnoughMoneyException;

    boolean performRefund(DTUPayTransaction transaction) throws dtu.ws.fastmoney.BankServiceException_Exception;

}