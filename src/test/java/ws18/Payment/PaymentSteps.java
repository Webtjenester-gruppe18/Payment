package ws18.Payment;

import dtu.ws.HTTPClients.TokenManagerHTTPClient;
import dtu.ws.HTTPClients.UserManagerHTTPClient;
import dtu.ws.control.ControlReg;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;
import dtu.ws.model.Customer;
import dtu.ws.model.Token;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

public class PaymentSteps {

    private BankService bankService;
    private Customer currentCustomer;
    private UserManagerHTTPClient userManager;
    private TokenManagerHTTPClient tokenManager;

    @Before
    public void setUp() {
        this.bankService = ControlReg.getFastMoneyBankService();
        this.userManager = new UserManagerHTTPClient();
        this.tokenManager = new TokenManagerHTTPClient();
    }

    @Given("a customer that is registered in DTUPay with a bank account")
    public void aCustomerThatIsRegisteredInDTUPayWithABankAccount() {
        User customer = new User();
        customer.setCprNumber("23675");
        customer.setFirstName("Frederik");
        customer.setLastName("Hjort");

        String accountId = "";
        try {
            accountId = this.bankService.createAccountWithBalance(customer, BigDecimal.valueOf(1000));
        } catch (BankServiceException_Exception e) {
            System.out.println("NOGET GIK GALT");
        }

        this.currentCustomer = new Customer(
                accountId,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getCprNumber());

        this.userManager.registerCustomer(this.currentCustomer);
    }

    @Given("the customer has at least one unused token")
    public void theCustomerHasAtLeastOneUnusedToken() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("a merchant that is registered in DTUPay with a bank account")
    public void aMerchantThatIsRegisteredInDTUPayWithABankAccount() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("the customer pays {int} kr to the merchant")
    public void theCustomerPaysKrToTheMerchant(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the transaction succeed")
    public void theTransactionSucceed() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the money is transferred")
    public void theMoneyIsTransferred() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
