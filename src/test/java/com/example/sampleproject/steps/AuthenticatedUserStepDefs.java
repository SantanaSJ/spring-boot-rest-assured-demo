package com.example.sampleproject.steps;

import com.example.sampleproject.model.entities.*;
import com.example.sampleproject.repository.AlbumRepository;
import com.example.sampleproject.repository.ArtistRepository;
import com.example.sampleproject.repository.UserRepository;
import com.example.sampleproject.repository.UserRoleRepository;
import com.example.sampleproject.stepsHelpers.Helper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Set;

public class AuthenticatedUserStepDefs {

    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private Response response;
    private RequestSpecification request;
    private Helper helper;

    public AuthenticatedUserStepDefs(AlbumRepository albumRepository, ArtistRepository artistRepository,
                                     UserRepository userRepository, UserRoleRepository userRoleRepository,
                                     Helper helper) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.helper = helper;
    }

    @Given("I am an authenticated {string} with {string}")
    public void iAmAnAuthenticatedUserWithRole(String userName, String role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userName);
        List<AlbumEntity> allAlbums = albumRepository.findAll();
        userEntity.setAlbums(allAlbums);
        userEntity.setPassword("testpass");

        UserRoleEntity userRoleEntity = userRoleRepository.findByRole(UserRoleEnum.valueOf(role));

        userEntity.setRoles(Set.of(userRoleEntity));
        userRepository.save(userEntity);
        request = RestAssured.given().auth().basic(userName, userEntity.getPassword());
    }

    //    valid user, valid id
    @When("I send GET request to {string} using {string} id")
    public void iAsUserSendGETRequestTo(String endpoint, String artistName) {
        ArtistEntity artistEntity = artistRepository.findByArtist(artistName).orElseThrow();

        response = request
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
        response = request
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
        helper.setResponse(response);
    }

  }

