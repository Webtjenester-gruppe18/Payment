package dtu.ws.HTTPClients;

import dtu.ws.model.Customer;
import dtu.ws.model.Merchant;
import dtu.ws.model.TransactionToUserByAccountId;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserManagerHTTPClient {

    Client c = ClientBuilder.newClient();
    WebTarget w = c.target("http://fastmoney-18.compute.dtu.dk:7676/usermanager/");

    public void addTransactionToUserByAccountId(String accountId, String transactionId) {

        TransactionToUserByAccountId obj = new TransactionToUserByAccountId(accountId, transactionId);

        Response response =
                w.path("transactionId/")
                        .request()
                        .put(Entity.entity(obj, MediaType.APPLICATION_JSON_TYPE));

        System.out.println("RESPONSE: " + response.getStatus());
    }

    public Customer getCustomerByCpr(String cpr) {

//        Response response =
//                w.path("customer/")
//                .path(cpr)
//                .request().get();
//
//        Customer customer = response.readEntity(Customer.class);

        Customer customer = w.path("customer/")
                .path(cpr)
                .request()
                .get(Customer.class);

        return customer;
    }

    public Merchant getMerchantByCpr(String cpr) {
        Merchant merchant = w.path("merchant/")
                .path(cpr)
                .request()
                .get(Merchant.class);

        return merchant;
    }
}
