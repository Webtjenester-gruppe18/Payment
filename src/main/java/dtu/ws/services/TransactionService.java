package dtu.ws.services;

import dtu.ws.database.ITransactionDatabase;
import dtu.ws.messagingutils.IEventReceiver;
import dtu.ws.messagingutils.IEventSender;
import dtu.ws.model.DTUPayTransaction;
import dtu.ws.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
