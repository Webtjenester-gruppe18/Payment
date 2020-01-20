Feature: Payment features

  Scenario: customer make a payment
    Given the customer has an account
    When the service receives the "MONEY_TRANSFER_REQUEST" event
    Then the payment is succeed
    And the "MONEY_TRANSFER_SUCCEED" is broadcast

    Scenario: user request a transaction
      Given there is a user with a bank account
      And there is a second user with a bank account
      When the service receives the "MONEY_TRANSFER_REQUEST"
      Then the transaction is succeed
      When the "REQUEST_TRANSACTIONS_RESPONSE" is broadcast