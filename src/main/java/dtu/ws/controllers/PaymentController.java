package dtu.ws.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @RequestMapping("/payment")
    public String index() {
        return "Hello from paymentcontroller";
    }

}
