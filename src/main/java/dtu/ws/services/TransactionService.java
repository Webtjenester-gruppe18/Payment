package dtu.ws.services;

import dtu.ws.database.ITransactionDatabase;
import dtu.ws.model.DTUPayTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Marcus August Christiansen - s175185
 */

@Service
public class TransactionService implements ITransactionService {

    private ITransactionDatabase transactionDatabase;

    @Autowired
    public TransactionService(ITransactionDatabase transactionDatabase) {
        this.transactionDatabase = transactionDatabase;
    }

    @Override
    public DTUPayTransaction getTransactionById(String transactionId) {
        return this.transactionDatabase.getTransactionById(transactionId);
    }

    @Override
    public ArrayList<DTUPayTransaction> getTransactionsByAccountId(String accountId) {
        return this.transactionDatabase.getTransactionsByAccountId(accountId);
    }

    @Override
    public String saveTransaction(DTUPayTransaction transaction) {
        return this.transactionDatabase.saveTransaction(transaction);
    }
}
