package dtu.ws.controllers;

import dtu.ws.control.ControlReg;
import dtu.ws.model.DTUPayTransaction;
import dtu.ws.services.IPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private IPaymentService paymentService = ControlReg.getPaymentService();

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public ResponseEntity<Object> getCustomerTransactions(@RequestParam String cpr) {

        ArrayList<DTUPayTransaction> res = this.paymentService.getTransactionsByCustomerCpr(cpr);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
