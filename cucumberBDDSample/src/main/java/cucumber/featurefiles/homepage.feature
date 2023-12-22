@Homepage @regression
Feature: As a standard user I should be able make a purchase of items as desired

  @testCase2
  Scenario Outline: Validate if user can buy desired number of items from a basket of n items.
    Given User launches "https://www.saucedemo.com"
    And enter "<UserName>" and "<Password>" and click login
    When user adds <items added to cart> items to basket and goes to cart
    Then user buys <items to checkout> items from cart page
    And complete checkout for "<First name>" "<Last name>" "<Postal code>"

    Examples: 
      | UserName      | Password     | items added to cart | items to checkout | First name  | Last name | Postal code |
      | standard_user | secret_sauce |                   3 |                 2 | Ashok Varma | Sagi      | M2N 7G8     |

  @testCase3
  Scenario Outline: Validate if user can buy items within desired price range
    Given User launches "https://www.saucedemo.com"
    When enter "<UserName>" and "<Password>" and click login
    Then user total purchase price should be with in <minimumPrice> and <maximumPrice>
    And complete checkout for "<First name>" "<Last name>" "<Postal code>"

    Examples: 
      | UserName      | Password     | minimumPrice | maximumPrice | First name  | Last name | Postal code |
      | standard_user | secret_sauce |           30 |           60 | Ashok Varma | Sagi      | M2N 7G8     |
