package dtu.ws.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

   /* private IPaymentService paymentService = ControlReg.getPaymentService();

    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    public ResponseEntity<Object> performPayment(@RequestBody PaymentObject paymentObject) {
        try {
            this.paymentService.performPayment(
                    paymentObject.getFromAccountNumber(),
                    paymentObject.getToAccountNumber(),
                    paymentObject.getAmount(),
                    paymentObject.getDescription(),
                    paymentObject.getToken());

        } catch (BankServiceException_Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (TokenValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotEnoughMoneyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Transaction succeed", HttpStatus.OK);
    }

    @RequestMapping(value = "/refunds", method = RequestMethod.POST)
    public ResponseEntity<Object> performRefund(@RequestBody DTUPayTransaction transaction) {

        try {
            this.paymentService.performRefund(transaction);
        } catch (BankServiceException_Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Refund succeed", HttpStatus.OK);
    }
*/
}
