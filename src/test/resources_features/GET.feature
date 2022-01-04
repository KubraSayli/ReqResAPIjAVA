Feature: As a QA,I want to verify if GET requests are working as expected for https://reqres.in/
  Background: Set up the url
    Given I set the url

  Scenario Outline: Verify if GET request is working as expected for creating user
    When I send get request "<pathParName1>" for path parameters "<pathParam1>" and "<pathParName2>" for "<pathParam2>"
    And status code should be 200
    Then I verify if data from GET body matches with data from response "<jsonPath1>" for "<testData1>" and "<jsonPath2>" for "<testData2>"
    Examples:
      | pathParName1 | pathParam1 | pathParName2 | pathParam2 | jsonPath1       | testData1 | jsonPath2      | testData2 |
      | usersPath    | users      | id           | 2          | data.first_name | Janet     | data.last_name | Weaver    |


