package dtu.ws.model;

/**
 * @author Marcus August Christiansen - s175185
 */

public class Customer extends DTUPayUser {

    public Customer() {
    }

    public Customer(String accountId, String firstName, String lastName, String cprNumber) {
        super(accountId, firstName, lastName, cprNumber);
    }
}
