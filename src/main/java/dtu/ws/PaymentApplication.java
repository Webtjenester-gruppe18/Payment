package dtu.ws;

import dtu.ws.control.ControlReg;
import dtu.ws.exception.NotEnoughMoneyException;
import dtu.ws.exception.TokenValidationException;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.model.Token;
import dtu.ws.services.IPaymentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.math.BigDecimal;

@EnableSwagger2
@SpringBootApplication
public class PaymentApplication {
    public static void main(String[] args) {

//        SpringApplication.run(PaymentApplication.class, args);

        IPaymentService paymentService = ControlReg.getPaymentService();

        try {
            paymentService.performPayment("account1", "account2", BigDecimal.valueOf(1000), "Comment", new Token());
        } catch (BankServiceException_Exception e) {
            System.out.println(e.getMessage());
        } catch (TokenValidationException e) {
            System.out.println(e.getMessage());
        } catch (NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
        }
    }
}
