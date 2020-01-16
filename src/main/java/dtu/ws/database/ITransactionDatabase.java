package dtu.ws.database;

import dtu.ws.model.DTUPayTransaction;

import java.util.ArrayList;

public interface ITransactionDatabase {
    DTUPayTransaction getTransactionById(String transactionId);
    ArrayList<DTUPayTransaction> getAllTransactions();
    String saveTransaction(DTUPayTransaction transaction);
}
