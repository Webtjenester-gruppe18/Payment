package dtu.ws.services;


import dtu.ws.exception.NotEnoughMoneyException;
import dtu.ws.exception.TokenValidationException;
import dtu.ws.model.DTUPayTransaction;
import dtu.ws.model.PaymentRequest;
import dtu.ws.model.Token;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author Marcus August Christiansen - s175185
 */

public interface IPaymentService {

    void performPayment(PaymentRequest paymentRequest) throws dtu.ws.fastmoney.BankServiceException_Exception;
    boolean performRefund(DTUPayTransaction transaction) throws dtu.ws.fastmoney.BankServiceException_Exception;
}
