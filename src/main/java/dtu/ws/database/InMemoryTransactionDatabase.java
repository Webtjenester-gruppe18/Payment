package dtu.ws.database;


import dtu.ws.model.DTUPayTransaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InMemoryTransactionDatabase implements ITransactionDatabase {

    private ArrayList<DTUPayTransaction> transactions = new ArrayList<>();

    @Override
    public DTUPayTransaction getTransactionById(String transactionId) {
        for (DTUPayTransaction transaction : this.transactions) {
            if (transaction.getTransactionId().equals(transactionId)) {
                return transaction;
            }
        }

        return null;
    }

    @Override
    public ArrayList<DTUPayTransaction> getTransactionsByAccountId(String accountId) {
        ArrayList<DTUPayTransaction> result = new ArrayList<>();

        for (DTUPayTransaction transaction : this.transactions) {
            if (transaction.getCreditor().equals(accountId) || transaction.getDebtor().equals(accountId)) {
                result.add(transaction);
            }
        }

        return result;
    }

    @Override
    public ArrayList<DTUPayTransaction> getAllTransactions() {
        return this.transactions;
    }

    @Override
    public String saveTransaction(DTUPayTransaction transaction) {
        this.transactions.add(transaction);

        return transaction.getTransactionId();
    }
}
