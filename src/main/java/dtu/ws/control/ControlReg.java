package dtu.ws.control;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.services.IPaymentService;

public class ControlReg {
    private static BankService bankService;
    private static IPaymentService paymentService;

    public static BankService getFastMoneyBankService() {
        if (bankService == null) bankService = new BankServiceService().getBankServicePort();
        return bankService;
    }

  /*  public static IPaymentService getPaymentService() {
        if (paymentService == null) paymentService = new PaymentService();
        return paymentService;
    }*/

}
