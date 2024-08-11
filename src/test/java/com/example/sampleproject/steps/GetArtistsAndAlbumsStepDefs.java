package com.example.sampleproject.steps;

import com.example.sampleproject.repository.AlbumRepository;
import com.example.sampleproject.repository.ArtistRepository;
import com.example.sampleproject.repository.UserRepository;
import com.example.sampleproject.service.impl.DataLoaderService;
import com.example.sampleproject.stepsHelpers.Helper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class GetArtistsAndAlbumsStepDefs {

    private ArtistRepository artistRepository;
    private DataLoaderService dataLoaderService;
    private UserRepository userRepository;
    private AlbumRepository albumRepository;
    private Response response;
    private Helper helper;

    public GetArtistsAndAlbumsStepDefs(ArtistRepository artistRepository, DataLoaderService dataLoaderService,
                                       UserRepository userRepository, AlbumRepository albumRepository, Helper helper) {
        this.artistRepository = artistRepository;
        this.dataLoaderService = dataLoaderService;
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
        this.helper = helper;
    }

    @Given("there are artists in the database")
    public void thereAreArtistsInTheDatabase() {
        if (artistRepository.count() == 0) {
            dataLoaderService.loadData();
        }
    }

    @Given("there are no artists in the database")
    public void thereAreNoArtistsInTheDatabase() {
//        clear album references from user
        userRepository
                .findAll()
                .forEach(user -> {
                    user.getAlbums().clear();
                    userRepository.save(user);
                });

        albumRepository.deleteAll();
        artistRepository.deleteAll();
    }

    @When("I send a GET request to {string}")
    public void userSendsAGETRequestTo(String endpoint) {
        response = RestAssured.get(endpoint);
        helper.setResponse(response);
    }

    @And("the response body should contain a list of artists")
    public void theResponseBodyShouldContainAListOfArtists() {
        int count = (int) artistRepository.count();
        response.then().assertThat().body("size()", equalTo(count));
    }

    @And("the response body should be empty")
    public void theResponseBodyShouldBeEmpty() {
        response.then().assertThat().body("size()", equalTo(0));
    }
}
