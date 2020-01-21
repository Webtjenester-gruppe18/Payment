Feature: Payment features

  Scenario: perform payment
    When the service receives the "MONEY_TRANSFER_REQUEST" event
    Then the money is transferred
    And the "MONEY_TRANSFER_SUCCEED" is broadcast

  Scenario: Request for transactions
    When the service receives the "REQUEST_TRANSACTIONS" event
    Then the transactions are retrieved
    And the "REQUEST_TRANSACTIONS_RESPONSE" is broadcast

  Scenario: Request for refund
    When the service receives the "REFUND_REQUEST" event
    Then the money is refunded
    And the "REFUND_SUCCEED" is broadcast