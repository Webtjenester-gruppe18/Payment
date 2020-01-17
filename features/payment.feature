Feature: Payment features

  Scenario: Customer pays merchant
    Given a customer that is registered in DTUPay with a bank account
    And the customer has at least one unused token
    And a merchant that is registered in DTUPay with a bank account
    When the customer pays 100 kr to the merchant
    Then the transaction succeed
    And the money is transferred
