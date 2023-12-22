@LoginPage @regression
Feature: Test Login functionality of sause labs website

  @LoginPage
  Scenario Outline: Validate Login page functionality by entering the correct login credentials
    Given User launches "https://www.saucedemo.com"
    And enter "<UserName>" and "<Password>" and click login
    Then verify page title as "Swag Labs"

    Examples: 
      | UserName      | Password     |
      | standard_user | secret_sauce |

  @LoginPage
  Scenario Outline: Validate error messages on the login page
    Given User launches "https://www.saucedemo.com"
    And enter "<UserName>" and "<Password>" and click login
    Then validate error message "<error message>"

    Examples: 
      | UserName        | Password     | error message                                                             |
      | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                       |
      | standard_user   | scret_saucT  | Epic sadface: Username and password do not match any user in this service |
      | standard_user   |              | Epic sadface: Password is required                                        |
      |                 | scret_saucT  | Epic sadface: Username is required                                        |
      |                 |              | Epic sadface: Username is required                                        |
