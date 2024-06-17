package com.example.sampleproject.steps;

import com.example.sampleproject.repository.ArtistRepository;
import com.example.sampleproject.service.impl.DataLoaderService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

@CucumberContextConfiguration
@SpringBootTest
public class MyStepDefs {

    private ArtistRepository artistRepository;
    private DataLoaderService dataLoaderService;
    private Response response;

    public MyStepDefs(ArtistRepository artistRepository, DataLoaderService dataLoaderService) {
        this.artistRepository = artistRepository;
        this.dataLoaderService = dataLoaderService;
    }

    @Given("there are artists in the database")
    public void thereAreArtistsInTheDatabase() {
        if (artistRepository.count() == 0) {
            dataLoaderService.loadData();
        }
    }

    @When("user sends a GET request to {string}")
    public void userSendsAGETRequestTo(String endpoint) {
        response = get(endpoint);
    }


    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int code) {
        response.then().assertThat().statusCode(code);
    }

    @And("the response body should contain a list of artists")
    public void theResponseBodyShouldContainAListOfArtists() {
        int count = (int) artistRepository.count();
        response.then().assertThat().body("size()", equalTo(count));
    }

    @And("content-type should be {string}")
    public void contentTypeShouldBe(String contentType) {
        response.then().assertThat().header("Content-Type", equalTo(contentType));
//        response.then().assertThat().contentType(ContentType.JSON);
    }
}
