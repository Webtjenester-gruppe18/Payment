package dtu.ws.control;

import dtu.ws.HTTPClients.ReportingHTTPClient;
import dtu.ws.HTTPClients.TokenManagerHTTPClient;
import dtu.ws.HTTPClients.UserManagerHTTPClient;
import dtu.ws.database.ITransactionDatabase;
import dtu.ws.database.InMemoryTransactionDatabase;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.services.IPaymentService;

public class ControlReg {
    private static BankService bankService;
    private static IPaymentService paymentService;
    private static TokenManagerHTTPClient tokenManagerHTTPClient;
    private static ReportingHTTPClient reportingHTTPClient;
    private static UserManagerHTTPClient userManagerHTTPClient;
    private static ITransactionDatabase transactionDatabase;

    public static BankService getFastMoneyBankService() {
        if (bankService == null) bankService = new BankServiceService().getBankServicePort();
        return bankService;
    }

    public static TokenManagerHTTPClient getTokenManagerHTTPClient() {
        if (tokenManagerHTTPClient == null) tokenManagerHTTPClient = new TokenManagerHTTPClient();
        return tokenManagerHTTPClient;
    }

    public static ReportingHTTPClient getReportingHTTPClient() {
        if (reportingHTTPClient == null) reportingHTTPClient = new ReportingHTTPClient();
        return reportingHTTPClient;
    }

    public static UserManagerHTTPClient getUserManagerHTTPClient() {
        if (userManagerHTTPClient == null) userManagerHTTPClient = new UserManagerHTTPClient();
        return userManagerHTTPClient;
    }

  /*  public static IPaymentService getPaymentService() {
        if (paymentService == null) paymentService = new PaymentService();
        return paymentService;
    }*/

    public static ITransactionDatabase getTransactionDatabase() {
        if (transactionDatabase == null) transactionDatabase = new InMemoryTransactionDatabase();
        return transactionDatabase;
    }
}
