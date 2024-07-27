package com.example.sampleproject.stepsHelpers;

import com.example.sampleproject.exception.ArtistNotFoundException;
import com.example.sampleproject.model.entities.AlbumEntity;
import com.example.sampleproject.model.entities.ArtistEntity;
import com.example.sampleproject.repository.AlbumRepository;
import com.example.sampleproject.repository.ArtistRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class Helper {

    private Response response;
    private ArtistRepository artistRepository;
    private AlbumRepository albumRepository;


    public Helper(ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }


    @Given("artists exist in the DB")
    public void theFollowingArtistsExistInTheDB(DataTable dataTable) {
        List<Map<String, String>> map = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : map) {
            ArtistEntity artist = new ArtistEntity();
            String name = row.get("name");
            String description = row.get("description");
            artist.setArtist(name);
            artist.setDescription(description);
            artistRepository.save(artist);
        }
    }

    @And("the following albums exist in the DB for that artist")
    public void theFollowingAlbumsExistInTheDBForThatArtist(DataTable dataTable) {

        List<Map<String, String>> map = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : map) {
            String artistName = row.get("artist");
            ArtistEntity artistEntity = artistRepository
                    .findByArtist(artistName)
                    .orElseThrow(() -> new ArtistNotFoundException("Artist not found!"));
            AlbumEntity album = new AlbumEntity();
            album.setArtist(artistEntity);
            album.setDescription(row.get("description"));
            album.setAlbumName(row.get("album"));
            albumRepository.save(album);
            artistEntity.getAlbums().add(album);
            artistRepository.save(artistEntity);
        }
    }

    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int code) {
        response.then().assertThat().statusCode(code);
    }

    @Then("the artist name should be {string}")
    public void theArtistNameShouldBe(String name) {
        response.then().body("artist", equalTo(name));
        System.out.println("Artist name in response: " + response.jsonPath().getString("artist"));
    }

    @And("content-type should be {string}")
    public void contentTypeShouldBe(String contentType) {
        response.then().assertThat().header("Content-Type", equalTo(contentType));
//        response.then().assertThat().contentType(ContentType.JSON);
    }

    @And("the artist description should be {string}")
    public void theArtistDescriptionShouldBe(String description) {
        response.then().body("description", equalTo(description));
        System.out.println("Description in response: " + response.jsonPath().getString("description"));
    }

    @And("the artist should have {string} albums")
    public void theArtistShouldHaveAlbums(String albums) {
        int numberOfAlbums = Integer.parseInt(albums);
        response.then().assertThat().body("albums.size()", equalTo(numberOfAlbums));
        System.out.println("Album size in response: " + response.jsonPath().getString("albums.size()"));
    }

    @And("the first album name should be {string}")
    public void theFirstAlbumNameShouldBe(String albumName) {
        response.then().assertThat().body("albums[0].album", equalTo(albumName));
        System.out.println("First album in response: " + response.jsonPath().getString("albums[0].album"));
    }

    @And("the first album description should be {string}")
    public void theFirstAlbumDescriptionShouldBe(String description) {
        response.then().assertThat().body("albums[0].description", equalTo(description));
        System.out.println("First album description in response: " + response.jsonPath().getString("albums[0].description"));
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
