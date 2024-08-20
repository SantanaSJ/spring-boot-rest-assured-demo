package com.example.sampleproject.steps;

import com.example.sampleproject.repository.AlbumRepository;
import com.example.sampleproject.repository.ArtistRepository;
import com.example.sampleproject.repository.UserRepository;
import com.example.sampleproject.service.ArtistService;
import com.example.sampleproject.service.impl.DataLoaderService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;

public class Hooks {

    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;
    private DataLoaderService dataLoaderService;
    private ArtistService artistService;
    private UserRepository userRepository;

    public Hooks(AlbumRepository albumRepository, ArtistRepository artistRepository, DataLoaderService dataLoaderService, ArtistService artistService, UserRepository userRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.dataLoaderService = dataLoaderService;
        this.artistService = artistService;
        this.userRepository = userRepository;
    }

    @Before
    public void beforeAll() {
        RestAssured.baseURI = "http://localhost:8080";

        dataLoaderService.loadData();

        RestAssured.filters(
//                new RequestLoggingFilter(LogDetail.ALL),
//                new ResponseLoggingFilter(LogDetail.ALL),
                new ErrorLoggingFilter());
    }


    @After
    public void tearDown() {
        userRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
    }

}
