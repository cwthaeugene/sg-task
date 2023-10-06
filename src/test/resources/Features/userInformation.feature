Feature: feature for retrieving user information


  Scenario: Retrieve user information when the session is valid
    Given user information api is available
    Then we send a post request to /authorize endpoint with username and password credentials to gain and save the token
    And we send another get request to /info endpoint with the gained token in the authorization header
    Then response status code is 200
    And the response returns user info

  Scenario: Retrieve user information when the session is not valid.
    Given user information api is available
    Then we send get request to /info endpoint
    Then response status code is 200
    And the response returns user info



