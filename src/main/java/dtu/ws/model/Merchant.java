package dtu.ws.model;

/**
 * @author Marcus August Christiansen - s175185
 */

public class Merchant extends DTUPayUser {

    public Merchant() {
    }

    public Merchant(String accountId, String firstName, String lastName, String cprNumber) {
        super(accountId, firstName, lastName, cprNumber);
    }

}
