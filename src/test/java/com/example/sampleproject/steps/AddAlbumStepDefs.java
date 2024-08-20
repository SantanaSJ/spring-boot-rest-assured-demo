package com.example.sampleproject.steps;

import com.example.sampleproject.model.entities.AlbumEntity;
import com.example.sampleproject.model.entities.ArtistEntity;
import com.example.sampleproject.repository.AlbumRepository;
import com.example.sampleproject.repository.ArtistRepository;
import com.example.sampleproject.repository.UserRepository;
import com.example.sampleproject.repository.UserRoleRepository;
import com.example.sampleproject.stepsHelpers.Helper;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

public class AddAlbumStepDefs {

    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private RequestSpecification request;
    private Helper helper;
    private Response response;
    private String csrfToken;

    public AddAlbumStepDefs(AlbumRepository albumRepository, ArtistRepository artistRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, Helper helper) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
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
        assertFalse(optionalAlbumEntity.isPresent(), "Album " + albumName + "by " + artistName + " already exists in the database.");
    }

    @When("I send a POST request to {string} with a valid request body with {string}, {string}, {string}  and CSRF token")
    public void iSendAPOSTRequestToWithAValidRequestBodyAndCSRFToken(String endpoint, String artistName, String albumName, String description) {
        JsonObject requestBody = new JsonObject();
        requestBody
                .add("artist", artistName)
                .add("albumName", albumName)
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
        response.then().assertThat().body("message", equalTo(message));
    }

    @And("the album {string} by {string} exists in the database")
    public void theAlbumByExistsInTheDatabase(String albumName, String artistName) {
        ArtistEntity artist = new ArtistEntity();
        artist.setArtist(artistName);
        artist.setAlbums(new ArrayList<>());
        artistRepository.save(artist);

        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumName(albumName);
        albumEntity.setArtist(artist);
        artist.getAlbums().add(albumEntity);
        albumRepository.save(albumEntity);
        artistRepository.save(artist);
        System.out.println();
    }

    @And("the response message should be {string} and timestamp should not be null")
    public void theResponseMessageShouldBeAndTimestampShouldNotBeNull(String  message) {
        response
                .then()
                .assertThat()
                .body("message", equalTo(message))
                .body("timestamp", notNullValue());
    }
}
