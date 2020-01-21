package dtu.ws.database;

import dtu.ws.model.DTUPayTransaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author Marcus August Christiansen - s175185
 */

public interface ITransactionDatabase {
    DTUPayTransaction getTransactionById(String transactionId);
    ArrayList<DTUPayTransaction> getTransactionsByAccountId(String accountId);
    ArrayList<DTUPayTransaction> getAllTransactions();
    String saveTransaction(DTUPayTransaction transaction);
}
