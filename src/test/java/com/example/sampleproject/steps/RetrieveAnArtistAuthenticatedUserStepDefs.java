package com.example.sampleproject.steps;

import com.example.sampleproject.model.entities.ArtistEntity;
import com.example.sampleproject.repository.AlbumRepository;
import com.example.sampleproject.repository.ArtistRepository;
import com.example.sampleproject.repository.UserRepository;
import com.example.sampleproject.repository.UserRoleRepository;
import com.example.sampleproject.stepsHelpers.Helper;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class RetrieveAnArtistAuthenticatedUserStepDefs {

    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private Response response;
    private Helper helper;

    public RetrieveAnArtistAuthenticatedUserStepDefs(AlbumRepository albumRepository, ArtistRepository artistRepository,
                                                     UserRepository userRepository, UserRoleRepository userRoleRepository, Helper helper) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.helper = helper;
    }

    //    valid user, valid id
    @When("I send GET request to {string} using {string} id")
    public void iAsUserSendGETRequestTo(String endpoint, String artistName) {
        ArtistEntity artistEntity = artistRepository.findByArtist(artistName).orElseThrow();

        response = helper.getRequest()
                .when()
                .get(endpoint + artistEntity.getId())
                .then()
                .extract()
                .response();

        helper.setResponse(response);
        System.out.println("Response status code !!: " + response.getStatusCode());
    }

    //    valid user, invalid artist id
    @When("I send GET request to {string} with invalid id")
    public void iAsUserSendGETRequestToWithInvalidId(String endpoint) {
        response = helper.getRequest()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
        helper.setResponse(response);
    }
}

