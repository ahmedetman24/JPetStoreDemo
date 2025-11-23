Feature: Shopping Scenario
  The required scenario is making registration for a user, login and add a pet to the shopping cart.

  Scenario Outline: register a new user
    Given user opens the home page using "<url>"
    When user go to sign in page
    And make registration using "<countryCode>", "<firstNumber>", "<secondNumber>", "<secondNumberLength>", "<numberLength>"
    Then home page should be displaying expected "<url>"

    Examples:
      | url                                                  | countryCode | firstNumber | secondNumber | secondNumberLength | numberLength |
      | https://petstore.octoperf.com/actions/Catalog.action |+20          |1            |0125          |1                   |8             |

  Scenario Outline: login with valid data
    Given user go to sign in page and may use "<url>", "<alternativeUserID>", "<alternativeUserPassword>", "<alternativeFirstName>"
    When login with valid data
    And all pets page is opened
    Then go to any pet page containing "<petName>"

    Examples:
      | url                                                  | alternativeUserID | alternativeUserPassword | alternativeFirstName | petName |
      | https://petstore.octoperf.com/actions/Catalog.action | ahmedetman        | 123456                  | Ahmed                |cats     |

  Scenario Outline: add pet into cart
    Given target pet page is displaying "<petName>"
    When add pet to the cart with "<petType>", "<itemID>"
    Then pet should be added to the cart with "<itemID>", "<petType>"

    Examples:
      | petName | petType   | itemID |
      | cats    | FL-DLH-02 |EST-16  |