Feature: As a QA,I want to verify if GET requests are working as expected for https://reqres.in/

  Background: Set up the url
    Given I set the url

  Scenario Outline: Verify if GET request is working as expected for creating user
    When I send get request "<pathParName1>" for path parameters "<pathParam1>" and "<pathParName2>" for "<pathParam2>"
    And status code should be 200
    Then I verify if data from GET body matches with data from response "<jsonPath1>" for "<testData1>" and "<jsonPath2>" for "<testData2>"
    Examples:
      | pathParName1 | pathParam1 | pathParName2 | pathParam2 | jsonPath1       | testData1    | jsonPath2      | testData2 |
      | usersPath    | users      | id           | 2          | data.first_name | Janet        | data.last_name | Weaver    |
      | PathNanme    | unknown    | id           | 2          | data.name       | fuchsia rose | data.id        | 2         |

  @this
  Scenario: Verify if GET request is working as expected for list of users
    When I send GET request to the endpoint with path parameter name as "usersName" for "users"  and "page" is "2" for query parameters
    And status code should be 200
    Then the number of data should be 6 for each page
    And all users have avatar
    And verify the data for eleventh user
      | data[4].id         | 11                                       |
      | data[4].email      | george.edwards@reqres.in                 |
      | data[4].first_name | George                                   |
      | data[4].last_name  | Edwards                                  |
      | data[4].avatar     | https://reqres.in/img/faces/11-image.jpg |


