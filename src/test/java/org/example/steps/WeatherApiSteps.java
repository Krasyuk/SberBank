package org.example.steps;

import io.cucumber.java.en.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.RestAssured;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static io.restassured.RestAssured.given;

public class WeatherApiSteps {
    private String apiKey;
    private Response response;
    private Map<String, Response> responses = new HashMap<>();

    @Given("I have a valid API key")
    public void iHaveAValidAPIKey() {
        this.apiKey = "1faa70bd36b44bffa49124839240506";
    }

    @Given("I have an invalid API key")
    public void iHaveAnInvalidAPIKey() {
        this.apiKey = "неверный API ключ";
    }

    @When("I request current weather for the following cities")
    public void iRequestCurrentWeatherForTheFollowingCities(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> cities = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> city : cities) {
            String cityName = city.get("city");
            Response response = given()
                    .queryParam("key", apiKey)
                    .queryParam("q", cityName)
                    .when()
                    .get("http://api.weatherapi.com/v1/current.json")
                    .then()
                    .extract().response();
            responses.put(cityName, response);
        }
    }

    @When("I request current weather for city {string}")
    public void iRequestCurrentWeatherForCity(String city) {
        response = given()
                .queryParam("key", apiKey)
                .queryParam("q", city)
                .when()
                .get("http://api.weatherapi.com/v1/current.json")
                .then()
                .extract().response();
    }

    @When("I request current weather without a city parameter")
    public void iRequestCurrentWeatherWithoutCityParameter() {
        response = given()
                .queryParam("key", apiKey)
                .when()
                .get("http://api.weatherapi.com/v1/current.json")
                .then()
                .extract().response();
    }

    @When("I request current weather with an invalid URL")
    public void iRequestCurrentWeatherWithInvalidURL() {
        response = given()
                .queryParam("key", apiKey)
                .queryParam("q", "New York")
                .when()
                .get("http://api.weatherapi.com/v1/invalid_endpoint.json")
                .then()
                .extract().response();
    }

    @When("I request current weather for an unknown city {string}")
    public void iRequestCurrentWeatherForAnUnknownCity(String city) {
        response = given()
                .queryParam("key", apiKey)
                .queryParam("q", city)
                .when()
                .get("http://api.weatherapi.com/v1/current.json")
                .then()
                .extract().response();
    }

    @Then("the response contains valid weather data for each city")
    @Step("Validate weather data for each city")
    public void validateWeatherResponse() {
        for (Map.Entry<String, Response> entry : responses.entrySet()) {
            String cityName = entry.getKey();
            Response response = entry.getValue();
            assertEquals(200, response.getStatusCode(), "Invalid status code for city: " + cityName);
            assertNotNull(response.jsonPath().get("location.name"), "Location name is null for city: " + cityName);
            assertNotNull(response.jsonPath().get("current.temp_c"), "Temperature is null for city: " + cityName);
        }
    }

    @Then("I compare the response with expected data")
    @Step("Compare response with expected data")
    public void compareResponseWithExpectedData() {
        Map<String, Map<String, Object>> expectedData = new HashMap<>();

        expectedData.put("New York", Map.of("temp_c", 19.4));
        expectedData.put("London", Map.of("temp_c", 15.0));
        expectedData.put("Tokyo", Map.of("temp_c", 25.0));
        expectedData.put("Sydney", Map.of("temp_c", 15.0));

        for (Map.Entry<String, Response> entry : responses.entrySet()) {
            String cityName = entry.getKey();
            Response response = entry.getValue();
            Map<String, Object> expected = expectedData.get(cityName);

            double actualTemp = response.jsonPath().getDouble("current.temp_c");
            double expectedTemp = (double) expected.get("temp_c");

            if (actualTemp != expectedTemp) {
                System.out.printf("Discrepancy for %s: expected temp=%.1f, but god temp=%.1f ",
                        cityName, expectedTemp, actualTemp);
            }

            assertEquals(expectedTemp, actualTemp, "Temperature mismatch for " + cityName);
        }
    }

    @Then("the response status code is {int}")
    @Step("Validate response status code")
    public void validateResponseStatusCode(int statusCode) {
        assertEquals(statusCode, response.getStatusCode(), "Unexpected status code");
    }

    @Then("the error message is {string}")
    @Step("Validate error message")
    public void validateErrorMessage(String expectedErrorMessage) {
        String actualErrorMessage = response.jsonPath().getString("error.message");
        assertEquals(expectedErrorMessage, actualErrorMessage, "Unexpected error message");
    }
}
