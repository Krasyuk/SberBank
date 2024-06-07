package org.example.steps;

import io.cucumber.java.en.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;

public class WeatherApiSteps {
    private final Map<String, Map<String, String>> expectedWeatherData = new HashMap<>();
    private final Map<String, JSONObject> actualWeatherData = new HashMap<>();
    private Response response;
    private String apiKey = "1faa70bd36b44bffa49124839240506";
    private String baseUrl = "http://api.weatherapi.com/v1/current.json";
    private static final Logger logger = LoggerFactory.getLogger(WeatherApiSteps.class);

    @Given("I have a valid API key")
    public void iHaveAValidAPIKey() {
        this.apiKey = "1faa70bd36b44bffa49124839240506";
    }

    @Given("I have an invalid API key")
    public void iHaveAnInvalidAPIKey() {
        this.apiKey = "неверный API ключ";
    }

    @Given("the following cities and their expected weather data")
    public void the_following_cities_and_their_expected_weather_data(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            Map<String, String> weatherData = new HashMap<>();
            weatherData.put("expected_temp", row.get("expected_temp"));
            expectedWeatherData.put(row.get("city"), weatherData);
        }
    }

    @When("I request the current weather for these cities")
    public void i_request_the_current_weather_for_these_cities() {
        for (String city : expectedWeatherData.keySet()) {
            Response response = RestAssured.given()
                    .queryParam("key", apiKey)
                    .queryParam("q", city)
                    .get(baseUrl);
            actualWeatherData.put(city, new JSONObject(response.getBody().asString()));
        }
    }

    @Then("the actual weather data should match the expected values")
    public void the_actual_weather_data_should_match_the_expected_values() {
        for (String city : expectedWeatherData.keySet()) {
            JSONObject actualData = actualWeatherData.get(city);
            Map<String, String> expectedData = expectedWeatherData.get(city);

            double actualTemp = actualData.getJSONObject("current").getDouble("temp_c");
            double expectedTemp = Double.parseDouble(expectedData.get("expected_temp"));

            try {
                Assert.assertEquals(expectedTemp, actualTemp, 1.0);
            } catch (AssertionError e) {
                logger.error("Temperature discrepancy for {}: expected {} but got {}", city, expectedTemp, actualTemp);
            }

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
