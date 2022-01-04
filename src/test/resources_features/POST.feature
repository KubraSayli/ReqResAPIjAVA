Feature: As a QA,I want to verify if POST requests are working as expected for https://reqres.in/

  Background: Set up the url
    Given I set the url


  Scenario Outline: Verify if POST request is working as expected for creating user
    When I set the expected data for request and response "<jsonFilePath1>" and "<jsonFilePath2>"
    And I send the post request with path parameter name "<pathParName1>" for "<pathParam1>"
    Then status code should be <code>
    Then I verify if data from POST body matches with data from response "<data1>" and "<data2>"

    Examples:
      | jsonFilePath1                            | jsonFilePath2                             | pathParName1 | pathParam1 | data1 | data2 | code |
      | src/test/testData/CreateUserRequest.json | src/test/testData/CreateUserResponse.json | usersPath    | users      | name  | job   | 201  |
      | src/test/testData/RegisterRequest.json   | src/test/testData/RegisterResponse.json   | registerPath | register   | id    | token | 200  |

  @test
  Scenario: Verify if POST request is working as expected for creating user
    When I set the expected data for request and response "src/test/testData/LoginRequest.json" and "src/test/testData/LoginResponse.json"
    And I send the post request with path parameter name "loginPath" for "login"
    Then status code should be 200
    And I verify if data from POST body matches with data from response "token"