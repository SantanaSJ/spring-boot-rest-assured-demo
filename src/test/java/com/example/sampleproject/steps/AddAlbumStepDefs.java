package com.example.sampleproject.steps;

import com.example.sampleproject.exception.AlbumNotFoundException;
import com.example.sampleproject.model.entities.AlbumEntity;
import com.example.sampleproject.repository.AlbumRepository;
import com.example.sampleproject.repository.UserRepository;
import com.example.sampleproject.repository.UserRoleRepository;
import com.example.sampleproject.stepsHelpers.Helper;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddAlbumStepDefs {

    private AlbumRepository albumRepository;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private RequestSpecification request;
    private Helper helper;
    private Response response;
    private String csrfToken;

    public AddAlbumStepDefs(AlbumRepository albumRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, Helper helper) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.helper = helper;
    }

    @And("CSRF token is valid and present")
    public void csrfTokenIsValidAndPresent() {
        response = RestAssured.given().get("/api/csrf");
        csrfToken = response.getCookie("XSRF-TOKEN");
        if (csrfToken == null) {
            throw new IllegalStateException("CSRF token not found in the response");
        }
    }

    @And("the album {string} by {string} does not exist in the database")
    public void theAlbumByDoesNotExistInTheDatabase(String albumName, String artistName) {
        Optional<AlbumEntity> optionalAlbumEntity = albumRepository.findByAlbumNameAndArtistName(albumName, artistName);
        if (optionalAlbumEntity.isPresent()) {
            throw new AlbumNotFoundException("Album with this name already exists!");
        }
    }

    @When("I send a POST request to {string} with a valid request body with {string}, {string}, {string}  and CSRF token")
    public void iSendAPOSTRequestToWithAValidRequestBodyAndCSRFToken(String endpoint, String artistName, String albumName, String description) {
        JsonObject requestBody = new JsonObject();
        requestBody
                .add("artist", artistName + "NEW")
                .add("albumName", albumName + "NEW")
                .add("description", description);
        String body = requestBody.toString();

        response = helper.getRequest()
                .header("X-XSRF-TOKEN", csrfToken)
                .cookie("XSRF-TOKEN", csrfToken)
                .contentType("application/json")
                .body(body)
                .post(endpoint);
        helper.setResponse(response);
    }

    @And("the location header should be present")
    public void theLocationHeaderShouldBePresent() {
        String locationHeaserString = response.getHeader("Location");
        assertNotNull(response.getHeader("Location"), "Location not found in the response");
        assertTrue(locationHeaserString.matches("http://localhost:8080/album/\\d+"));
    }

    @And("the response message should be {string}")
    public void theResponseMessageShouldBe(String message) {
        response.then().body("message", equalTo(message));
    }
}
