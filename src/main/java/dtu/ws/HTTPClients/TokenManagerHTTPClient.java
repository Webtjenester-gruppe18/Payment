package dtu.ws.HTTPClients;

import dtu.ws.model.Token;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Component
public class TokenManagerHTTPClient {

    Client c = ClientBuilder.newClient();
    WebTarget w = c.target("http://fastmoney-18.compute.dtu.dk:7373/");

    public boolean validateToken(String userCprNumber, Token token) {
        Token response = w.path("tokens/")
                .path(userCprNumber)
                .path("/valid")
                .request()
                .post(Entity.entity(token, MediaType.APPLICATION_JSON_TYPE), Token.class);

        return response.getValue().equals(token.getValue());
    }

    public void useToken(Token token) {

        Token response = w.path("tokens/")
                .path(token.getValue())
                .request()
                .put(Entity.entity(token, MediaType.APPLICATION_JSON_TYPE), Token.class);
    }


}
