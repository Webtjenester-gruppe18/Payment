package dtu.ws.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionToUserByAccountId {

    private String accountId;
    private String transactionId;

    public TransactionToUserByAccountId(String accountId, String transactionId) {
        this.accountId = accountId;
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
