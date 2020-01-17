package dtu.ws.database;

import dtu.ws.model.DTUPayTransaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface ITransactionDatabase {
    DTUPayTransaction getTransactionById(String transactionId);
    ArrayList<DTUPayTransaction> getAllTransactions();
    String saveTransaction(DTUPayTransaction transaction);
}
