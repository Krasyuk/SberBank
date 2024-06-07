Feature: Weather API Tests

  Scenario: Get weather for multiple cities and compare with expected values
    Given the following cities and their expected weather data
      | city     | expected_temp |
      | New York | 25            |
      | London   | 15            |
      | Tokyo    | 20            |
      | Sydney   | 22            |
    When I request the current weather for these cities
    Then the actual weather data should match the expected values

