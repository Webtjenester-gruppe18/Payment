package dtu.ws.services;

import dtu.ws.model.DTUPayTransaction;

import java.util.ArrayList;

public interface ITransactionService {

    DTUPayTransaction getTransactionById(String transactionId);
    ArrayList<DTUPayTransaction> getTransactionsByAccountId(String accountId);
    String saveTransaction(DTUPayTransaction transaction);
}
