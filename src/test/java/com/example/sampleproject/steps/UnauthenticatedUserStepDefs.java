package com.example.sampleproject.steps;

import com.example.sampleproject.model.entities.ArtistEntity;
import com.example.sampleproject.repository.AlbumRepository;
import com.example.sampleproject.repository.ArtistRepository;
import com.example.sampleproject.repository.UserRepository;
import com.example.sampleproject.repository.UserRoleRepository;
import com.example.sampleproject.stepsHelpers.Helper;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UnauthenticatedUserStepDefs {

    private ArtistRepository artistRepository;
    private AlbumRepository albumRepository;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private Response response;
    private Helper helper;

    public UnauthenticatedUserStepDefs(ArtistRepository artistRepository, AlbumRepository albumRepository,
                                       UserRepository userRepository, UserRoleRepository userRoleRepository,
                                       Helper helper) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.helper = helper;
    }

//    unauthenticated user, valid artist id
    @When("I as an unauthenticated user send GET request to {string} using {string} id")
    public void iAsUnauthenticatedUserSendGETRequestTo(String endpoint, String artistName) {
        ArtistEntity artistEntity = artistRepository.findByArtist(artistName).orElseThrow();

        response = RestAssured.given()
                .when()
                .get(endpoint + artistEntity.getId())
                .then()
                .extract()
                .response();

        helper.setResponse(response);
        System.out.println("Response status code: " + response.getStatusCode());
    }

//    valid user, invalid artist id
    @When("I as an unauthenticated user send GET request to {string} with invalid id")
    public void iAsUnauthenticatedUserSendGETRequestToWithInvalidId(String endpoint) {
        response = RestAssured.given()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
        helper.setResponse(response);
    }
}

