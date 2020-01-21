package dtu.ws.services;

import dtu.ws.model.DTUPayTransaction;

import java.util.ArrayList;

/**
 * @author Marcus August Christiansen - s175185
 */

public interface ITransactionService {

    DTUPayTransaction getTransactionById(String transactionId);
    ArrayList<DTUPayTransaction> getTransactionsByAccountId(String accountId);
    String saveTransaction(DTUPayTransaction transaction);
}
