package dtu.ws.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

/*    private IPaymentService paymentService = ControlReg.getPaymentService();

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public ResponseEntity<Object> getCustomerTransactions(@RequestParam String cpr) {

        ArrayList<DTUPayTransaction> res = this.paymentService.getTransactionsByCustomerCpr(cpr);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }*/
}
