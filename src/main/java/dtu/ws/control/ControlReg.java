package dtu.ws.control;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;

public class ControlReg {
    private static BankService bankService;

    public static BankService getFastMoneyBankService() {
        if (bankService == null) bankService = new BankServiceService().getBankServicePort();
        //  if (bankService == null) bankService = new InMemoryBankService();
        return bankService;
    }
}
