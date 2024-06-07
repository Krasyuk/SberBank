Feature: Weather API Tests

  Scenario: Handle API errors
    Given I have an invalid API key
    When I request current weather for city "New York"
    Then the response status code is 403
    And the error message is "API key has been disabled."

    Given I have a valid API key
    When I request current weather without a city parameter
    Then the response status code is 400
    And the error message is "Parameter q is missing."

    Given I have a valid API key
    When I request current weather with an invalid URL
    Then the response status code is 400
    And the error message is "API URL is invalid."

    Given I have a valid API key
    When I request current weather for an unknown city "UnknownCity"
    Then the response status code is 400
    And the error message is "No matching location found."